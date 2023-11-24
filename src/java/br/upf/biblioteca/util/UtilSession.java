package br.upf.biblioteca.util;

import br.upf.biblioteca.entity.Usuario;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author
 */
public class UtilSession {

    // Captura a sessão do contexto criado pelo Java Server Faces
    private final FacesContext context = FacesContext.getCurrentInstance();
    private final javax.servlet.http.HttpSession session = (javax.servlet.http.HttpSession) context.getExternalContext().getSession(false);
    private final HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    private static final Logger logger = Logger.getLogger(UtilSession.class.getName());

    /**
     * Método responsável por setar o usuário selecionado.
     *
     * @param usuario
     */
    public void setUsuarioSelecionado(Usuario usuario) {
        session.setAttribute("selecionado-usuario", usuario);
    }

    /**
     * Método utilizado para buscar o usuário selecionado.
     *
     * @return
     */
    public Usuario getUsuarioSelecionado() {
        return (Usuario) session.getAttribute("selecionado-usuario");
    }

    /**
     * Método utilizado para buscar a permissao de acesso do usuário logado.
     *
     * @return
     */
    public String getPermissaoacessoUsuarioLogado() {
        return (String) session.getAttribute("logado-permissao-acesso");
    }

    /**
     * Método utilizado para buscar a permissao de acesso do usuário logado.
     *
     * @param permissaoacesso
     */
    public void setPermissaoacessoUsuarioLogado(String permissaoacesso) {
        session.setAttribute("logado-permissao-acesso", permissaoacesso);
    }

    /**
     * Método utilizado para buscar o usuário logado.
     *
     * @return
     */
    public Usuario getUsuarioLogado() {
        return (Usuario) session.getAttribute("logado-usuario");
    }

    /**
     * Busca a permissão de acesso a tela.
     *
     * @return
     */
    public String getTelaPermissao() {
        return (String) session.getAttribute("tela-permissao");
    }

    /**
     * Define a permissao da tela.
     *
     * @param usrPermissao
     */
    public void setTelaPermissao(String usrPermissao) {
        session.setAttribute("tela-permissao", usrPermissao);
    }

    /**
     * Método responsável por setar um email de um usuário na sessão.
     *
     * @param dsEmail
     */
    public void setUsuarioEmail(String dsEmail) {
        session.setAttribute("logado-usrEmail", dsEmail);
    }

    /**
     * Método utilizado para buscar o email do usuário na sessão.
     *
     * @return
     */
    public String getUsuarioEmail() {
        return (String) session.getAttribute("logado-usrEmail");
    }

    /**
     * Busca o caminho completo da URL a partir do "http" da página em execução
     * Ex: http://localhost:8080/plifestyle/faces/lbiCfa.xhtml
     *
     * @return
     */
    public String buscarURLCompleto() {
        return request.getRequestURL().toString();
    }

    /**
     * Método que monta a URL com IP até o servletPath. Ex:
     * http://192.168.1.1:8080/plifestyle/faces
     *
     * @return
     */
    public String montarURLComIPAteServletPath() {
        String url = "http://" + buscarIpServer() + ":" + getServerPort() + getContextPath() + getServletPath();
        return url;
    }

    /**
     * Método que monta a URL com Hostname até o servletPath. Ex:
     * http://localhost:8080/plifestyle/faces
     *
     * @return
     */
    public String montarURLComHostnameteServletPath() {
        String url = "http://" + getServerHost() + ":" + getServerPort() + getContextPath() + getServletPath();
        return url;
    }

    /**
     * Método que monta a URL com Hostname até o servletPath. Ex:
     * https://localhost:8080/plifestyle/faces
     *
     * @return
     */
    public String montarURLComHostnameteServletPathHttps() {
        String url = "https://" + getServerHost() + ":" + getServerPort() + getContextPath() + getServletPath();
        if (url.startsWith("https://localhost")) {
            url = url.replace("https://localhost", "http://localhost");
        }
        return url;
    }

    /**
     * Método que monta a URL com Hostname até o servletPath. Ex:
     * http://localhost:8080/plifestyle
     *
     * @return
     */
    public String montarURLComHostnameteServlet() {
        String url = "http://" + getServerHost() + ":" + getServerPort() + getContextPath();
        return url;
    }

    /**
     * Método utilizado para pegar o IP da máquina servidor
     *
     * @return
     */
    public String buscarIpServer() {
        String ip = null;
        try {
            // System.out.println(InetAddress.getLocalHost().getHostAddress());
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.error("Erro: " + e);
        }
        return ip;
    }

    /**
     * Método utilizado para pegar o Nome do servidor máquina servidor ex.:
     * DESKTOP-GQQ3GH6
     *
     * @return
     */
    public String buscarServerName() {
        String host = null;
        try {
            //System.out.println(InetAddress.getLocalHost().getHostName());
            host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.error("Erro: " + e);
        }
        return host;
    }

    public FacesContext getContext() {
        return context;
    }

    public HttpSession getSession() {
        return session;
    }

    public String getServerHost() {
        return request.getServerName(); //"localhost" 
    }

    public Integer getServerPort() {
        return request.getServerPort(); // "8080"
    }

    public String getContextPath() {
        return request.getContextPath(); // "/plifestyle"
    }

    public String getServletPath() {
        return request.getServletPath(); // "/faces" 
    }

    public String getUrlContext() {
        return request.getRequestURI(); // O caminho completo da página atual a partir do contexto
    }

}
