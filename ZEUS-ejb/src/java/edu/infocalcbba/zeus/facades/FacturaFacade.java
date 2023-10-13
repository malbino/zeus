/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.facades;

import edu.infocalcbba.argos.facades.AbstractFacade;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Factura;
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
public class FacturaFacade extends AbstractFacade<Factura> {

    private static final String CONDICION_VALIDA = "VALIDA";

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public FacturaFacade() {
        super(Factura.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public long cantidadPorDosificacion(int id_dosificacion) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT COUNT(f) FROM Factura f JOIN f.dosificacion d WHERE d.id_dosificacion=:id_dosificacion");
            q.setParameter("id_dosificacion", id_dosificacion);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public List<Factura> buscarFacturas(int numero) {
        List<Factura> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT f FROM Factura f WHERE f.numero=:numero ORDER BY f.fecha");
            q.setParameter("numero", numero);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Factura> listaFacturas(int id_turnocaja) {
        List<Factura> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT f FROM Factura f JOIN f.turnocaja t WHERE t.id_turnocaja=:id_turnocaja AND f.condicion=:condicion ORDER BY f.fecha");
            q.setParameter("id_turnocaja", id_turnocaja);
            q.setParameter("condicion", CONDICION_VALIDA);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Factura> listaFacturas(int id_campus, Date desde, Date hasta) {
        List<Factura> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT f FROM Factura f JOIN f.dosificacion d JOIN d.campus c WHERE c.id_campus=:id_campus AND f.fecha BETWEEN :desde AND :hasta ORDER BY f.fecha");
            q.setParameter("id_campus", id_campus);
            q.setParameter("desde", Reloj.getInicioDia(desde));
            q.setParameter("hasta", Reloj.getFinDia(hasta));

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Factura> listaFacturas(int id_campus, int id_persona, Date desde, Date hasta) {
        List<Factura> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT f FROM Factura f JOIN f.dosificacion d JOIN d.campus c JOIN f.turnocaja tc JOIN tc.empleado e WHERE c.id_campus=:id_campus AND e.id_persona=:id_persona AND f.condicion=:condicion AND f.fecha BETWEEN :desde AND :hasta ORDER BY f.fecha");
            q.setParameter("id_campus", id_campus);
            q.setParameter("id_persona", id_persona);
            q.setParameter("condicion", CONDICION_VALIDA);
            q.setParameter("desde", Reloj.getInicioDia(desde));
            q.setParameter("hasta", Reloj.getFinDia(hasta));

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public double montoFacturas(int id_turnocaja) {
        double res = 0;

        try {
            Query q = em.createQuery("SELECT SUM(f.monto) FROM Factura f JOIN f.turnocaja t WHERE t.id_turnocaja=:id_turnocaja AND f.condicion=:condicion");
            q.setParameter("id_turnocaja", id_turnocaja);
            q.setParameter("condicion", CONDICION_VALIDA);

            res = (double) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

}
