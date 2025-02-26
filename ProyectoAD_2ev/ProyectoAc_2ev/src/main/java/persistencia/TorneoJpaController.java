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
import persistencia.exceptions.NonexistentEntityException;

/**
 * Controlador JPA para gestionar la persistencia de objetos {@link Torneo}.
 * Proporciona métodos para crear, leer, actualizar y eliminar registros en la
 * base de datos.
 */
public class TorneoJpaController {

    //Creamos:
    /**
     * Constructor por defecto que inicializa la conexión con la base de datos.
     * Se utiliza la unidad de persistencia definida en `persistence.xml`.
     */
    public TorneoJpaController() {
        //a createEntityManagerFactory, el entra el nombre d ela UP que nos hemos creado en el persistence.xml
        emf = Persistence.createEntityManagerFactory("PU_proyecto");
    }

    /**
     * Constructor que permite especificar una fábrica de entidades para la
     * persistencia.
     *
     * @param emf Fábrica de administradores de entidades.
     */
    public TorneoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    /**
     * Obtiene un nuevo {@link EntityManager} para interactuar con la base de
     * datos.
     *
     * @return Un administrador de entidades para realizar operaciones de
     * persistencia.
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Crea un nuevo torneo en la base de datos.
     *
     * @param torneo Objeto {@link Torneo} a persistir.
     */
    public void create(Torneo torneo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(torneo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Modifica un registro existente de un torneo en la base de datos.
     *
     * @param torneo Objeto {@link Torneo} con la información actualizada.
     * @throws NonexistentEntityException Si el registro no existe en la base de
     * datos.
     * @throws Exception Si ocurre un error en la transacción.
     */
    public void edit(Torneo torneo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            torneo = em.merge(torneo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = torneo.getId_t();
                if (findTorneo(id) == null) {
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

    /**
     * Elimina un torneo de la base de datos por su ID.
     *
     * @param id Identificador del torneo a eliminar.
     * @throws NonexistentEntityException Si el torneo no existe en la base de
     * datos.
     */
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Torneo torneo;
            try {
                torneo = em.getReference(Torneo.class, id);
                torneo.getId_t();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The Torneo with id " + id + " no longer exists.", enfe);
            }
            em.remove(torneo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Obtiene una lista con todos los torneos almacenados en la base de datos.
     *
     * @return Lista de objetos {@link Torneo}.
     */
    public List<Torneo> findTorneoEntities() {
        return findTorneoEntities(true, -1, -1);
    }

    /**
     * Obtiene una lista de torneos con paginación.
     *
     * @param maxResults Número máximo de resultados a obtener.
     * @param firstResult Índice del primer resultado a recuperar.
     * @return Lista de objetos {@link Torneo}.
     */
    public List<Torneo> findTorneoEntities(int maxResults, int firstResult) {
        return findTorneoEntities(false, maxResults, firstResult);
    }

    /**
     * Método privado que realiza la consulta de torneos con o sin paginación.
     *
     * @param all Si es `true`, devuelve todos los registros. Si es `false`, usa
     * paginación.
     * @param maxResults Máximo número de resultados.
     * @param firstResult Primer resultado a recuperar.
     * @return Lista de objetos {@link Torneo} obtenidos de la base de datos.
     */
    private List<Torneo> findTorneoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Torneo.class));
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
     * Busca un torneo en la base de datos por su ID.
     *
     * @param id Identificador del torneo.
     * @return Objeto {@link Torneo} correspondiente al ID proporcionado, o
     * `null` si no existe.
     */
    public Torneo findTorneo(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Torneo.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene la cantidad total de torneos almacenados en la base de datos.
     *
     * @return Número total de torneos registrados.
     */
    public int getTorneoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Torneo> rt = cq.from(Torneo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
