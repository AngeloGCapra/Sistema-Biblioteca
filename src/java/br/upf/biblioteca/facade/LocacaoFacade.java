package br.upf.biblioteca.facade;

import br.upf.biblioteca.entity.Locacao;
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
public class LocacaoFacade extends AbstractFacade<Locacao> {

    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;
    private List<Locacao> entityList;
    private final Logger logger = Logger.getLogger(LocacaoFacade.class.getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LocacaoFacade() {
        super(Locacao.class);
    }
    
    /**
     * Método utilizado para buscar uma lista ordenada (DESC)
     *
     * @return
     */
    public List<Locacao> findAllOrderByDataDevolucao() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createNamedQuery("Locacao.findAllOrderByDataDevolucao");
            entityList = (List<Locacao>) query.getResultList();
        } catch (Exception e) {
            logger.error("Erro: " + e);
        }
        return entityList;
    }
    
}
