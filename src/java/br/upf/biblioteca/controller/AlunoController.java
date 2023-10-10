package br.upf.biblioteca.controller;

import br.upf.biblioteca.entity.Aluno;
import br.upf.biblioteca.controller.util.JsfUtil;
import br.upf.biblioteca.controller.util.JsfUtil.PersistAction;
import br.upf.biblioteca.facade.AlunoFacade;
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

@Named("alunoController")
@SessionScoped
public class AlunoController implements Serializable {

    @EJB
    private br.upf.biblioteca.facade.AlunoFacade ejbFacade;
    private List<Aluno> items = null;
    private Aluno selected;
    private List<Aluno> filteredAluno;
    private List<Boolean> listIsTrue;
    
    private final CommonService commonService = new CommonService();

    public AlunoController() {
    }

    public Aluno getSelected() {
        return selected;
    }

    public void setSelected(Aluno selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AlunoFacade getFacade() {
        return ejbFacade;
    }

    public Aluno prepareCreate() {
        selected = new Aluno();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AlunoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AlunoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AlunoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Aluno> getItems() {
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

    public Aluno getAluno(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Aluno> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Aluno> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    public List<Aluno> getFilteredAluno() {
        return filteredAluno;
    }

    public void setFilteredAluno(List<Aluno> filteredAluno) {
        this.filteredAluno = filteredAluno;
    }
    
    /**
     * Buscar todos os registros, ordenando por código. Utilizando as regras
     * para buscar os dados novamente na base de dados.
     *
     * @param isReload
     * @return
     */
    public List<Aluno> findAllOrderByNomeIsReload(boolean isReload) {
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
        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("AlunoListForm:datalist");
        if (dataTable != null && dataTable.getFilterBy() != null && !dataTable.getFilterBy().toString().isEmpty()) {
            dataTable.reset();

            PrimeFaces.current().ajax().update("AlunoListForm:datalist");
        }
    }

    public void cancelar() {
        selected = null;
        items = null;
    }

    @FacesConverter(forClass = Aluno.class)
    public static class AlunoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AlunoController controller = (AlunoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "alunoController");
            return controller.getAluno(getKey(value));
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
            if (object instanceof Aluno) {
                Aluno o = (Aluno) object;
                return getStringKey(o.getCdAluno());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Aluno.class.getName()});
                return null;
            }
        }

    }

}
