/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.facades;

import edu.infocalcbba.publica.entities.Funcionalidad;
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
public class FuncionalidadFacade extends AbstractFacade<Funcionalidad> {

    private static final String SISTEMA = "ZEUS";

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public FuncionalidadFacade() {
        super(Funcionalidad.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Funcionalidad> listaFuncionalidades() {
        List<Funcionalidad> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT f FROM Funcionalidad f JOIN f.sistema s ORDER BY s.nombre, f.nombre");

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public Funcionalidad buscarPorRolNombre(int id_rol, String nombre) {
        Funcionalidad f = null;

        try {
            Query q = em.createQuery("SELECT f FROM Funcionalidad f JOIN f.sistema s JOIN f.roles r WHERE s.nombre=:sistema AND r.id_rol=:id_rol AND f.nombre=:nombre");
            q.setParameter("sistema", SISTEMA);
            q.setParameter("id_rol", id_rol);
            q.setParameter("nombre", nombre);

            f = (Funcionalidad) q.getSingleResult();
        } catch (Exception e) {

        }

        return f;
    }

    public List<Funcionalidad> buscarPorRolURI(int id_rol, String uri) {
        List<Funcionalidad> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT f FROM Funcionalidad f JOIN f.sistema s JOIN f.roles r JOIN f.paginas p WHERE s.nombre=:sistema AND r.id_rol=:id_rol AND p.uri=:uri");
            q.setParameter("sistema", SISTEMA);
            q.setParameter("id_rol", id_rol);
            q.setParameter("uri", uri);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
}
