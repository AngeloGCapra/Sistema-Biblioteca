package br.upf.biblioteca.facade;

import br.upf.biblioteca.entity.Usuario;
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
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;
    private List<Usuario> entityList;
    private final Logger logger = Logger.getLogger(UsuarioFacade.class.getName());

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    /**
     * Método utilizado para buscar uma lista ordenada
     *
     * @return
     */
    public List<Usuario> findAllOrderByNome() {
        entityList = new ArrayList<>();
        try {
            Query query = getEntityManager().createNamedQuery("Usuario.findAllOrderByNome");
            entityList = (List<Usuario>) query.getResultList();
        } catch (Exception e) {
            logger.error("Erro: " + e);
        }
        return entityList;
    }
    
}
