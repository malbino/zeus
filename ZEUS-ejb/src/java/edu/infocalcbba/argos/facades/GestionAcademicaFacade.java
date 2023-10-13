/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.facades;

import edu.infocalcbba.argos.entities.GestionAcademica;
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
public class GestionAcademicaFacade extends AbstractFacade<GestionAcademica> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public GestionAcademicaFacade() {
        super(GestionAcademica.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<GestionAcademica> listaGestionesAcademicas() {
        List<GestionAcademica> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT g FROM GestionAcademica g ORDER BY g.inicio");
            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<GestionAcademica> listaGestionesAcademicas(int id_rol) {
        List<GestionAcademica> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT g FROM Rol r JOIN r.gestionesAcademicas g WHERE r.id_rol=:id_rol ORDER BY g.inicio");
            q.setParameter("id_rol", id_rol);
            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<GestionAcademica> listaGestionesAcademicas(int id_rol, int id_regimen) {
        List<GestionAcademica> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT g FROM Rol r JOIN r.gestionesAcademicas g JOIN g.regimen e WHERE r.id_rol=:id_rol AND e.id_regimen=:id_regimen ORDER BY g.inicio");
            q.setParameter("id_rol", id_rol);
            q.setParameter("id_regimen", id_regimen);
            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<GestionAcademica> listaGestionesAcademicas_Sin(int id_rol, int id_regimen, int id_gestionacademica) {
        List<GestionAcademica> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT g FROM Rol r JOIN r.gestionesAcademicas g JOIN g.regimen e WHERE r.id_rol=:id_rol AND e.id_regimen=:id_regimen AND g.id_gestionacademica!=:id_gestionacademica ORDER BY g.inicio");
            q.setParameter("id_rol", id_rol);
            q.setParameter("id_regimen", id_regimen);
            q.setParameter("id_gestionacademica", id_gestionacademica);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
}
