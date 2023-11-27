package br.upf.biblioteca.controller;

import br.upf.biblioteca.util.Criptografia;
import br.upf.biblioteca.controller.util.JsfUtil;
import br.upf.biblioteca.util.UtilSession;
import br.upf.biblioteca.entity.Usuario;
import br.upf.biblioteca.facade.UsuarioFacade;
import br.upf.biblioteca.service.UsuarioService;
import br.upf.biblioteca.util.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named("usuarioAcessoController")
@SessionScoped
public class UsuarioAcessoController implements Serializable {

    @EJB
    private br.upf.biblioteca.facade.UsuarioFacade ejbFacade;

    private UtilSession utilSession;
    private Usuario selected;
    private Usuario selectedUsuario;
    private String loginOrEmail;
    private String msg = "";
    private boolean loginPorDsLogin;
    private boolean sucessPersistencia = false; 
    
    private final UsuarioService usuarioService = new UsuarioService();
    
    // Captura a sessão do contexto criado pelo Java Server Faces
    private FacesContext context = null;
    private HttpSession session = null;

    public UsuarioAcessoController() {

    }

    /**
     * Método utilizado para inicializar métodos ao instanciar a classe...
     */
    @PostConstruct
    public void init() {
        prepareAutenticarUsuario();
    }

    public void prepareAutenticarUsuario() {
        selected = new Usuario();
        initializeEmbeddableKey();
    }

    /**
     * Método utilizado para autenticar usuario
     *
     * @return
     */
    public String autenticarUsuario() {
        Usuario usuario;
        handleInput();

        if (loginPorDsLogin) {
            // Login por DsLogin
            usuario = pesquisarUsuarioUsuarioCPF(usuarioService.criptografarValue(loginOrEmail));
        } else {
            // Login por Email
            usuario = pesquisarUsuarioUsuarioEmail(usuarioService.criptografarValue(loginOrEmail.toLowerCase()));
        }

        String senhaCripto = Criptografia.criptografar(selected.getDsSenha().trim().replaceAll("\\s+", " "));

        if (usuario.getCdUsuario() != null) {
            if (usuario.getDsSenha() != null) {
                if (usuarioService.autenticarUsuarioSenha(senhaCripto, usuario.getDsSenha())) {
                    // Criando a sessão...
                    context = FacesContext.getCurrentInstance();
                    session = (HttpSession) context.getExternalContext().getSession(false);;

                    // Cria o atributo "logado-usuario" na sessão e envia o objeto para mesma
                    session.setAttribute("logado-usuario", usuario);
                    session.setAttribute("logado-dsEmail", usuario.getDsEmail());
                    session.setAttribute("logado-permissao-acesso", usuario.getUsrPermissaoacesso());
                    
                    if (usuario.getUsrPermissaoacesso().equals("BIBLIOTECARIO")) {
                        session.setAttribute("logado-bibliotecario", true);
                    }
                    
                    return processLoginSucess(usuario);

                } else { // Caso o email ou senha seja incorreto
                    msg = ResourceBundle.getBundle("/Bundle").getString("UsuarioLoginMessage_credencialSenhaIncorreta");
                    JsfUtil.addErrorMessage(msg);
                    // Mantem usuario na mesma página
                    return processLoginCancel();
                }
            } else { // Usuário sem senha (não cadastrada)...
                msg = ResourceBundle.getBundle("/Bundle").getString("UsuarioLoginMessage_senhaNaoCadastrada");
                JsfUtil.addErrorMessage(usuario.getNmNome() + msg);
                return processLoginCancel();
            }
        } else { // Para usuário não cadastrado...
            msg = ResourceBundle.getBundle("/Bundle").getString("UsuarioLoginMessage_credencialNaoLocalizada");
            JsfUtil.addErrorMessage(msg);
            return processLoginCancel();
        }
    }

    /**
     * Método utilizado para invalidar sessão.
     *
     * @return
     */
    public String logout() {
        // Invalida a sessão
        session.invalidate();
        // "?faces-redirect=true" Realiza o redirecionamento de página.
        return "/login.xhtml?faces-redirect=true";
    }

    /**
     * Método responsável por preparar a criação de uma nova senha.
     */
    public void prepareNewPassword() {
        selectedUsuario = new Usuario();
        selected = new Usuario();
        initializeEmbeddableKey();
    }

