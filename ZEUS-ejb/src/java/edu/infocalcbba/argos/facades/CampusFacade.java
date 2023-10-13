/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.facades;

import edu.infocalcbba.argos.entities.Campus;
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
public class CampusFacade extends AbstractFacade<Campus> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public CampusFacade() {
        super(Campus.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public Campus buscarCampus(String codigo) {
        Campus campus = null;

        try {
            Query q = em.createQuery("SELECT c FROM Campus c WHERE c.codigo=:codigo");
            q.setParameter("codigo", codigo);

            campus = (Campus) q.getSingleResult();
        } catch (Exception e) {
            
        }

        return campus;
    }

    public Campus buscarCampus(String codigo, int id_campus) {
        Campus campus = null;

        try {
            Query q = em.createQuery("SELECT c FROM Campus c WHERE c.codigo=:codigo AND c.id_campus!=:id_campus");
            q.setParameter("codigo", codigo);
            q.setParameter("id_campus", id_campus);

            campus = (Campus) q.getSingleResult();
        } catch (Exception e) {
            
        }

        return campus;
    }

    public List<Campus> listaCampus() {
        List<Campus> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT c FROM Campus c ORDER BY c.codigo");
            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }

    public List<Campus> listaCampus(int id_institucion) {
        List<Campus> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT c FROM Campus c JOIN c.institucion i WHERE i.id_institucion=:id_institucion ORDER BY c.codigo");
            q.setParameter("id_institucion", id_institucion);

            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }

}
