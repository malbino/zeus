/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.facades;

import edu.infocalcbba.argos.facades.AbstractFacade;
import edu.infocalcbba.zeus.entities.Detalle;
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
public class DetalleFacade extends AbstractFacade<Detalle> {

    private static final String CONDICION_VALIDA = "VALIDA";

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public DetalleFacade() {
        super(Detalle.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Detalle> listaDetalles_Formacion(int id_factura) {
        List<Detalle> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM Detalle d JOIN d.factura f LEFT JOIN d.cuota c WHERE f.id_factura=:id_factura ORDER BY c.numero, d.codigo");
            q.setParameter("id_factura", id_factura);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Detalle> listaDetalles_Capacitacion(int id_factura) {
        List<Detalle> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM Detalle d JOIN d.factura f LEFT JOIN d.pago p WHERE f.id_factura=:id_factura ORDER BY p.numero, d.codigo");
            q.setParameter("id_factura", id_factura);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
    
    public List<Detalle> listaDetalles_Formacion(int id_campus, int id_carrera, Date desde, Date hasta) {
        List<Detalle> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM Detalle d JOIN d.factura f JOIN f.dosificacion o JOIN o.campus c JOIN d.cuota u JOIN u.inscrito i JOIN i.carrera a WHERE c.id_campus=:id_campus AND a.id_carrera=:id_carrera AND f.condicion=:condicion AND f.fecha BETWEEN :desde AND :hasta AND EXTRACT(YEAR FROM f.fecha)=EXTRACT(YEAR FROM i.fecha) ORDER BY f.numero");
            q.setParameter("id_campus", id_campus);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("condicion", CONDICION_VALIDA);
            q.setParameter("desde", Reloj.getInicioDia(desde));
            q.setParameter("hasta", Reloj.getFinDia(hasta));

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
    
    public List<Detalle> listaDetalles_Formacion_GestionesAnteriores(int id_campus, int id_carrera, Date desde, Date hasta) {
        List<Detalle> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM Detalle d JOIN d.factura f JOIN f.dosificacion o JOIN o.campus c JOIN d.cuota u JOIN u.inscrito i JOIN i.carrera a WHERE c.id_campus=:id_campus AND a.id_carrera=:id_carrera AND f.condicion=:condicion AND f.fecha BETWEEN :desde AND :hasta AND EXTRACT(YEAR FROM f.fecha)!=EXTRACT(YEAR FROM i.fecha) ORDER BY f.numero");
            q.setParameter("id_campus", id_campus);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("condicion", CONDICION_VALIDA);
            q.setParameter("desde", Reloj.getInicioDia(desde));
            q.setParameter("hasta", Reloj.getFinDia(hasta));

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Detalle> listaDetalles_Capacitacion(int id_campus, int id_carrera, Date desde, Date hasta) {
        List<Detalle> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM Detalle d JOIN d.factura f JOIN f.dosificacion o JOIN o.campus c JOIN d.pago p JOIN p.planpago pp JOIN pp.inscrito i JOIN i.grupo g JOIN g.curso u JOIN u.carrera a WHERE c.id_campus=:id_campus AND a.id_carrera=:id_carrera AND f.condicion=:condicion AND f.fecha BETWEEN :desde AND :hasta AND EXTRACT(YEAR FROM f.fecha)=EXTRACT(YEAR FROM i.fecha) ORDER BY f.numero");
            q.setParameter("id_campus", id_campus);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("condicion", CONDICION_VALIDA);
            q.setParameter("desde", Reloj.getInicioDia(desde));
            q.setParameter("hasta", Reloj.getFinDia(hasta));

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
    
    public List<Detalle> listaDetalles_Capacitacion_GestionesAnteriores(int id_campus, int id_carrera, Date desde, Date hasta) {
        List<Detalle> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM Detalle d JOIN d.factura f JOIN f.dosificacion o JOIN o.campus c JOIN d.pago p JOIN p.planpago pp JOIN pp.inscrito i JOIN i.grupo g JOIN g.curso u JOIN u.carrera a WHERE c.id_campus=:id_campus AND a.id_carrera=:id_carrera AND f.condicion=:condicion AND f.fecha BETWEEN :desde AND :hasta AND EXTRACT(YEAR FROM f.fecha)!=EXTRACT(YEAR FROM i.fecha) ORDER BY f.numero");
            q.setParameter("id_campus", id_campus);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("condicion", CONDICION_VALIDA);
            q.setParameter("desde", Reloj.getInicioDia(desde));
            q.setParameter("hasta", Reloj.getFinDia(hasta));

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Detalle> listaDetalles_TramCert(int id_campus, int id_carrera, Date desde, Date hasta) {
        List<Detalle> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM Detalle d JOIN d.factura f JOIN f.dosificacion o JOIN o.campus c JOIN d.carrera a WHERE c.id_campus=:id_campus AND a.id_carrera=:id_carrera AND f.condicion=:condicion AND f.fecha BETWEEN :desde AND :hasta ORDER BY f.numero");
            q.setParameter("id_campus", id_campus);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("condicion", CONDICION_VALIDA);
            q.setParameter("desde", Reloj.getInicioDia(desde));
            q.setParameter("hasta", Reloj.getFinDia(hasta));

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Detalle> listaDetalles_DefensaGrado(int id_campus, int id_carrera, Date desde, Date hasta) {
        List<Detalle> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM Detalle d JOIN d.factura f JOIN f.dosificacion o JOIN o.campus c JOIN d.derecho e JOIN e.defensaGrado n JOIN n.carrera a WHERE c.id_campus=:id_campus AND a.id_carrera=:id_carrera AND f.condicion=:condicion AND f.fecha BETWEEN :desde AND :hasta ORDER BY f.numero");
            q.setParameter("id_campus", id_campus);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("condicion", CONDICION_VALIDA);
            q.setParameter("desde", Reloj.getInicioDia(desde));
            q.setParameter("hasta", Reloj.getFinDia(hasta));

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Detalle> listaDetalles_Externa(int id_campus, Date desde, Date hasta) {
        List<Detalle> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT d FROM Detalle d JOIN d.factura f JOIN f.dosificacion o JOIN o.campus c LEFT JOIN d.cuota u LEFT JOIN d.pago p LEFT JOIN d.carrera a LEFT JOIN d.estudiante e LEFT JOIN d.derecho r WHERE u.id_cuota IS NULL AND p.id_pago IS NULL AND a.id_carrera IS NULL AND e.id_persona IS NULL AND r.id_derecho IS NULL AND c.id_campus=:id_campus AND f.condicion=:condicion AND f.fecha BETWEEN :desde AND :hasta ORDER BY f.fecha");
            q.setParameter("id_campus", id_campus);
            q.setParameter("condicion", CONDICION_VALIDA);
            q.setParameter("desde", Reloj.getInicioDia(desde));
            q.setParameter("hasta", Reloj.getFinDia(hasta));

            l = q.getResultList();
        } catch (Exception e) {
            System.out.println(e);
        }

        return l;
    }

}
