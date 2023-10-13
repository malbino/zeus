/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.facades;

import edu.infocalcbba.argos.entities.Estudiante;
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
public class EstudianteFacade extends AbstractFacade<Estudiante> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public EstudianteFacade() {
        super(Estudiante.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public Estudiante buscarPorDni(String dni) {
        Estudiante est = null;

        try {
            Query q = em.createQuery("SELECT e FROM Estudiante e WHERE e.dni=:dni");
            q.setParameter("dni", dni);

            est = (Estudiante) q.getSingleResult();
        } catch (Exception e) {
            
        }

        return est;
    }

    public Estudiante buscarPorDni(String dni, int id_persona) {
        Estudiante est = null;

        try {
            Query q = em.createQuery("SELECT e FROM Estudiante e WHERE e.dni=:dni AND e.id_persona!=:id_persona");
            q.setParameter("dni", dni);
            q.setParameter("id_persona", id_persona);

            est = (Estudiante) q.getSingleResult();
        } catch (Exception e) {
            
        }

        return est;
    }
}
