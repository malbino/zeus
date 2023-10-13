/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.facades;

import edu.infocalcbba.argos.entities.Inscrito;
import edu.infocalcbba.util.Reloj;
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
public class InscritoFacade extends AbstractFacade<Inscrito> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public InscritoFacade() {
        super(Inscrito.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Inscrito> listaInscritosActivos() {
        List<Inscrito> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT i FROM Inscrito i JOIN i.gestionacademica g WHERE :fecha BETWEEN g.inicio AND g.fin");
            q.setParameter("fecha", Reloj.getDate());

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Inscrito> inscripcionesPorEstudiante(int id_rol, int id_persona) {
        List<Inscrito> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT i FROM Inscrito i JOIN i.gestionacademica g JOIN i.estudiante e JOIN g.roles r WHERE r.id_rol=:id_rol AND e.id_persona=:id_persona");
            q.setParameter("id_rol", id_rol);
            q.setParameter("id_persona", id_persona);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public Inscrito buscarInscrito(int id_gestionacademica, int id_carrera, int id_persona) {
        Inscrito inscrito = null;

        try {
            Query q = em.createQuery("SELECT i FROM Inscrito i JOIN i.gestionacademica g JOIN i.carrera c JOIN i.estudiante e WHERE g.id_gestionacademica=:id_gestionacademica AND c.id_carrera=:id_carrera AND e.id_persona=:id_persona");
            q.setParameter("id_gestionacademica", id_gestionacademica);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("id_persona", id_persona);

            inscrito = (Inscrito) q.getSingleResult();
        } catch (Exception e) {

        }

        return inscrito;
    }

    public Inscrito buscarInscrito(int id_gestionacademica, int id_carrera, String dni) {
        Inscrito inscrito = null;

        try {
            Query q = em.createQuery("SELECT i FROM Inscrito i JOIN i.gestionacademica g JOIN i.carrera c JOIN i.estudiante e WHERE g.id_gestionacademica=:id_gestionacademica AND c.id_carrera=:id_carrera AND e.dni=:dni");
            q.setParameter("id_gestionacademica", id_gestionacademica);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("dni", dni);

            inscrito = (Inscrito) q.getSingleResult();
        } catch (Exception e) {

        }

        return inscrito;
    }

    public Inscrito buscarInscrito(String dni) {
        Inscrito inscrito = null;

        try {
            Query q = em.createQuery("SELECT i FROM Inscrito i JOIN i.gestionacademica g JOIN i.estudiante e WHERE g.estado=TRUE AND e.dni=:dni");
            q.setParameter("dni", dni);

            inscrito = (Inscrito) q.getSingleResult();
        } catch (Exception e) {

        }

        return inscrito;
    }

    public long cantidadPorGestionAcademicaCarrera(int id_gestionacademica, int id_carrera) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT COUNT(i) FROM Inscrito i JOIN i.gestionacademica ga JOIN i.carrera c WHERE ga.id_gestionacademica=:id_gestionacademica AND c.id_carrera=:id_carrera");
            q.setParameter("id_gestionacademica", id_gestionacademica);
            q.setParameter("id_carrera", id_carrera);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public Inscrito buscarInscrito(int id_factura) {
        Inscrito inscrito = null;

        try {
            Query q = em.createQuery("SELECT DISTINCT i FROM Factura f JOIn f.detalles d JOIN d.cuota c JOIN c.inscrito i WHERE f.id_factura=:id_factura");
            q.setParameter("id_factura", id_factura);

            inscrito = (Inscrito) q.getSingleResult();
        } catch (Exception e) {

        }

        return inscrito;
    }
}
