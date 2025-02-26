/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import logicaNegocio.Jugador;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistencia.exceptions.NonexistentEntityException;

/**
 * Controlador JPA para gestionar la persistencia de objetos {@link Jugador}.
 * Proporciona métodos para crear, leer, actualizar y eliminar registros en la
 * base de datos.
 */
public class JugadorJpaController implements Serializable {

    //Creamos:
    /**
     * Constructor por defecto que inicializa la conexión con la base de datos.
     * Se utiliza la unidad de persistencia definida en `persistence.xml`.
     */
    public JugadorJpaController() {
        //a createEntityManagerFactory, el entra el nombre d ela UP que nos hemos creado en el persistence.xml
        emf = Persistence.createEntityManagerFactory("PU_proyecto");
    }

    /**
     * Constructor que permite especificar una fábrica de entidades para la
     * persistencia.
     *
     * @param emf Fábrica de administradores de entidades.
     */
    public JugadorJpaController(EntityManagerFactory emf) {
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
     * Crea un nuevo jugador en la base de datos.
     *
     * @param jugador Objeto {@link Jugador} a persistir.
     */
    public void create(Jugador jugador) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(jugador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Modifica un registro existente de un jugador en la base de datos.
     *
     * @param jugador Objeto {@link Jugador} con la información actualizada.
     * @throws NonexistentEntityException Si el registro no existe en la base de
     * datos.
     * @throws Exception Si ocurre un error en la transacción.
     */
    public void edit(Jugador jugador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            jugador = em.merge(jugador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = jugador.getId_j();
                if (findJugador(id) == null) {
                    throw new NonexistentEntityException("The Jugador with id " + id + " no longer exists.");
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
     * Elimina un registro de un jugador de la base de datos por su ID.
     *
     * @param id Identificador del jugador a eliminar.
     * @throws NonexistentEntityException Si el registro no existe en la base de
     * datos.
     */
    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jugador jugador;
            try {
                jugador = em.getReference(Jugador.class, id);
                jugador.getId_j();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The Jugador with id " + id + " no longer exists.", enfe);
            }
            em.remove(jugador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Obtiene una lista con todos los jugadores almacenados en la base de
     * datos.
     *
     * @return Lista de objetos {@link Jugador}.
     */
    public List<Jugador> findJugadorEntities() {
        return findJugadorEntities(true, -1, -1);
    }

    /**
     * Obtiene una lista de jugadores con paginación.
     *
     * @param maxResults Número máximo de resultados a obtener.
     * @param firstResult Índice del primer resultado a recuperar.
     * @return Lista de objetos {@link Jugador}.
     */
    public List<Jugador> findJugadorEntities(int maxResults, int firstResult) {
        return findJugadorEntities(false, maxResults, firstResult);
    }

    /**
     * Método privado que realiza la consulta de jugadores con o sin paginación.
     *
     * @param all Si es `true`, devuelve todos los registros. Si es `false`, usa
     * paginación.
     * @param maxResults Máximo número de resultados.
     * @param firstResult Primer resultado a recuperar.
     * @return Lista de objetos {@link Jugador} obtenidos de la base de datos.
     */
    private List<Jugador> findJugadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jugador.class));
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
     * Busca un jugador en la base de datos por su ID.
     *
     * @param id Identificador del jugador.
     * @return Objeto {@link Jugador} correspondiente al ID proporcionado, o
     * `null` si no existe.
     */
    public Jugador findJugador(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jugador.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene la cantidad total de jugadores almacenados en la base de datos.
     *
     * @return Número total de jugadores registrados.
     */
    public int getJugadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jugador> rt = cq.from(Jugador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
