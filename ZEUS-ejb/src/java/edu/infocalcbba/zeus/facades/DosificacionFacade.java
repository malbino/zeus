/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.facades;

import edu.infocalcbba.argos.facades.AbstractFacade;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Dosificacion;
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
public class DosificacionFacade extends AbstractFacade<Dosificacion> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public DosificacionFacade() {
        super(Dosificacion.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public Dosificacion buscarDosificacion(long numero_autorizacion) {
        Dosificacion dosificacion = null;

        try {
            Query q = em.createQuery("SELECT d FROM Dosificacion d WHERE d.numero_autorizacion=:numero_autorizacion");
            q.setParameter("numero_autorizacion", numero_autorizacion);

            dosificacion = (Dosificacion) q.getSingleResult();
        } catch (Exception e) {
            
        }
        
        return dosificacion;
    }

    public Dosificacion buscarDosificacion(long numero_autorizacion, int id_dosificacion) {
        Dosificacion dosificacion = null;

        try {
            Query q = em.createQuery("SELECT d FROM Dosificacion d WHERE d.numero_autorizacion=:numero_autorizacion AND d.id_dosificacion!=:id_dosificacion");
            q.setParameter("numero_autorizacion", numero_autorizacion);
            q.setParameter("id_dosificacion", id_dosificacion);

            dosificacion = (Dosificacion) q.getSingleResult();
        } catch (Exception e) {
            
        }

        return dosificacion;
    }

    public List<Dosificacion> listaDosificaciones(int id_campus) {
        List<Dosificacion> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM Dosificacion d INNER JOIN d.campus c WHERE c.id_campus=:id_campus ORDER BY d.factivacion");
            q.setParameter("id_campus", id_campus);

            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }

    public Dosificacion dosificacionActiva(int id_campus) {
        Dosificacion dosificacion = null;

        try {
            Query q = em.createQuery("SELECT d FROM Dosificacion d JOIN d.campus c WHERE c.id_campus=:id_campus AND d.factivacion=(SELECT MAX(d.factivacion) FROM Dosificacion d JOIN d.campus c WHERE c.id_campus=:id_campus) AND d.flimite_emision>=:fecha");
            q.setParameter("id_campus", id_campus);
            q.setParameter("fecha", Reloj.getDate());

            dosificacion = (Dosificacion) q.getSingleResult();
        } catch (Exception e) {
            
        }

        return dosificacion;
    }

}
