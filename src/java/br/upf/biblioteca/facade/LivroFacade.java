package br.upf.biblioteca.facade;

import br.upf.biblioteca.entity.Livro;
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
public class LivroFacade extends AbstractFacade<Livro> {

    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;
    private List<Livro> entityList;
    private final Logger logger = Logger.getLogger(LivroFacade.class.getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LivroFacade() {
        super(Livro.class);
    }
    
    /**
     * Método utilizado para buscar uma lista ordenada
     *
     * @return
     */
    public List<Livro> findAllOrderByNomeLivro() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createNamedQuery("Livro.findAllOrderByNomeLivro");
            entityList = (List<Livro>) query.getResultList();
        } catch (Exception e) {
            logger.error("Erro: " + e);
        }
        return entityList;
    }
    
}
