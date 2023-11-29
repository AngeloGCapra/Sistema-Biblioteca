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
     * Método utilizado para buscar uma lista ordenada de todos usuários.
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

    /**
     * Método que busca um usuário por DsLogin.
     *
     * @param dsLogin
     * @return
     */
    public Usuario findByDsLogin(String dsLogin) {
        Usuario usuario = new Usuario();
        try {
            //Reutilizando a querie criada na classe Usuário.
            Query query = getEntityManager().createNamedQuery("Usuario.findByDsLogin");
            // Adicionando parâmetro...
            query.setParameter("dsLogin", dsLogin);
            query.setMaxResults(1);

            // Verifica se existe algum resultado para não gerar excessão
            if (!query.getResultList().isEmpty()) {
                usuario = (Usuario) query.getSingleResult();
            } else {
                logger.info("Nenhum resultado localizado para findByDsLogin");
            }
        } catch (Exception e) {
            logger.error("ERROR: " + e);
        }
        return usuario;
    }

    /**
     * Método que busca uma usuário por usrUsuarioLogin.
     *
     * @param usrUsuarioLogin
     * @return
     */
    public Usuario findByUsrUsuarioLogin(String usrUsuarioLogin) {
        Usuario usuario = new Usuario();
        try {
            // Reutilizando a querie criada na classe Usuário.
            Query query = getEntityManager().createNamedQuery("Usuario.findByUsrUsuarioLogin");
            // Adicionando parâmetro...
            query.setParameter("usrUsuarioLogin", usrUsuarioLogin);
            query.setMaxResults(1);

            // Verifica se existe algum resultado para não gerar excessão
            if (!query.getResultList().isEmpty()) {
                usuario = (Usuario) query.getSingleResult();
            } else {
                logger.info("Nenhum resultado localizado para findByPesUsuarioCpf");
            }
        } catch (Exception e) {
            logger.error("ERROR: " + e);
        }
        return usuario;
    }

    /**
     * Método utilizado para buscar usuário por DsEmail.
     *
     * @param dsEmail
     * @return
     */
    public Usuario findByDsEmail(String dsEmail) {
        Usuario usuario = new Usuario();
        dsEmail = dsEmail.toLowerCase();
        try {
            // Reutilizando a querie criada na classe Usuário.
            Query query = getEntityManager().createNamedQuery("Usuario.findByDsEmail");
            // Adicionando parâmetro...
            query.setParameter("dsEmail", dsEmail);
            query.setMaxResults(1);

            // Verifica se existe algum resultado para não gerar excessão
            if (!query.getResultList().isEmpty()) {
                usuario = (Usuario) query.getSingleResult();
            } else {
                logger.info("Nenhum resultado localizado para findByDsEmail");
            }
        } catch (Exception e) {
            logger.error("ERROR: " + e);
        }
        return usuario;
    }

    /**
     * Método utilizado para buscar usuário por usrUsuarioEmail.
     *
     * @param usrUsuarioEmail
     * @return
     */
    public Usuario findByUsrUsuarioEmail(String usrUsuarioEmail) {
        Usuario usuario = new Usuario();
        usrUsuarioEmail = usrUsuarioEmail.toLowerCase();
        try {
            // Reutilizando a querie criada na classe Usuário.
            Query query = getEntityManager().createNamedQuery("Usuario.findByUsrUsuarioEmail");
            // Adicionando parâmetro...
            query.setParameter("usrUsuarioEmail", usrUsuarioEmail);
            query.setMaxResults(1);

            // Verifica se existe algum resultado para não gerar excessão
            if (!query.getResultList().isEmpty()) {
                usuario = (Usuario) query.getSingleResult();
            } else {
                logger.info("Nenhum resultado localizado para findByUsrUsuarioEmail");
            }
        } catch (Exception e) {
            logger.error("ERROR: " + e);
        }
        return usuario;
    }
    
    public Usuario findUsuarioByUsuarioEmailDataNascimento(Usuario usuario) {
        Usuario usr = new Usuario();
        try {
            // Reutilizando a querie criada na classe Pessoa.
            Query query = getEntityManager().createNamedQuery("Usuario.findUsuarioByUsuarioEmailDataNascimento");
            // Adicionando parâmetro...
            query.setParameter(1, usuario.getUsrUsuarioEmail());
            query.setParameter(2, usuario.getUsrDatanascimento());

            // Verifica se existe algum resultado para não gerar excessão
            if (!query.getResultList().isEmpty()) {
                usr = (Usuario) query.getSingleResult();
            } else {
                logger.info("Nenhum resultado localizado para findUsuarioByUsuarioEmailDataNascimento");
            }
        } catch (Exception e) {
            logger.error("ERROR: " + e);
        }
        return usr;
    }

}
