/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.phoenix.facades;

import edu.infocalcbba.phoenix.entities.InscritoPh;
import edu.infocalcbba.publica.facades.AbstractFacade;
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
public class InscritoPhFacade extends AbstractFacade<InscritoPh> {
    
    private static final String CONDICION_ABIERTA = "ABIERTA";

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public InscritoPhFacade() {
        super(InscritoPh.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public List<InscritoPh> inscripcionesPorEstudiante(int id_persona) {
        List<InscritoPh> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT i FROM InscritoPh i JOIN i.gestion g JOIN i.estudiante e WHERE g.condicion=:condicion AND e.id_persona=:id_persona");
            q.setParameter("condicion", CONDICION_ABIERTA);
            q.setParameter("id_persona", id_persona);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
}
