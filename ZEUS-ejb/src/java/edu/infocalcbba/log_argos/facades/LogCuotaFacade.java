/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.log_argos.facades;

import edu.infocalcbba.argos.facades.*;
import edu.infocalcbba.log_argos.entities.LogCuota;
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
public class LogCuotaFacade extends AbstractFacade<LogCuota> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public LogCuotaFacade() {
        super(LogCuota.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<LogCuota> listaLogsCuota(int id_cuota) {
        List<LogCuota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT lc FROM LogCuota lc WHERE lc.id_cuota=:id_cuota ORDER BY lc.fecha");
            q.setParameter("id_cuota", id_cuota);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
}
