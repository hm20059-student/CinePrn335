package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Collections;
import java.util.List;

public abstract class AbstractDataPersistence<T> {
    final Class tipoDatos;

    public AbstractDataPersistence(Class tipoDatos) {
        this.tipoDatos = tipoDatos;
    }

    public abstract EntityManager getEntityManager();

    public T findById(Object id) throws IllegalArgumentException, IllegalStateException {
        EntityManager em = null;
        if (id == null) {
            throw new IllegalArgumentException("parametro no valido: idTipoSala");
        }
        try {
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("error al acceder al reporsitorio");
            }
        } catch (IllegalStateException ex) {
            throw new IllegalStateException("error al acceder al reporsitorio", ex);
        }
        return (T) em.find(this.tipoDatos, id);
    }

    public void delete(T registro) {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("EntityManager no está configurado");
            }
            if (!em.contains(registro)) {
                // Si no está contenido, hacer un merge del objeto primero
                registro = em.merge(registro);
            }
            em.remove(registro);

        } catch (IllegalStateException e) {
            throw new IllegalStateException("error : ", e);
        }

    }

    public T update(T registro) {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        T modificado = null;
        EntityManager em = null;
        try {
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("fallo el entityManager");
            }
            modificado = em.merge(registro);
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al acceder al repositorio", e);
        }
        return modificado;
    }

    public int count() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("fallo el entityManager");
            }
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<T> raiz = cq.from(this.tipoDatos);

            cq.select(cb.count(raiz));

            TypedQuery<Long> query = em.createQuery(cq);

            return query.getSingleResult().intValue();

        } catch (Exception e) {
            throw new IllegalStateException("Error al contar los registros", e);
        }
    }

    public void create(T registro) throws IllegalStateException, IllegalArgumentException {
        EntityManager em = null;
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        try {
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("Error al acceder al repositorio");
            }
            em.persist(registro);
        } catch (Exception e) {
            throw new IllegalStateException("Error al acceder al repositorio xd", e);
        }
    }

    public List<T> findRange(int first, int max) throws IllegalStateException, IllegalArgumentException {
        return findRange(first, max, "", ""); // Llama al método con orden como cadena vacía
    }

    public List<T> findRange(int first, int pageSize, String ordenar, String direccion) throws IllegalArgumentException, IllegalStateException {
//        List<T> resultado = null;
        if (first < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Parametros no validos");
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("Error al acceder al repositorio");
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Error al acceder al repositorio");
        }
        if (first >= 0 && pageSize > 0) {
            try {
                if (em != null) {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery cq = cb.createQuery(tipoDatos);
                    Root<T> raiz = cq.from(tipoDatos);
                    cq.select(raiz);
                    TypedQuery q = em.createQuery(cq);

                    if (!ordenar.isEmpty()) {
                        if (!direccion.equals("ASCENDING")) {

                            cq.orderBy(cb.desc(raiz.get(ordenar))); // Orden ascendente
                        } else {

                            cq.orderBy(cb.asc(raiz.get(ordenar))); // Orden ascendente
                        }
                    }
                    q.setFirstResult(first);
                    q.setMaxResults(pageSize);
                    return q.getResultList();
                }

            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }
            throw new IllegalStateException();
//            return resultado;
        }
        return Collections.emptyList();
    }

    public String imprimirCarnet() {
        return "HM20059";
    }
}
