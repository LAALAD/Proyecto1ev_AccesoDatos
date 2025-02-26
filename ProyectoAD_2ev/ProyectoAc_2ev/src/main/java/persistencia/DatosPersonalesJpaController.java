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

/**
 * Controlador JPA para gestionar la persistencia de objetos
 * {@link DatosPersonales}. Proporciona métodos para crear, leer, actualizar y
 * eliminar registros en la base de datos.
 */
public class DatosPersonalesJpaController implements Serializable {

    //Creamos:DatosPersonalesJpaController
    /**
     * Constructor por defecto que inicializa la conexión con la base de datos.
     * Se utiliza la unidad de persistencia definida en `persistence.xml`.
     */
    public DatosPersonalesJpaController() {
        //a createEntityManagerFactory, el entra el nombre d ela UP que nos hemos creado en el persistence.xml
        emf = Persistence.createEntityManagerFactory("PU_proyecto");
    }

    /**
     * Constructor que permite especificar una fábrica de entidades para la
     * persistencia.
     *
     * @param emf Fábrica de administradores de entidades.
     */
    public DatosPersonalesJpaController(EntityManagerFactory emf) {
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
     * Crea un nuevo registro de datos personales en la base de datos.
     *
     * @param datosP Objeto {@link DatosPersonales} a persistir.
     */
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

    /**
     * Modifica un registro existente de datos personales en la base de datos.
     *
     * @param datosP Objeto {@link DatosPersonales} con la información
     * actualizada.
     * @throws NonexistentEntityException Si el registro no existe en la base de
     * datos.
     * @throws Exception Si ocurre un error en la transacción.
     */
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

    /**
     * Elimina un registro de datos personales de la base de datos por su ID.
     *
     * @param id Identificador del registro a eliminar.
     * @throws NonexistentEntityException Si el registro no existe en la base de
     * datos.
     */
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

    /**
     * Obtiene una lista de todos los registros de datos personales almacenados
     * en la base de datos.
     *
     * @return Lista de objetos {@link DatosPersonales}.
     */
    public List<DatosPersonales> findDatosPersonalesEntities() {
        return findDatosPersonalesEntities(true, -1, -1);
    }

    /**
     * Obtiene una lista de registros de datos personales con paginación.
     *
     * @param maxResults Número máximo de resultados a obtener.
     * @param firstResult Índice del primer resultado a recuperar.
     * @return Lista de objetos {@link DatosPersonales}.
     */
    public List<DatosPersonales> findDatosPersonalesEntities(int maxResults, int firstResult) {
        return findDatosPersonalesEntities(false, maxResults, firstResult);
    }

    /**
     * Método privado que realiza la consulta de registros de datos personales
     * con o sin paginación.
     *
     * @param all Si es `true`, devuelve todos los registros. Si es `false`, usa
     * paginación.
     * @param maxResults Máximo número de resultados.
     * @param firstResult Primer resultado a recuperar.
     * @return Lista de objetos {@link DatosPersonales} obtenidos de la base de
     * datos.
     */
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

    /**
     * Busca un registro de datos personales en la base de datos por su ID.
     *
     * @param id Identificador del registro de datos personales.
     * @return Objeto {@link DatosPersonales} correspondiente al ID
     * proporcionado, o `null` si no existe.
     */
    public DatosPersonales findDatosPersonales(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DatosPersonales.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene la cantidad total de registros de datos personales almacenados en
     * la base de datos.
     *
     * @return Número total de registros de datos personales.
     */
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
