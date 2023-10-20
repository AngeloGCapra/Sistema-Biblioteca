package br.upf.biblioteca.facade;

import br.upf.biblioteca.entity.Genero;
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
public class GeneroFacade extends AbstractFacade<Genero> {

    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;
    private List<Genero> entityList;
    private final Logger logger = Logger.getLogger(GeneroFacade.class.getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GeneroFacade() {
        super(Genero.class);
    }
    
    /**
     * Método utilizado para buscar uma lista ordenada
     *
     * @return
     */
    public List<Genero> findAllOrderByDescricao() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createNamedQuery("Genero.findAllOrderByDescricao");
            entityList = (List<Genero>) query.getResultList();
        } catch (Exception e) {
            logger.error("Erro: " + e);
        }
        return entityList;
    }
    
}
