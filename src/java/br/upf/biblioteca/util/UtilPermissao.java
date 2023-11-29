package br.upf.biblioteca.util;

import br.upf.biblioteca.entity.Usuario;
import br.upf.biblioteca.entity.enumeration.UsuariopermissaoacessoEnum;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("utilPermissao")
@SessionScoped
public class UtilPermissao implements Serializable {

    private final UtilSession utilSession = new UtilSession();
    private final String DIRETORIA = UsuariopermissaoacessoEnum.DIRETORIA.toString();
    private final String BIBLIOTECARIO = UsuariopermissaoacessoEnum.BIBLIOTECARIO.toString();
    private final String PROFESSOR = UsuariopermissaoacessoEnum.PROFESSOR.toString();

    private final Usuario usuarioLogado = (Usuario) utilSession.getUsuarioLogado();

    /**
     * Método responsável por validar a permissão de acesso conforme critérios.
     *
     * @param telaPermissao
     * @return
     */
    private boolean validaPermissao(String telaPermissao) {
        boolean permissaoValida = false; //permissão não válida

        if (telaPermissao.equals(usuarioLogado.getUsrPermissaoacesso()) // Se a permissão do usuário é a mesma permissão da tela
                || telaPermissao.equals(PROFESSOR) // Se a tela possui permissão para PROFESSOR (todos usuarios tem permissão para PROFESSOR) 
                || usuarioLogado.getUsrPermissaoacesso().equals(DIRETORIA) // Ou SE o usuário é administrador(diretoria)
                ) {
            permissaoValida = true; //Se ocorreu a validação com sucesso, muda para mermissão valida.
        }
        return permissaoValida;
    }
    
    /**
     * Método responsável por definir a permissão (DIRETORIA) para página e
     * chamar o método para validar a permissão sempre que a mesma for
     * requisitada.
     *
     * @throws IOException
     */
    public void setTelaPermissaoSessionDiretoria() throws IOException {
        // Permissao negada
        if (!validaPermissao(DIRETORIA)) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../../permissao-negada.xhtml");
        }
    }
    
    /**
     * Método responsável por definir a permissão (BIBLIOTECARIO) para página e
     * chamar o método para validar a permissão sempre que a mesma for
     * requisitada.
     *
     * @throws IOException
     */
    public void setTelaPermissaoSessionBibliotecario() throws IOException {
        // Permissao negada
        if (!validaPermissao(BIBLIOTECARIO)) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../../permissao-negada.xhtml");
        }
    }
    
    /**
     * Método responsável por definir a permissão (PROFESSOR) para página e
     * chamar o método para validar a permissão sempre que a mesma for
     * requisitada.
     *
     * @throws IOException
     */
    public void setTelaPermissaoSessionProfessor() throws IOException {
        // Permissao negada
        if (!validaPermissao(PROFESSOR)) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../../permissao-negada.xhtml");
        }
    }
    
    /**
     * Método responsável por definir a permissão (DIRETORIA) para página e
     * chamar o método para validar a permissão sempre que a mesma for
     * requisitada.
     *
     * @throws java.io.IOException
     */
    public boolean getTelaPermissaoSessionDiretoria() throws IOException {
        // Permissao concedida
        if (validaPermissao(DIRETORIA)) {
            return true;
        } // Permissão negada
        else {
            return false;
        }
    }
    
    /**
     * Método responsável por definir a permissão (BIBLIOTECARIO) para página e
     * chamar o método para validar a permissão sempre que a mesma for
     * requisitada.
     *
     * @throws java.io.IOException
     */
    public boolean getTelaPermissaoSessionBibliotecario() throws IOException {
        // Permissao concedida
        if (validaPermissao(BIBLIOTECARIO)) {
            return true;
        } // Permissão negada
        else {
            return false;
        }
    }
    
    /**
     * Método responsável por definir a permissão (PROFESSOR) para página e
     * chamar o método para validar a permissão sempre que a mesma for
     * requisitada.
     *
     * @throws java.io.IOException
     */
    public boolean getTelaPermissaoSessionPROFESSOR() throws IOException {
        // Permissao concedida
        if (validaPermissao(PROFESSOR)) {
            return true;
        } // Permissão negada
        else {
            return false;
        }
    }

}
