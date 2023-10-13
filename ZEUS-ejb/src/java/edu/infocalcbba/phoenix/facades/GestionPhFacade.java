/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.phoenix.facades;

import edu.infocalcbba.phoenix.entities.GestionPh;
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
public class GestionPhFacade extends AbstractFacade<GestionPh> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public GestionPhFacade() {
        super(GestionPh.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public GestionPh buscarGestion(String codigo) {
        GestionPh curso = null;

        try {
            Query q = em.createQuery("SELECT g FROM Gestion g WHERE g.codigo=:codigo");
            q.setParameter("codigo", codigo);

            curso = (GestionPh) q.getSingleResult();
        } catch (Exception e) {

        }

        return curso;
    }

    public GestionPh buscarGestion(String codigo, int id_gestion) {
        GestionPh curso = null;

        try {
            Query q = em.createQuery("SELECT g FROM Gestion g WHERE g.codigo=:codigo AND g.id_gestion!=:id_gestion");
            q.setParameter("codigo", codigo);
            q.setParameter("id_curso", id_gestion);

            curso = (GestionPh) q.getSingleResult();
        } catch (Exception e) {

        }

        return curso;
    }

    public List<GestionPh> listaGestiones() {
        List<GestionPh> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT g FROM GestionPh g ORDER BY g.inicio");

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

}
