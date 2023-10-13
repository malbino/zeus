/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.facades;

import edu.infocalcbba.argos.entities.Carrera;
import edu.infocalcbba.argos.entities.DefensaGrado;
import edu.infocalcbba.util.Reloj;
import java.util.ArrayList;
import java.util.Date;
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
public class DefensaGradoFacade extends AbstractFacade<DefensaGrado> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public DefensaGradoFacade() {
        super(DefensaGrado.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public DefensaGrado buscarDefensaGrado(int id_egresado, String condicion) {
        DefensaGrado egresado = null;

        try {
            Query q = em.createQuery("SELECT d FROM DefensaGrado d JOIN d.egresado e WHERE e.id_egresado=:id_egresado AND d.condicion=:condicion");
            q.setParameter("id_egresado", id_egresado);
            q.setParameter("condicion", condicion);

            egresado = (DefensaGrado) q.getSingleResult();
        } catch (Exception e) {

        }

        return egresado;
    }

    public List<DefensaGrado> listaDefensasGrado() {
        List<DefensaGrado> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM DefensaGrado d ORDER BY d.fecha");

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
    
    public List<DefensaGrado> listaDefensasGrado(int id_convocatoria, Carrera carrera, String modalidad) {
        List<DefensaGrado> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM DefensaGrado d WHERE (d.id_defensagrado IN (SELECT d.id_defensagrado FROM DefensaGrado d JOIN d.convocatoria c JOIN d.egresado e JOIN e.carrera a WHERE c.id_convocatoria=:id_convocatoria AND a.id_carrera=:id_carrera) OR d.id_defensagrado IN (SELECT d.id_defensagrado FROM DefensaGrado d JOIN d.convocatoria c JOIN d.egresado e WHERE c.id_convocatoria=:id_convocatoria AND e.planestudio LIKE :planestudio)) AND d.modalidad=:modalidad");
            q.setParameter("id_convocatoria", id_convocatoria);
            q.setParameter("id_carrera", carrera.getId_carrera());
            q.setParameter("planestudio", "%" + carrera.getNombre() + "%");
            q.setParameter("modalidad", modalidad);
            
            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
    
    public List<DefensaGrado> listaDefensasGrado(int id_egresado) {
        List<DefensaGrado> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM DefensaGrado d JOIN d.egresado e JOIN d.convocatoria c WHERE e.id_egresado=:id_egresado ORDER BY c.gestion, c.numero");
            q.setParameter("id_egresado", id_egresado);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
}
