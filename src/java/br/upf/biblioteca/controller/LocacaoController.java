package br.upf.biblioteca.controller;

import br.upf.biblioteca.entity.Locacao;
import br.upf.biblioteca.controller.util.JsfUtil;
import br.upf.biblioteca.controller.util.JsfUtil.PersistAction;
import br.upf.biblioteca.facade.LocacaoFacade;
import br.upf.biblioteca.service.CommonService;

import java.io.Serializable;
import java.util.List;
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
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

@Named("locacaoController")
@SessionScoped
public class LocacaoController implements Serializable {

    @EJB
    private br.upf.biblioteca.facade.LocacaoFacade ejbFacade;
    private List<Locacao> items = null;
    private Locacao selected;
    private List<Locacao> filteredLocacao;
    private List<Boolean> listIsTrue;
    
    private final CommonService commonService = new CommonService();

    public LocacaoController() {
    }

    public Locacao getSelected() {
        return selected;
    }

    public void setSelected(Locacao selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LocacaoFacade getFacade() {
        return ejbFacade;
    }

    public Locacao prepareCreate() {
        selected = new Locacao();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LocacaoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LocacaoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("LocacaoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Locacao> getItems() {
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

    public Locacao getLocacao(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Locacao> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Locacao> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    public List<Locacao> getFilteredLocacao() {
        return filteredLocacao;
    }

    public void setFilteredLocacao(List<Locacao> filteredLocacao) {
        this.filteredLocacao = filteredLocacao;
    }
    
    /**
     * Buscar todos os registros, ordenando por data. Utilizando as regras
     * para buscar os dados novamente na base de dados.
     *
     * @param isReload
     * @return
     */
    public List<Locacao> findAllOrderByDataDevolucaoIsReload(boolean isReload) {
        if (commonService.reloadItems((items == null), isReload)) {
            items = getFacade().findAllOrderByDataDevolucao();
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
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("LocacaoListForm:datalist");
        if (dataTable != null && dataTable.getFilterBy() != null && !dataTable.getFilterBy().toString().isEmpty()) {
            dataTable.reset();

            PrimeFaces.current().ajax().update("LocacaoListForm:datalist");
        }
    }

    public void cancelar() {
        selected = null;
        items = null;
    }

    @FacesConverter(forClass = Locacao.class)
    public static class LocacaoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LocacaoController controller = (LocacaoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "locacaoController");
            return controller.getLocacao(getKey(value));
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
            if (object instanceof Locacao) {
                Locacao o = (Locacao) object;
                return getStringKey(o.getCdLocacao());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Locacao.class.getName()});
                return null;
            }
        }

    }

}
