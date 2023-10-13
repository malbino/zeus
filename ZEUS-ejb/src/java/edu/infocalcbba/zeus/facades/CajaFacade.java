/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.facades;

import edu.infocalcbba.argos.facades.AbstractFacade;
import edu.infocalcbba.zeus.entities.Caja;
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
public class CajaFacade extends AbstractFacade<Caja> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public CajaFacade() {
        super(Caja.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public Caja buscarCaja(String codigo) {
        Caja caja = null;

        try {
            Query q = em.createQuery("SELECT c FROM Caja c WHERE c.codigo=:codigo");
            q.setParameter("codigo", codigo);

            caja = (Caja) q.getSingleResult();
        } catch (Exception e) {
            
        }
        
        return caja;
    }

    public Caja buscarCaja(String codigo, int id_caja) {
        Caja caja = null;

        try {
            Query q = em.createQuery("SELECT c FROM Caja c WHERE c.codigo=:codigo AND c.id_caja!=:id_caja");
            q.setParameter("codigo", codigo);
            q.setParameter("id_caja", id_caja);

            caja = (Caja) q.getSingleResult();
        } catch (Exception e) {
            
        }

        return caja;
    }

    public List<Caja> listaCajas() {
        List<Caja> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT c FROM Caja c ORDER BY c.nombre");
            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }

    public List<Caja> listaCajas(int id_campus) {
        List<Caja> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT c FROM Caja c INNER JOIN c.campus a WHERE a.id_campus=:id_campus ORDER BY c.nombre");
            q.setParameter("id_campus", id_campus);

            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }

}
