package br.upf.biblioteca.entity.enumeration;

import java.util.ResourceBundle;

public enum UsuariopermissaoacessoEnum {
    DIRETORIA(ResourceBundle.getBundle("/Bundle").getString("UsuarioPermissaoEnum_Diretoria")),
    BIBLIOTECARIO(ResourceBundle.getBundle("/Bundle").getString("UsuarioPermissaoEnum_Bibliotecario")),
    PROFESSOR(ResourceBundle.getBundle("/Bundle").getString("UsuarioPermissaoEnum_Professor"));
        
    private final String value;

    private UsuariopermissaoacessoEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
