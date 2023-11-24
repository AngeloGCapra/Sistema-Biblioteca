package br.upf.biblioteca.controller;

import br.upf.biblioteca.controller.enumeration.UsuariopermissaoacessoEnumController;
import br.upf.biblioteca.entity.Usuario;
import br.upf.biblioteca.controller.util.JsfUtil;
import br.upf.biblioteca.controller.util.JsfUtil.PersistAction;
import br.upf.biblioteca.facade.UsuarioFacade;
import br.upf.biblioteca.service.CommonService;
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

    private List<Usuario> items = null;
    private Usuario selected;
    private List<Usuario> filteredUsuario;
    private List<Boolean> listIsTrue;

    private Date minDateRecSenha;
    private Date maxDateRecSenha;

    private final CommonService commonService = new CommonService();
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

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Usuario> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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

    public List<Boolean> getListIsTrue() {
        return listIsTrue;
    }

    public void setListIsTrue(List<Boolean> listIsTrue) {
        this.listIsTrue = listIsTrue;
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
     * @return Pessoa
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
