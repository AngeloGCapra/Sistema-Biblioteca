package br.upf.biblioteca.service;

import br.upf.biblioteca.entity.Usuario;
import br.upf.biblioteca.util.Criptografia;
import br.upf.biblioteca.util.Email;
import org.apache.log4j.Logger;

public class UsuarioService {
    
    private final Email email = new Email();
    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());

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
                    //  System.out.println("Sucesso no envio do e-mail para " + pessoa.getPesEmail());
                    // logger.info("Sucesso no envio do e-mail de nova senha para " + pessoa.getPesEmail());
                } else {
                    System.out.println("FALHA na tentativa de enviar email para " + usuario.getDsEmail());
                    logger.info("FALHA na tentativa de enviar email de nova senha para " + usuario.getDsEmail());
                }
            }
        }.start();
    }

}
