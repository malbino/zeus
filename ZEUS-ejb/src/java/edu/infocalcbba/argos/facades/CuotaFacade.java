/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.facades;

import edu.infocalcbba.argos.entities.Cuota;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tincho
 */
@Stateless
@LocalBean
public class CuotaFacade extends AbstractFacade<Cuota> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public CuotaFacade() {
        super(Cuota.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Cuota> listaCuota(int id_inscrito) {
        List<Cuota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT c FROM Cuota c JOIN c.inscrito i WHERE i.id_inscrito=:id_inscrito ORDER BY c.numero");
            q.setParameter("id_inscrito", id_inscrito);

            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }

    public List<Cuota> listaCuota(int id_inscrito, String condicion) {
        List<Cuota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT c FROM Cuota c JOIN c.inscrito i WHERE i.id_inscrito=:id_inscrito AND c.condicion=:condicion ORDER BY c.numero");
            q.setParameter("id_inscrito", id_inscrito);
            q.setParameter("condicion", condicion);

            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }
    
    public long pagadoColegiatura(int id_inscrito) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT SUM(c.pagado) FROM Cuota c JOIN c.inscrito i WHERE i.id_inscrito=:id_inscrito");
            q.setParameter("id_inscrito", id_inscrito);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

}
