/*
 * To change this template, choose Tools | Templates
 * and optcn the template in the editor.
 */
package edu.infocalcbba.zeus.facades;

import edu.infocalcbba.argos.facades.AbstractFacade;
import edu.infocalcbba.zeus.entities.PagoTramCert;
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
public class PagoTramCertFacade extends AbstractFacade<PagoTramCert> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public PagoTramCertFacade() {
        super(PagoTramCert.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public PagoTramCert buscarPagoTramCert(String codigo) {
        PagoTramCert pagoexterno = null;

        try {
            Query q = em.createQuery("SELECT ptc FROM  PagoTramCert ptc WHERE ptc.codigo=:codigo");
            q.setParameter("codigo", codigo);

            pagoexterno = (PagoTramCert) q.getSingleResult();
        } catch (Exception e) {

        }

        return pagoexterno;
    }

    public PagoTramCert buscarPagoTramCert(String codigo, int id_pagotramcert) {
        PagoTramCert pagotramcert = null;

        try {
            Query q = em.createQuery("SELECT ptc FROM PagoTramCert ptc WHERE ptc.codigo=:codigo AND ptc.id_pagotramcert!=:id_pagotramcert");
            q.setParameter("codigo", codigo);
            q.setParameter("id_pagotramcert", id_pagotramcert);

            pagotramcert = (PagoTramCert) q.getSingleResult();
        } catch (Exception e) {

        }

        return pagotramcert;
    }

    public List<PagoTramCert> listaPagosTramCert() {
        List<PagoTramCert> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT ptc FROM PagoTramCert ptc ORDER BY ptc.concepto");

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

}
