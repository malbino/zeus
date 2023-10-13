/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.facades;

import edu.infocalcbba.publica.entities.Usuario;
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
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Usuario> buscarUsuarios(String consulta) {
        List<Usuario> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT u FROM Usuario u WHERE CONCAT(u.papellido, u.sapellido, u.nombre) LIKE :consulta ORDER BY u.papellido, u.sapellido, u.nombre");
            q.setParameter("consulta", consulta + "%");

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public Usuario buscarPorUsuario(String usuario) {
        Usuario usr = null;

        try {
            Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario=:usuario");
            q.setParameter("usuario", usuario);

            usr = (Usuario) q.getSingleResult();
        } catch (Exception e) {
            
        }

        return usr;
    }
    
    public Usuario buscarPorUsuario(String usuario, int id_persona) {
        Usuario usr = null;

        try {
            Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario=:usuario AND u.id_persona!=:id_persona");
            q.setParameter("usuario", usuario);
            q.setParameter("id_persona", id_persona);

            usr = (Usuario) q.getSingleResult();
        } catch (Exception e) {

        }

        return usr;
    }
}
