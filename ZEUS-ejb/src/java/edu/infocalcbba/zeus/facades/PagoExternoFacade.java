/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.facades;

import edu.infocalcbba.argos.facades.AbstractFacade;
import edu.infocalcbba.zeus.entities.PagoExterno;
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
public class PagoExternoFacade extends AbstractFacade<PagoExterno> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public PagoExternoFacade() {
        super(PagoExterno.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public PagoExterno buscarPagoExterno(String codigo) {
        PagoExterno pagoexterno = null;

        try {
            Query q = em.createQuery("SELECT pe FROM PagoExterno pe WHERE pe.codigo=:codigo");
            q.setParameter("codigo", codigo);

            pagoexterno = (PagoExterno) q.getSingleResult();
        } catch (Exception e) {

        }

        return pagoexterno;
    }

    public PagoExterno buscarPagoExterno(String codigo, int id_pagoexterno) {
        PagoExterno pagoexterno = null;

        try {
            Query q = em.createQuery("SELECT pe FROM PagoExterno pe WHERE pe.codigo=:codigo AND pe.id_pagoexterno!=:id_pagoexterno");
            q.setParameter("codigo", codigo);
            q.setParameter("id_pagoexterno", id_pagoexterno);

            pagoexterno = (PagoExterno) q.getSingleResult();
        } catch (Exception e) {

        }

        return pagoexterno;
    }

    public List<PagoExterno> listaPagosExternos() {
        List<PagoExterno> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT pe FROM PagoExterno pe ORDER BY pe.concepto");

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

}
