package br.upf.biblioteca.facade;

import br.upf.biblioteca.entity.Autor;
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
public class AutorFacade extends AbstractFacade<Autor> {

    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;
    private List<Autor> entityList;
    private final Logger logger = Logger.getLogger(AutorFacade.class.getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AutorFacade() {
        super(Autor.class);
    }
    
    /**
     * Método utilizado para buscar uma lista ordenada
     *
     * @return
     */
    public List<Autor> findAllOrderByNome() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createNamedQuery("Autor.findAllOrderByNome");
            entityList = (List<Autor>) query.getResultList();
        } catch (Exception e) {
            logger.error("Erro: " + e);
        }
        return entityList;
    }
    
}
