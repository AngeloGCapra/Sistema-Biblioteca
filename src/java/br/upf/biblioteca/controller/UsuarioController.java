package br.upf.biblioteca.controller;

import br.upf.biblioteca.controller.enumeration.UsuariopermissaoacessoEnumController;
import br.upf.biblioteca.entity.Usuario;
import br.upf.biblioteca.controller.util.JsfUtil;
import br.upf.biblioteca.controller.util.JsfUtil.PersistAction;
import br.upf.biblioteca.entity.TipoUsuario;
import br.upf.biblioteca.entity.enumeration.UsuariopermissaoacessoEnum;
import br.upf.biblioteca.facade.UsuarioFacade;
import br.upf.biblioteca.service.CommonService;
import br.upf.biblioteca.service.UsuarioService;
import br.upf.biblioteca.util.Util;
import br.upf.biblioteca.util.UtilSession;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

@Named("usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    @EJB
    private br.upf.biblioteca.facade.UsuarioFacade ejbFacade;

    @EJB
    private br.upf.biblioteca.facade.TipoUsuarioFacade ejbTipoUsuarioFacade;

    private List<Usuario> items = null;
    private Usuario selected;
    private List<Usuario> filteredUsuario;
    private List<Boolean> listIsTrue;

    private TipoUsuario tipoUsuario;
    private Date minDateRecSenha;
    private Date maxDateRecSenha;
    private boolean sucessPersistencia = false;

    private final CommonService commonService = new CommonService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final UtilSession utilSession = new UtilSession();

    public UsuarioController() {
    }

    public Usuario getSelected() {
        return selected;
    }

    public void setSelected(Usuario selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UsuarioFacade getFacade() {
        return ejbFacade;
    }

    public Usuario prepareCreate() {
        selected = new Usuario();
        initializeEmbeddableKey();
        return selected;
    }

    public Usuario prepareCreateLogin() {
        selected = new Usuario();
        selected.setCdTipoUsuario(ejbTipoUsuarioFacade.find(3)); // Tipo usuario "3" = Professor
        selected.setUsrPermissaoacesso(UsuariopermissaoacessoEnum.PROFESSOR.toString());

        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        String msgLog;
        
        // Se o campo de Email estiver vazio, usa o campo de Login parar setar um email
        if (selected.getDsEmail() == null || selected.getDsEmail().equals("")) {
            selected.setDsEmail(selected.getDsLogin() + "@gmail.com");
        }

        // Seta a senha com base na data de nascimento passada
//        if (selected.getDsSenha() == null || selected.getDsSenha().equals("")) {
//            selected.setDsSenha(Util.formatarData(selected.getUsrDatanascimento(), "ddMMyyyy"));
//        }

        // Verifica se o email cadastrado já foi utilizado...
        if (ejbFacade.findByDsEmail(selected.getDsEmail()).getCdUsuario() == null) {
            // Verifica se o login cadastrado já foi utilizado...
            if (ejbFacade.findByDsLogin(selected.getDsLogin()).getCdUsuario() == null) {
                if (usuarioService.validarTamanhoSenha(selected.getDsSenha())) { // Validando tamanho da senha...
                    if (validarDatanascimento(selected.getUsrDatanascimento())) { // Validando login
                        // Se o processo de criação ocorrer com sucesso...
                        if (processCreate()) {
                            selected = new Usuario();
                            PrimeFaces current = PrimeFaces.current();
                            current.executeScript("PF('UsuarioCreateDialog').hide();");
                        }
                    }
                } else {
                    msgLog = ResourceBundle.getBundle("/Bundle").getString("UsuarioTamanhoInvalidoMessage_usrSenha");
                    JsfUtil.addAlertMessage(msgLog);
                }
            } else {
                msgLog = ResourceBundle.getBundle("/Bundle").getString("CreateUsuarioMsgFail_LoginExistente");
                JsfUtil.addAlertMessage(msgLog);
            }
        } else {
            msgLog = ResourceBundle.getBundle("/Bundle").getString("CreateUsuarioMsgFail_EmailExistente");
            JsfUtil.addAlertMessage(msgLog);
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    /**
     * Executa o processo de criação.
     *
     * @return
     */
    private boolean processCreate() {
        boolean sucessProcess = false;
        selected = usuarioService.prepareCreate(selected);

        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"));
        if (sucessPersistencia) {
            items = null;    // Invalidate listIsTrue of items to trigger re-query.

            sucessProcess = true;
        }
        return sucessProcess;
    }

    /**
     * Método responsável por validar se a datanascimento é anterior a data
     * atual.
     *
     * @param datanascimento
     * @return
     */
    private boolean validarDatanascimento(Date datanascimento) {
        String msgLogDatanascimentoInvalida = ResourceBundle.getBundle("/Bundle").getString("UsuarioDatanascimentoInvalidoMessage_usrDatanascimento");
        boolean isValid = false;
        Date Dataatual = Util.buscarDataAtual();

        if (datanascimento.before(Dataatual)) {
            isValid = true;
        } else {
            JsfUtil.addErrorMessage(msgLogDatanascimentoInvalida);
        }
        return isValid;
    }

    public void update() {
        String msgLog;
        
        // Se o campo de Email estiver vazio, usa o campo de Login parar setar um email
        if (selected.getDsEmail() == null || selected.getDsEmail().equals("")) {
            selected.setDsEmail(selected.getDsLogin() + "@gmail.com");
        }

        // Seta a senha com base na data de nascimento passada
//        if (selected.getDsSenha() == null || selected.getDsSenha().equals("")) {
//            selected.setDsSenha(Util.formatarData(selected.getUsrDatanascimento(), "ddMMyyyy"));
//        }

        // Verifica se o email cadastrado já foi utilizado por outro usuário...
        if (ejbFacade.findByDsEmail(selected.getDsEmail()).getCdUsuario() == null || ejbFacade.findByDsEmail(selected.getDsEmail()).getCdUsuario() == selected.getCdUsuario()) {
            if (ejbFacade.findByDsLogin(selected.getDsLogin()).getCdUsuario() == null || ejbFacade.findByDsLogin(selected.getDsLogin()).getCdUsuario() == selected.getCdUsuario()) {
                if (validarDatanascimento(selected.getUsrDatanascimento())) {
                    // Se o processo de edição ocorrer com sucesso...
                    if (processUpdate()) {
                        PrimeFaces current = PrimeFaces.current();
                        current.executeScript("PF('UsuarioEditDialog').hide();");
                    }
                }
            } else {
                msgLog = ResourceBundle.getBundle("/Bundle").getString("CreateUsuarioMsgFail_LoginExistente");
                JsfUtil.addAlertMessage(msgLog);
            }
        } else {
            msgLog = ResourceBundle.getBundle("/Bundle").getString("CreateUsuarioMsgFail_EmailExistente");
            JsfUtil.addAlertMessage(msgLog);
        }

        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    /**
     * Executa o processo de edição.
     *
     * @return
     */
    private boolean processUpdate() {
        boolean sucessProcess = false;
        selected = usuarioService.prepareUpdate(selected);
        
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"));
        if (sucessPersistencia) {
            items = null; // Invalidate listIsTrue of items to trigger re-query.
            sucessProcess = true;
        }
        
        return sucessProcess;
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
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
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    /**
     * Buscar todos os registros, ordenando por nome. Utilizando as regras para
     * buscar os dados novamente na base de dados.
     *
     * @param isReload
     * @return
     */
    public List<Usuario> findAllOrderByNomeIsReload(boolean isReload) {
        if (commonService.reloadItems((items == null), isReload)) {
            items = getFacade().findAllOrderByNome();
        }
        return items;
    }

    public void onToggle(ToggleEvent e) {
        listIsTrue.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }

    //Método que atualiza a dataTable e limpa os filtros das colunas
    public void clearAllFilters() {
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("UsuarioListForm:datalist");
        if (dataTable != null && dataTable.getFilterBy() != null && !dataTable.getFilterBy().toString().isEmpty()) {
            dataTable.reset();

            PrimeFaces.current().ajax().update("UsuarioListForm:datalist");
        }
    }

    public void cancelar() {
        selected = null;
        items = null;
    }

    /**
     * Método que busca o usuário logado que esta na sessão.
     *
     * @return Usuario
     */
    public Usuario getUsuarioLogado() {
        return utilSession.getUsuarioLogado();
    }

    /**
     * @return the minDate p/ Recuperar Senha
     */
    public Date getMinDateRecSenha() {
        minDateRecSenha = Util.buscarDataAPartirDataAtual(-36500).getTime();
        return minDateRecSenha;
    }

    /**
     * @return the maxDate p/ Recuperar Senha
     */
    public Date getMaxDateRecSenha() {
        maxDateRecSenha = Util.buscarDataAPartirDataAtual(0).getTime();
        return maxDateRecSenha;
    }

    public String formatarPermissao(Usuario usuario) {
        String permissaoRetorno = "";
        
        if (usuario != null && usuario.getUsrPermissaoacesso() != null && !usuario.getUsrPermissaoacesso().isEmpty()) {
            Map<String, String> permissaoMap = new HashMap<>();
            UsuariopermissaoacessoEnumController usuariopermissaoacessoEnumController = new UsuariopermissaoacessoEnumController();
            SelectItem[] listaPermissoes = usuariopermissaoacessoEnumController.getPermissoes();
            List<SelectItem> lista = Arrays.asList(listaPermissoes);
            
            for (int i = 0; i < lista.size(); i++) {
                if (usuario.getUsrPermissaoacesso().equals(lista.get(i).getValue().toString())) {
                    permissaoMap.put(lista.get(i).getValue().toString(), lista.get(i).getLabel());
                    permissaoRetorno = permissaoMap.get(usuario.getUsrPermissaoacesso());
                }
            }
        }
        return permissaoRetorno;
    }

    public List<Usuario> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public List<Boolean> getListIsTrue() {
        return listIsTrue;
    }

    public void setListIsTrue(List<Boolean> listIsTrue) {
        this.listIsTrue = listIsTrue;
    }

    public Usuario getUsuario(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Usuario> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Usuario> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public List<Usuario> getFilteredUsuario() {
        return filteredUsuario;
    }

    public void setFilteredUsuario(List<Usuario> filteredUsuario) {
        this.filteredUsuario = filteredUsuario;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getCdUsuario());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Usuario.class.getName()});
                return null;
            }
        }

    }

}
