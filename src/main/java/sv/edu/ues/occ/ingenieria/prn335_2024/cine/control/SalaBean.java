package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

import java.io.Serializable;

public class SalaBean extends AbstractDataPersistence<Sala> implements Serializable {
    @PersistenceContext(unitName = "CinePU")
    EntityManager em;

    public SalaBean() {
        super(Sala.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return null;
    }

//    public List<Sala> findByIdTipoSala(Integer idTipoSala, int first, int max) {
//        if (idTipoSala != null && first >= 0 && max >0) {
//            try {
//                if (em != null) {
//                    Query q = em.createNamedQuery("Sala.findByIdTipoSala");
//                    q.setParameter("idTipoSala", idTipoSala);
//                    q.setMaxResults(max);
//                    q.setFirstResult(first);
////                   Query q=em.createQuery("select s from SalaCaracteristica sc where sc.idTipoSala.idTipoSala="+idTipoSala+" " +
////                            "JOIN sc.idSala s group by s.idSala order by s.nombre ASC",Sala.class);
//
//                    return q.getResultList();
//                }
//            } catch (Exception e) {
//                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
//            }
//        }
//        return Collections.emptyList();
//    }

}
