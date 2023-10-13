/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.facades;

import edu.infocalcbba.argos.facades.AbstractFacade;
import edu.infocalcbba.zeus.entities.TurnoCaja;
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
public class TurnoCajaFacade extends AbstractFacade<TurnoCaja> {

    private static final String CONDICION_ABIERTO = "ABIERTO";

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public TurnoCajaFacade() {
        super(TurnoCaja.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public TurnoCaja turnoCajaAbierto(int id_persona) {
        TurnoCaja turnocaja = null;

        try {
            Query q = em.createQuery("SELECT t FROM TurnoCaja t JOIN t.empleado e WHERE e.id_persona=:id_persona AND t.condicion=:condicion");
            q.setParameter("id_persona", id_persona);
            q.setParameter("condicion", CONDICION_ABIERTO);

            turnocaja = (TurnoCaja) q.getSingleResult();
        } catch (Exception e) {
            
        }

        return turnocaja;
    }

    public List<TurnoCaja> listaTurnosCaja(int id_persona) {
        List<TurnoCaja> l =  new ArrayList();

        try {
            Query q = em.createQuery("SELECT t FROM TurnoCaja t JOIN t.empleado e WHERE e.id_persona=:id_persona AND t.condicion=:condicion");
            q.setParameter("id_persona", id_persona);
            q.setParameter("condicion", CONDICION_ABIERTO);

            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }

}
