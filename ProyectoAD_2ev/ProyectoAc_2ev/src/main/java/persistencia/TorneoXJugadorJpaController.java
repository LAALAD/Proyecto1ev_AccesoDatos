/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logicaNegocio.Torneo;
import logicaNegocio.TorneoXJugador;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author DAM2_02
 */
public class TorneoXJugadorJpaController {
    
    //Creamos:
    public TorneoXJugadorJpaController() {
        //a createEntityManagerFactory, el entra el nombre d ela UP que nos hemos creado en el persistence.xml
        emf = Persistence.createEntityManagerFactory("PU_proyecto");
    }

    public TorneoXJugadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TorneoXJugador txj) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(txj);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TorneoXJugador txj) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            txj = em.merge(txj);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = txj.getId();
                if (findTorneoXJugador(id) == null) {
                    throw new NonexistentEntityException("The Torneo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TorneoXJugador txj;
            try {
                txj = em.getReference(TorneoXJugador.class, id);
                txj.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The TorneoXJugador with id " + id + " no longer exists.", enfe);
            }
            em.remove(txj);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TorneoXJugador> findTorneoXJugadorEntities() {
        return findTorneoXJugadorEntities(true, -1, -1);
    }

    public List<TorneoXJugador> findTorneoXJugadorEntities(int maxResults, int firstResult) {
        return findTorneoXJugadorEntities(false, maxResults, firstResult);
    }

    private List<TorneoXJugador> findTorneoXJugadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TorneoXJugador.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TorneoXJugador findTorneoXJugador(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TorneoXJugador.class, id);
        } finally {
            em.close();
        }
    }

    public int getTorneoXJugadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Torneo> rt = cq.from(TorneoXJugador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
