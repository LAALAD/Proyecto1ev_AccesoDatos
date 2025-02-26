/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import logicaNegocio.Arbitro;
import persistencia.exceptions.NonexistentEntityException;

/**
 * Clase que gestiona la persistencia de los objetos {@link Arbitro}.
 * Proporciona métodos CRUD para interactuar con la base de datos a través de JPA.
 *
 * @author paulc
 */
public class ArbitroJpaController implements Serializable {
    
    //Creamos:
    /**
     * Constructor por defecto que inicializa la conexión con la base de datos.
     * Se utiliza la unidad de persistencia definida en `persistence.xml`.
     */
    public ArbitroJpaController() {
         //a createEntityManagerFactory, el entra el nombre d ela UP que nos hemos creado en el persistence.xml
        emf = Persistence.createEntityManagerFactory("PU_proyecto");
    }

    /**
     * Constructor que permite especificar una fábrica de entidades para la persistencia.
     * 
     * @param emf Fábrica de administradores de entidades.
     */
    public ArbitroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    /**
     * Obtiene un nuevo {@link EntityManager} para interactuar con la base de datos.
     * 
     * @return Un administrador de entidades para realizar operaciones de persistencia.
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Crea un nuevo registro de árbitro en la base de datos.
     * 
     * @param arbitro El objeto {@link Arbitro} a persistir.
     */
    public void create(Arbitro arbitro) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(arbitro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Modifica un registro existente de un árbitro en la base de datos.
     * 
     * @param arbitro El objeto {@link Arbitro} con los nuevos datos.
     * @throws NonexistentEntityException Si el árbitro no existe en la base de datos.
     * @throws Exception Si ocurre un error en la transacción.
     */
    public void edit(Arbitro arbitro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            arbitro = em.merge(arbitro);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = arbitro.getId();
                if (findArbitro(id) == null) {
                    throw new NonexistentEntityException("The Arbitro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Elimina un árbitro de la base de datos según su ID.
     * 
     * @param id El identificador del árbitro a eliminar.
     * @throws NonexistentEntityException Si el árbitro no existe en la base de datos.
     */
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Arbitro arbitro;
            try {
                arbitro = em.getReference(Arbitro.class, id);
                arbitro.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The Arbitro with id " + id + " no longer exists.", enfe);
            }
            em.remove(arbitro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Obtiene una lista con todos los árbitros almacenados en la base de datos.
     * 
     * @return Lista de objetos {@link Arbitro}.
     */
    public List<Arbitro> findArbitrosEntities() {
        return findArbitrosEntities(true, -1, -1);
    }

    /**
     * Obtiene una lista de árbitros con paginación.
     * 
     * @param maxResults Número máximo de resultados a obtener.
     * @param firstResult Índice del primer resultado a recuperar.
     * @return Lista de objetos {@link Arbitro}.
     */
    public List<Arbitro> findArbitrosEntities(int maxResults, int firstResult) {
        return findArbitrosEntities(false, maxResults, firstResult);
    }

    /**
     * Método privado que realiza la consulta de árbitros con o sin paginación.
     * 
     * @param all Si es `true`, devuelve todos los árbitros. Si es `false`, usa paginación.
     * @param maxResults Máximo número de resultados.
     * @param firstResult Primer resultado a recuperar.
     * @return Lista de árbitros obtenidos de la base de datos.
     */
    private List<Arbitro> findArbitrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Arbitro.class));
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

    /**
     * Busca un árbitro en la base de datos según su ID.
     * 
     * @param id Identificador del árbitro.
     * @return El objeto {@link Arbitro} si se encuentra en la base de datos, de lo contrario, `null`.
     */
    public Arbitro findArbitro(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Arbitro.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene la cantidad total de árbitros almacenados en la base de datos.
     * 
     * @return Número total de árbitros.
     */
    public int getArbitroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Arbitro> rt = cq.from(Arbitro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
