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
 * Controlador JPA para gestionar la persistencia de objetos
 * {@link TorneoXJugador}. Proporciona métodos para crear, leer, actualizar y
 * eliminar registros en la base de datos.
 */
public class TorneoXJugadorJpaController {

    //Creamos:
    /**
     * Constructor por defecto que inicializa la conexión con la base de datos.
     * Se utiliza la unidad de persistencia definida en `persistence.xml`.
     */
    public TorneoXJugadorJpaController() {
        //a createEntityManagerFactory, el entra el nombre d ela UP que nos hemos creado en el persistence.xml
        emf = Persistence.createEntityManagerFactory("PU_proyecto");
    }

    /**
     * Constructor que permite especificar una fábrica de entidades para la
     * persistencia.
     *
     * @param emf Fábrica de administradores de entidades.
     */
    public TorneoXJugadorJpaController(EntityManagerFactory emf) {
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
     * Crea una nueva relación entre un torneo y un jugador en la base de datos.
     *
     * @param txj Objeto {@link TorneoXJugador} a persistir.
     */
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

    /**
     * Modifica un registro existente de una relación entre torneo y jugador en
     * la base de datos.
     *
     * @param txj Objeto {@link TorneoXJugador} con la información actualizada.
     * @throws NonexistentEntityException Si el registro no existe en la base de
     * datos.
     * @throws Exception Si ocurre un error en la transacción.
     */
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

    /**
     * Elimina un registro de la relación entre torneo y jugador de la base de
     * datos por su ID.
     *
     * @param id Identificador de la relación a eliminar.
     * @throws NonexistentEntityException Si el registro no existe en la base de
     * datos.
     */
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

    /**
     * Obtiene una lista con todas las relaciones entre torneos y jugadores
     * almacenadas en la base de datos.
     *
     * @return Lista de objetos {@link TorneoXJugador}.
     */
    public List<TorneoXJugador> findTorneoXJugadorEntities() {
        return findTorneoXJugadorEntities(true, -1, -1);
    }

    /**
     * Obtiene una lista de relaciones entre torneos y jugadores con paginación.
     *
     * @param maxResults Número máximo de resultados a obtener.
     * @param firstResult Índice del primer resultado a recuperar.
     * @return Lista de objetos {@link TorneoXJugador}.
     */
    public List<TorneoXJugador> findTorneoXJugadorEntities(int maxResults, int firstResult) {
        return findTorneoXJugadorEntities(false, maxResults, firstResult);
    }

    /**
     * Método privado que realiza la consulta de relaciones entre torneos y
     * jugadores con o sin paginación.
     *
     * @param all Si es `true`, devuelve todos los registros. Si es `false`, usa
     * paginación.
     * @param maxResults Máximo número de resultados a obtener.
     * @param firstResult Índice del primer resultado a recuperar.
     * @return Lista de objetos {@link TorneoXJugador} obtenidos de la base de
     * datos.
     */
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

    /**
     * Busca una relación entre un torneo y un jugador en la base de datos por
     * su ID.
     *
     * @param id Identificador de la relación entre torneo y jugador.
     * @return Objeto {@link TorneoXJugador} correspondiente al ID
     * proporcionado, o `null` si no existe.
     */
    public TorneoXJugador findTorneoXJugador(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TorneoXJugador.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene la cantidad total de relaciones entre torneos y jugadores
     * almacenadas en la base de datos.
     *
     * @return Número total de registros en la tabla TorneoXJugador.
     */
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
