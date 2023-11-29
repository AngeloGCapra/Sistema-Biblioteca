package br.upf.biblioteca.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {

    private static MessageDigest md = null;

    // Método estático para geração do algoritmo de criptografia
    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Método estático para gerar a chave criptografada.
    private static char[] hexCodes(byte[] text) {
        char[] hexOutput = new char[text.length * 2];
        String hexString;
        
        for (int i = 0; i < text.length; i++) {
            hexString = "00" + Integer.toHexString(text[i]);
            //hexString.toUpperCase().getChars(hexString.length() - 2, 
            hexString.getChars(hexString.length() - 2,
                    hexString.length(), hexOutput, i * 2);
        }
        
        return hexOutput;
    }

    /**
     * Método público que recebe o texto a ser criptografado.
     * 
     * @param pwd
     * @return 
     */
    public static String criptografar(String pwd) {
        if (md != null) {
            return new String(hexCodes(md.digest(pwd.getBytes())));
        }
        
        return null;
    }

    // Para fazer chamadas a esta classe, fazemos o seguinte:
    public static void main(String[] args) {
        String senha = "angelo@gmail.com";
        System.out.println(Criptografia.criptografar(senha));
    }

}
