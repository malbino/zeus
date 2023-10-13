/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.facades;

import edu.infocalcbba.argos.entities.Egresado;
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
public class EgresadoFacade extends AbstractFacade<Egresado> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public EgresadoFacade() {
        super(Egresado.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public Egresado buscarEgresado(int id_carrera, int id_persona) {
        Egresado egresado = null;

        try {
            Query q = em.createQuery("SELECT e FROM Egresado e JOIN e.carrera c JOIN e.estudiante s WHERE c.id_carrera=:id_carrera AND s.id_persona=:id_persona");
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("id_persona", id_persona);

            egresado = (Egresado) q.getSingleResult();
        } catch (Exception e) {

        }

        return egresado;
    }

    public Egresado buscarEgresado_CRONOS(String planestudio, String personal) {
        Egresado egresado = null;

        try {
            Query q = em.createQuery("SELECT e FROM Egresado e WHERE e.planestudio=:planestudio AND e.personal=:personal");
            q.setParameter("planestudio", planestudio);
            q.setParameter("personal", personal);

            egresado = (Egresado) q.getSingleResult();
        } catch (Exception e) {

        }

        return egresado;
    }
}
