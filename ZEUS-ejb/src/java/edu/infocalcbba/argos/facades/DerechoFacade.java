/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.facades;

import edu.infocalcbba.argos.entities.Derecho;
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
public class DerechoFacade extends AbstractFacade<Derecho> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public DerechoFacade() {
        super(Derecho.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Derecho> listaDerecho(int id_defensagrado) {
        List<Derecho> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM Derecho d JOIN d.defensaGrado e WHERE e.id_defensagrado=:id_defensagrado ORDER BY d.numero");
            q.setParameter("id_defensagrado", id_defensagrado);

            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }

    public List<Derecho> listaDerecho(int id_defensagrado, String condicion) {
        List<Derecho> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM Derecho d JOIN d.defensaGrado e WHERE e.id_defensagrado=:id_defensagrado AND d.condicion=:condicion ORDER BY d.numero");
            q.setParameter("id_defensagrado", id_defensagrado);
            q.setParameter("condicion", condicion);

            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }

}
