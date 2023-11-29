package br.upf.biblioteca.facade;

import br.upf.biblioteca.entity.Aluno;
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
public class AlunoFacade extends AbstractFacade<Aluno> {

    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;
    private List<Aluno> entityList;
    private final Logger logger = Logger.getLogger(AlunoFacade.class.getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlunoFacade() {
        super(Aluno.class);
    }
    
    /**
     * Método utilizado para buscar uma lista ordenada
     *
     * @return
     */
    public List<Aluno> findAllOrderByNome() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createNamedQuery("Aluno.findAllOrderByNome");
            entityList = (List<Aluno>) query.getResultList();
        } catch (Exception e) {
            logger.error("Erro: " + e);
        }
        return entityList;
    }
    
}
