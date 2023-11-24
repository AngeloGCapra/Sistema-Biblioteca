package br.upf.biblioteca.controller.enumeration;

import br.upf.biblioteca.entity.enumeration.UsuariopermissaoacessoEnum;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

@Named("usuariopermissaoacessoEnumController")
@SessionScoped
public class UsuariopermissaoacessoEnumController implements Serializable {

    public SelectItem[] getPermissoes() {
        SelectItem[] items = new SelectItem[UsuariopermissaoacessoEnum.values().length];
        int i = 0;
        for (UsuariopermissaoacessoEnum t : UsuariopermissaoacessoEnum.values()) {
            items[i++] = new SelectItem(t, t.getValue());
        }
        return items;
    }

}
