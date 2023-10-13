/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.facades;

import edu.infocalcbba.publica.entities.Rol;
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
public class RolFacade extends AbstractFacade<Rol> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public RolFacade() {
        super(Rol.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public Rol buscarRol(String codigo) {
        Rol rol = null;

        try {
            Query q = em.createQuery("SELECT r FROM Rol r WHERE r.codigo=:codigo");
            q.setParameter("codigo", codigo);

            rol = (Rol) q.getSingleResult();
        } catch (Exception e) {
            
        }

        return rol;
    }
    
    public Rol buscarRol(String codigo, int id_rol) {
        Rol rol = null;

        try {
            Query q = em.createQuery("SELECT r FROM Rol r WHERE r.codigo=:codigo AND r.id_rol!=:id_rol");
            q.setParameter("codigo", codigo);
            q.setParameter("id_rol", id_rol);

            rol = (Rol) q.getSingleResult();
        } catch (Exception e) {

        }

        return rol;
    }

    public List<Rol> listaRoles() {
        List<Rol> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT r FROM Rol r ORDER BY r.nombre");

            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }

    public List<Rol> listaRoles(int id_persona) {
        List<Rol> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT r FROM Rol r JOIN r.usuarios u WHERE u.id_persona=:id_persona");
            q.setParameter("id_persona", id_persona);

            l = q.getResultList();
        } catch (Exception e) {
            
        }

        return l;
    }
}
