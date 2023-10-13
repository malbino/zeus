/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.facades;

import edu.infocalcbba.publica.entities.Pais;
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
public class PaisFacade extends AbstractFacade<Pais> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public PaisFacade() {
        super(Pais.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Pais> listaPaises() {
        List<Pais> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT p FROM Pais p ORDER BY p.nombre");
            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

}
