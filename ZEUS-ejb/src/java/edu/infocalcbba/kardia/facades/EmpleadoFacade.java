/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.kardia.facades;

import edu.infocalcbba.argos.facades.AbstractFacade;
import edu.infocalcbba.kardia.entities.Empleado;
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
public class EmpleadoFacade extends AbstractFacade<Empleado> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public EmpleadoFacade() {
        super(Empleado.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Empleado> listaEmpleados() {
        List<Empleado> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT e FROM Empleado e WHERE e.estado=TRUE ORDER BY e.papellido, e.sapellido, e.nombre");

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
}
