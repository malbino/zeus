/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.facades;

import edu.infocalcbba.argos.entities.Carrera;
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
public class CarreraFacade extends AbstractFacade<Carrera> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public CarreraFacade() {
        super(Carrera.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public Carrera buscarCarrera(String codigo) {
        Carrera carrera = null;

        try {
            Query q = em.createQuery("SELECT c FROM Carrera c WHERE c.codigo=:codigo");
            q.setParameter("codigo", codigo);

            carrera = (Carrera) q.getSingleResult();
        } catch (Exception e) {

        }

        return carrera;
    }

    public Carrera buscarCarrera(String codigo, int id_carrera) {
        Carrera carrera = null;

        try {
            Query q = em.createQuery("SELECT c FROM Carrera c WHERE c.codigo=:codigo AND c.id_carrera!=:id_carrera");
            q.setParameter("codigo", codigo);
            q.setParameter("id_carrera", id_carrera);

            carrera = (Carrera) q.getSingleResult();
        } catch (Exception e) {

        }

        return carrera;
    }

    public List<Carrera> listaCarreras() {
        List<Carrera> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT c FROM Carrera c ORDER BY c.nombre");
            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Carrera> listaCarreras(int id_regimen) {
        List<Carrera> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT c FROM Carrera c INNER JOIN c.regimen r WHERE r.id_regimen=:id_regimen ORDER BY c.nombre");
            q.setParameter("id_regimen", id_regimen);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Carrera> listaCarreras_NoEstudia(int id_regimen, int id_persona) {
        List<Carrera> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT c FROM Carrera c JOIN c.regimen r WHERE r.id_regimen=:id_regimen AND c.id_carrera NOT IN (SELECT c.id_carrera FROM Estudiante e JOIN e.carreras c WHERE e.id_persona=:id_persona) ORDER BY c.nombre");
            q.setParameter("id_regimen", id_regimen);
            q.setParameter("id_persona", id_persona);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Carrera> listaCarrerasCampus(int id_campus) {
        List<Carrera> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT c FROM Carrera c JOIN c.campus a WHERE a.id_campus=:id_campus ORDER BY c.nombre");
            q.setParameter("id_campus", id_campus);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
    
    public List<Carrera> listaCarreras_GAEmpleado(int id_gestionacademica, int id_persona) {
        List<Carrera> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT DISTINCT c FROM Grupo g JOIN g.paralelo p JOIN p.carrera c JOIN p.gestionacademica ga JOIN g.empleado e WHERE ga.id_gestionacademica=:id_gestionacademica AND e.id_persona=:id_persona ORDER BY c.nombre");
            q.setParameter("id_gestionacademica", id_gestionacademica);
            q.setParameter("id_persona", id_persona);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
}
