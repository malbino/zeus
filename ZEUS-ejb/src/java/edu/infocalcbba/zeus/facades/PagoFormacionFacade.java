/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.facades;

import edu.infocalcbba.argos.facades.AbstractFacade;
import edu.infocalcbba.zeus.entities.PagoFormacion;
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
public class PagoFormacionFacade extends AbstractFacade<PagoFormacion> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public PagoFormacionFacade() {
        super(PagoFormacion.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public PagoFormacion buscarPagoFormacion(String codigo) {
        PagoFormacion pagoformacion = null;

        try {
            Query q = em.createQuery("SELECT pf FROM PagoFormacion pf WHERE pf.codigo=:codigo");
            q.setParameter("codigo", codigo);

            pagoformacion = (PagoFormacion) q.getSingleResult();
        } catch (Exception e) {

        }

        return pagoformacion;
    }

    public PagoFormacion buscarPagoFormacion(String codigo, int id_pagoformacion) {
        PagoFormacion pagoformacion = null;

        try {
            Query q = em.createQuery("SELECT pf FROM PagoFormacion pf WHERE pf.codigo=:codigo AND pf.id_pagoformacion!=:id_pagoformacion");
            q.setParameter("codigo", codigo);
            q.setParameter("id_pagoformacion", id_pagoformacion);

            pagoformacion = (PagoFormacion) q.getSingleResult();
        } catch (Exception e) {

        }

        return pagoformacion;
    }

    public List<PagoFormacion> listaPagosFormacion() {
        List<PagoFormacion> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT pf FROM PagoFormacion pf ORDER BY pf.concepto");

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

}