    /**
     * Método responsável por gerar nova senha...
     */
    public void createNewPassword() {
        selectedUsuario = prepararDadosNovaSenha(selectedUsuario);
        selected = ejbFacade.findUsuarioByUsuarioEmailDataNascimento(selectedUsuario);
        String senhaNova = Util.gerarCodigoAleatorioNumerico(6);
        String senhaNovaCriptografada = Criptografia.criptografar(senhaNova);
        
        if (selected.getDsSenha()!= null) {
            selected.setDsSenha(senhaNovaCriptografada); // Definir nova senha
            persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdateSenha_Sucesso"));
            if (sucessPersistencia) {
                usuarioService.enviarEmailNovaSenha(selected, senhaNova);
            } else {
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        } else {
            JsfUtil.addAlertMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdateSenha_Error"));
        }
    }

    public void handleInput() {
        if (loginOrEmail != null && !loginOrEmail.isEmpty()) {

            // Se a string de login passada não possuir um caracter "@", será um login por DsLogin
            if (!loginOrEmail.contains("@")) {
                setLoginPorDsLogin(true);
            } else {
                setLoginPorDsLogin(false);
            }
        }
    }

    /**
     * Método responsável por tratar os dados para gerar nova senha.
     *
     * @param usuario
     * @return
     */
    private Usuario prepararDadosNovaSenha(Usuario usuario) {
        Usuario usuarioDadosTratados = new Usuario();
        
        // Tratando da data de nascimento...
        Date dataNascimento = Util.tratarDatanascimentoDate(usuario.getUsrDatanascimento());
        usuarioDadosTratados.setUsrDatanascimento(dataNascimento);
        usuarioDadosTratados.setUsrUsuarioEmail(Criptografia.criptografar(usuario.getDsEmail().toLowerCase()));
        
        return usuarioDadosTratados;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private boolean persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == JsfUtil.PersistAction.CREATE) {
                    getFacade().create(selected);
                    sucessPersistencia = true;
                } else if (persistAction == JsfUtil.PersistAction.UPDATE) {
                    getFacade().edit(selected);
                    sucessPersistencia = true;
                } else if (persistAction == JsfUtil.PersistAction.DELETE) {
                    getFacade().remove(selected);
                    sucessPersistencia = true;
                } else {
                    JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccuredType"));
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
        return sucessPersistencia;
    }

    public void definirCredencialLogin() {
        //String summary = loginCpf ? "CPF" : "Email";
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
        selected = new Usuario();
    }

    public String processLoginSucess(Usuario usuario) {
        if (usuario.getUsrPermissaoacesso().equals("DIRETORIA")) {
            return "/admin/usuario/List.xhtml?faces-redirect=true";
        } else {
            return "/admin/livro/List.xhtml?faces-redirect=true";
        }
    }

    public String processLoginCancel() {
        return null;
    }

    /**
     * Método utilizado para buscar uma usuario passando o email
     *
     * @param usuarioEmail
     * @return usuario
     */
    public Usuario pesquisarUsuarioUsuarioEmail(String usuarioEmail) {
        return ejbFacade.findByUsrUsuarioEmail(usuarioEmail);
    }

    /**
     * Método utilizado para buscar uma usuario passando o login.
     *
     * @param usuarioLogin
     * @return usuario
     */
    public Usuario pesquisarUsuarioUsuarioCPF(String usuarioLogin) {
        return ejbFacade.findByUsrUsuarioLogin(usuarioLogin);
    }

    public String returnApplicationURL() {
        utilSession = new UtilSession();
        return utilSession.montarURLComHostnameteServletPath();
    }

    public Usuario getSelected() {
        return selected;
    }

    public void setSelected(Usuario selected) {
        this.selected = selected;
    }

    public Usuario getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(Usuario selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    public UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public boolean isSucessPersistencia() {
        return sucessPersistencia;
    }

    public void setSucessPersistencia(boolean sucessPersistencia) {
        this.sucessPersistencia = sucessPersistencia;
    }

    public boolean isLoginPorDsLogin() {
        return loginPorDsLogin;
    }

    public void setLoginPorDsLogin(boolean loginPorDsLogin) {
        this.loginPorDsLogin = loginPorDsLogin;
    }

    public String getLoginOrEmail() {
        return loginOrEmail;
    }

    public void setLoginOrEmail(String loginOrEmail) {
        this.loginOrEmail = loginOrEmail;
    }

}
