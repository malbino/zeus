/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.facades;

import edu.infocalcbba.argos.facades.AbstractFacade;
import edu.infocalcbba.zeus.entities.Cliente;
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
public class ClienteFacade extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public ClienteFacade() {
        super(Cliente.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Cliente> listaClientes() {
        List<Cliente> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT c FROM Cliente c ORDER BY c.nit_ci");
            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }
    
    public List<Cliente> listaClientes(int id_persona) {
        List<Cliente> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT DISTINCT c FROM Detalle d JOIN d.factura f JOIN f.cliente c WHERE d.id_detalle IN (SELECT d.id_detalle FROM Detalle d JOIN d.cuota c JOIN c.inscrito i JOIN i.estudiante e WHERE e.id_persona=:id_persona) OR d.id_detalle IN (SELECT d.id_detalle FROM Detalle d JOIN d.pago p JOIN p.planpago pp JOIN pp.inscrito i JOIN i.estudiante e WHERE e.id_persona=:id_persona) OR d.id_detalle IN (SELECT d.id_detalle FROM Detalle d JOIN d.estudiante e WHERE e.id_persona=:id_persona) OR d.id_detalle IN (SELECT d.id_detalle FROM Detalle d JOIN d.derecho e JOIN e.defensaGrado f JOIN f.egresado g JOIN g.estudiante s WHERE s.id_persona=:id_persona) ORDER BY c.nit_ci");
            q.setParameter("id_persona", id_persona);
            
            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }

}
