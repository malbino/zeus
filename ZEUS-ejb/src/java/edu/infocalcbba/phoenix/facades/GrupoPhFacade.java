/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.phoenix.facades;

import edu.infocalcbba.phoenix.entities.GrupoPh;
import edu.infocalcbba.publica.facades.AbstractFacade;
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
public class GrupoPhFacade extends AbstractFacade<GrupoPh> {

    private static final String CONDICION_ABIERTA = "ABIERTA";

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public GrupoPhFacade() {
        super(GrupoPh.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<GrupoPh> listaGrupos_GestionCurso(int id_gestion, int id_curso) {
        List<GrupoPh> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT gu FROM GrupoPh gu JOIN gu.gestion ge JOIN gu.curso c WHERE ge.id_gestion=:id_gestion AND c.id_curso=:id_curso ORDER BY gu.codigo");
            q.setParameter("id_gestion", id_gestion);
            q.setParameter("id_curso", id_curso);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<GrupoPh> listaGrupos_GestionCarrera(int id_gestion, int id_carrera) {
        List<GrupoPh> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT gu FROM GrupoPh gu JOIN gu.gestion ge JOIN gu.curso cu JOIN cu.carrera ca WHERE ge.id_gestion=:id_gestion AND ca.id_carrera=:id_carrera ORDER BY cu.nombre, cu.version, gu.codigo");
            q.setParameter("id_gestion", id_gestion);
            q.setParameter("id_carrera", id_carrera);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public long contarGrupos(int id_gestion, int id_curso) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT COUNT(gu) FROM GrupoPh gu JOIN gu.gestion ge JOIN gu.curso c WHERE ge.id_gestion=:id_gestion AND c.id_curso=:id_curso");
            q.setParameter("id_gestion", id_gestion);
            q.setParameter("id_curso", id_curso);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public List<GrupoPh> listaGrupos_Empleado(int id_persona) {
        List<GrupoPh> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT g FROM GrupoPh g JOIN g.gestion e JOIN g.empleado m JOIN g.curso c WHERE e.condicion=:condicion AND m.id_persona=:id_persona ORDER BY c.nombre, c.version, g.codigo");
            q.setParameter("condicion", CONDICION_ABIERTA);
            q.setParameter("id_persona", id_persona);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

}
