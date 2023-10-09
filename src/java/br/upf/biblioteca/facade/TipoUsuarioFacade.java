package br.upf.biblioteca.facade;

import br.upf.biblioteca.entity.TipoUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author Angelo
 */
@Stateless
public class TipoUsuarioFacade extends AbstractFacade<TipoUsuario> {

    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;
    private List<TipoUsuario> entityList;
    private final Logger logger = Logger.getLogger(TipoUsuarioFacade.class.getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoUsuarioFacade() {
        super(TipoUsuario.class);
    }
    
    /**
     * Método utilizado para buscar uma lista ordenada
     *
     * @return
     */
    public List<TipoUsuario> findAllOrderByDescricao() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createNamedQuery("TipoUsuario.findAllOrderByDescricao");
            entityList = (List<TipoUsuario>) query.getResultList();
        } catch (Exception e) {
            logger.error("Erro: " + e);
        }
        return entityList;
    }
    
}
