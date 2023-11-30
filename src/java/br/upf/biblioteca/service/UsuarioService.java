package br.upf.biblioteca.service;

import br.upf.biblioteca.entity.Usuario;
import br.upf.biblioteca.util.Criptografia;
import br.upf.biblioteca.util.Email;
import br.upf.biblioteca.util.Util;
import java.util.Date;
import org.apache.log4j.Logger;

public class UsuarioService {

    private final Email email = new Email();
    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());
    
    public UsuarioService() {
    }

    public Usuario prepareCreate(Usuario usr) {
        // Ajustando a data de nascimento...
        Date dataNascimento = Util.tratarDatanascimentoDate(usr.getUsrDatanascimento());
        usr.setUsrDatanascimento(dataNascimento);

        // Email com letra minuscula...
        usr.setDsEmail(usr.getDsEmail().toLowerCase());

        // Criptografa o usrUsuarioLogin (login) para ser armazenado...
        usr.setUsrUsuarioLogin(criptografarValue(usr.getDsLogin()));

        // Criptografar o usrUsuarioEmail (email) para ser armazenado
        usr.setUsrUsuarioEmail(criptografarUsuario(usr.getDsEmail()));

        /* Ao cadastrar o usuário sempre será definido como professor, quando necessário mudar a permissão, 
           somente o administrador consegue fazer a mudança. */
//        usr.setUsrPermissaoacesso(UsuariopermissaoacessoEnum.PROFESSOR.toString());
        
        // Criptografa o dsSenha para ser armazenado...
        usr.setDsSenha(criptografarValue(usr.getDsSenha()));
        
        return usr;
    }

    public Usuario prepareUpdate(Usuario usr) {
        // Email com letra minuscula...
        usr.setDsEmail(usr.getDsEmail().toLowerCase());

        // Criptografa o dsSenha para ser armazenado...
//        usr.setDsSenha(criptografarValue(usr.getDsSenha()));

        // Criptografa o usrUsuarioLogin (login) para ser armazenado...
        usr.setUsrUsuarioLogin(criptografarValue(usr.getDsLogin()));

        // Criptografar o usrUsuarioEmail (email) para ser armazenado
        usr.setUsrUsuarioEmail(criptografarUsuario(usr.getDsEmail()));

        return usr;
    }

    /**
     * Método responsável por criptografar um valor string.
     *
     * @param value
     * @return
     */
    public String criptografarValue(String value) {
        String valueCriptografado = null;

        if (value != null && !value.equals("")) {
            valueCriptografado = Criptografia.criptografar(value);
        }

        return valueCriptografado;
    }

    private String criptografarUsuario(String usuario) {
        if (usuario != null && !usuario.equals("")) {
            usuario = criptografarValue(usuario.toLowerCase());
        }

        return usuario;
    }

    /**
     * Método utilizado para autenticar a senha de um usuário a partir de uma
     * senha recebida e a senha gravada em banco.
     *
     * @param senha
     * @param senhaDB
     * @return
     */
    public boolean autenticarUsuarioSenha(String senha, String senhaDB) {
        boolean autenticacaoValida = false;

        /* Se o usuário informado é igual ao usuário armazenado no banco de dados
           e a senha informada é a mesma que a senha armazenada no banco de dados,
           então o login é válido. */
        if (senha.equals(senhaDB)) {
            autenticacaoValida = true;
        }

        return autenticacaoValida;
    }

    /**
     * Método responsável por validar o tamanho da senha...
     *
     * @param senha
     * @return
     */
    public boolean validarTamanhoSenha(String senha) {
        boolean senhaValida = true;
        if (senha.length() < 6) { // Senha no mínimo com 6 caracteres...
            senhaValida = false;
        }
        return senhaValida;
    }

    public void enviarEmailNovaSenha(Usuario usuario, String senhaNova) {
        String assunto = "Sistema Biblioteca: Nova Senha";
        String msg = email.mensagemHtmlNovaSenha(usuario.getNmNome(), senhaNova);

        //Executando a ação em segundo plano para não aguardar resposta
        new Thread() {
            @Override
            public void run() {
                //enviando email....
                boolean sucessEmail = Email.emailHTML(
                        usuario.getDsEmail(),
                        msg,
                        assunto,
                        Email.USER_FUNATI_CONTATO,
                        Email.PASSWD_FUNATI_ACCOUNT,
                        Email.USER_FUNATI_ACCOUNT,
                        "Nova Senha");
                if (sucessEmail) {
                    //  System.out.println("Sucesso no envio do e-mail para " + usuario.getPesEmail());
                    // logger.info("Sucesso no envio do e-mail de nova senha para " + usuario.getPesEmail());
                } else {
                    System.out.println("FALHA na tentativa de enviar email para " + usuario.getDsEmail());
                    logger.info("FALHA na tentativa de enviar email de nova senha para " + usuario.getDsEmail());
                }
            }
        }.start();
    }

}
