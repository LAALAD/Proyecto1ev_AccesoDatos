
package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logicaNegocio.DatosPersonales;
import persistencia.exceptions.NonexistentEntityException;

public class DatosPersonalesJpaController implements Serializable {
     
    //Creamos:DatosPersonalesJpaController
    public DatosPersonalesJpaController() {
        //a createEntityManagerFactory, el entra el nombre d ela UP que nos hemos creado en el persistence.xml
        emf = Persistence.createEntityManagerFactory("PU_proyecto");
    }

    public DatosPersonalesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DatosPersonales datosP) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(datosP);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DatosPersonales datosP) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            datosP = em.merge(datosP);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = datosP.getId();
                if (findDatosPersonales(id) == null) {
                    throw new NonexistentEntityException("The DatosPersonales with id " + id + " no longer exists.");
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
            DatosPersonales datosP;
            try {
                datosP = em.getReference(DatosPersonales.class, id);
                datosP.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The DatosPersonales with id " + id + " no longer exists.", enfe);
            }
            em.remove(datosP);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DatosPersonales> findDatosPersonalesEntities() {
        return findDatosPersonalesEntities(true, -1, -1);
    }

    public List<DatosPersonales> findDatosPersonalesEntities(int maxResults, int firstResult) {
        return findDatosPersonalesEntities(false, maxResults, firstResult);
    }

    private List<DatosPersonales> findDatosPersonalesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DatosPersonales.class));
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

    public DatosPersonales findDatosPersonales(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DatosPersonales.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatosPersonalesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DatosPersonales> rt = cq.from(DatosPersonales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
