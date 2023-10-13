/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.phoenix.facades;

import edu.infocalcbba.argos.facades.*;
import edu.infocalcbba.phoenix.entities.Pago;
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
public class PagoFacade extends AbstractFacade<Pago> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public PagoFacade() {
        super(Pago.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Pago> listaPagos(int id_planpago) {
        List<Pago> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT p FROM Pago p JOIN p.planpago pp WHERE pp.id_planpago=:id_planpago ORDER BY p.numero");
            q.setParameter("id_planpago", id_planpago);

            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }

    public List<Pago> listaPagos(int id_planpago, String condicion) {
        List<Pago> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT p FROM Pago p JOIN p.planpago pp WHERE pp.id_planpago=:id_planpago AND p.condicion=:condicion ORDER BY p.numero");
            q.setParameter("id_planpago", id_planpago);
            q.setParameter("condicion", condicion);

            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }
    
    public long adeudadoFormacionValores(int id_persona) {
        long l = 0;

        try {
            Query q = em.createQuery("SELECT SUM(p.adeudado) FROM Pago p JOIN p.planpago pp JOIN pp.inscrito i JOIN i.estudiante e JOIN i.grupo g JOIN g.curso c WHERE e.id_persona=:id_persona AND (c.id_curso>=703 AND c.id_curso<=734)");
            q.setParameter("id_persona", id_persona);

            l = (long) q.getSingleResult();
        } catch (Exception e) {
            
        }

        return l;
    }

}
