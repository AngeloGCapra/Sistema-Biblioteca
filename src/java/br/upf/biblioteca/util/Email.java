package br.upf.biblioteca.util;

import java.io.Serializable;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

public class Email implements Serializable {

    public static final String USER_FUNATI_CONTATO = "contatosistemabiblioteca@gmail.com";
    public static final String USER_FUNATI_ACCOUNT = "contatosistemabiblioteca@gmail.com";
    public static final String PASSWD_FUNATI_ACCOUNT = "b7d70b2192aa739f413e156357993619";    
    private static final Logger logger = Logger.getLogger(Email.class.getName());

    /**
     * Método utilizado para envio de email no formato HTML.
     *
     * @param user
     * @param passwd
     * @param emailDestinatario
     * @param emailRemetente
     * @param decricaoRemetente
     * @param assunto
     * @param mensagem
     * @return
     */
    public static boolean emailHTML(
            String emailDestinatario, String mensagem, String assunto, String user, String passwd,
            String emailRemetente, String decricaoRemetente) {
        boolean processSucess = false;
        //TODO é necessário adequar a implementação para necessidade, assim como foi feito no método enviarEmailSimples
        try {
            // Cria o e-mail 
            HtmlEmail email = new HtmlEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(587);
            email.setSSLOnConnect(true);
            email.setAuthenticator(new DefaultAuthenticator(user, passwd));
            email.setDebug(false);
            email.addTo(emailDestinatario); //destinatário  
            email.setFrom(emailRemetente, decricaoRemetente); // remetente 
            email.setSubject(assunto); // assunto do e-mail 
            email.setHtmlMsg(mensagem);
            // envia o e-mail 
            email.send();
            processSucess = true;
        } catch (EmailException e) {
            //System.out.println("Error no envio de email HTML: " + e);
            logger.error("Error no envio de email HTML: " + e);
        }
        return processSucess;
    }

    /**
     * Método utilizado para enviar email contendo a nova senha.
     *
     * @param senha
     * @param nome
     * @return
     */
    public String mensagemHtmlNovaSenha(String nome, String senha) {
        String mens
                = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n"
                + "        <title>Funati</title>\n"
                + "    </head>\n"
                + "    <body>\n"
                + "    <p><label>Olá " + nome + "! Sua nova senha é: " + senha + "</label> \n"
                + "    </body>\n"
                + "</html>";
        return mens;
    }

}
