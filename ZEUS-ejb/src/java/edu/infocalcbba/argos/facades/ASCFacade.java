/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.facades;

import edu.infocalcbba.argos.entities.ASC;
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
public class ASCFacade extends AbstractFacade<ASC> {

    private static final String CONDICION_APROBADO = "APROBADO";
    private static final String TIPO_CURRICULAR = "CURRICULAR";

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public ASCFacade() {
        super(ASC.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public ASC buscarASC(String codigo) {
        ASC asc = null;

        try {
            Query q = em.createQuery("SELECT a FROM ASC a WHERE a.codigo=:codigo");
            q.setParameter("codigo", codigo);

            asc = (ASC) q.getSingleResult();
        } catch (Exception e) {

        }

        return asc;
    }

    public ASC buscarASC(String codigo, int id_asc) {
        ASC asc = null;

        try {
            Query q = em.createQuery("SELECT a FROM ASC a WHERE a.codigo=:codigo AND a.id_asc!=:id_asc");
            q.setParameter("codigo", codigo);
            q.setParameter("id_asc", id_asc);

            asc = (ASC) q.getSingleResult();
        } catch (Exception e) {

        }

        return asc;
    }

    public List<ASC> listaASCs() {
        List<ASC> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT a FROM ASC a ORDER BY a.nombre");
            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<ASC> listaASCs(int id_carrera) {
        List<ASC> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT a FROM ASC a INNER JOIN a.carrera c INNER JOIN a.nivelestudio n WHERE c.id_carrera=:id_carrera ORDER BY n.nivel, a.nombre");
            q.setParameter("id_carrera", id_carrera);
            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<ASC> listaASCs_Sin(int id_carrera, int id_asc) {
        List<ASC> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT a FROM ASC a INNER JOIN a.carrera c INNER JOIN a.nivelestudio n WHERE c.id_carrera=:id_carrera AND a.id_asc!=:id_asc ORDER BY n.nivel, a.nombre");
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("id_asc", id_asc);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<ASC> listaASCs_CarreraNivelEstudio(int id_carrera, int id_nivelestudio) {
        List<ASC> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT a FROM ASC a INNER JOIN a.carrera c INNER JOIN a.nivelestudio ne WHERE c.id_carrera=:id_carrera AND ne.id_nivelestudio=:id_nivelestudio ORDER BY a.numero");
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("id_nivelestudio", id_nivelestudio);
            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<ASC> materiasDisponibles(int id_carrera, int id_nivelestudio, int id_paralelo) {
        List<ASC> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT a FROM ASC a JOIN a.carrera c JOIN a.nivelestudio ne WHERE c.id_carrera=:id_carrera AND ne.id_nivelestudio=:id_nivelestudio AND a.id_asc NOT IN (SELECT a.id_asc FROM Grupo g JOIN g.paralelo p JOIN g.asc a WHERE p.id_paralelo=:id_paralelo) ORDER BY a.numero");
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("id_nivelestudio", id_nivelestudio);
            q.setParameter("id_paralelo", id_paralelo);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<ASC> listaASCs_CarreraNivel(int id_carrera, int nivel) {
        List<ASC> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT a FROM ASC a JOIN a.carrera c JOIN a.nivelestudio ne WHERE c.id_carrera=:id_carrera AND ne.nivel=:nivel ORDER BY a.nombre");
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("nivel", nivel);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<ASC> listaASCs_Prerequisito(int id_asc) {
        List<ASC> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT a FROM ASC a JOIN a.prerequisitos p WHERE p.id_asc=:id_asc");
            q.setParameter("id_asc", id_asc);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public long cantidadASCsPorCarreraNivel(int id_carrera, int nivel) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT COUNT(a) FROM ASC a JOIN a.carrera c JOIN a.nivelestudio ne WHERE c.id_carrera=:id_carrera AND ne.nivel=:nivel AND a.tipo=:tipo");
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("nivel", nivel);
            q.setParameter("tipo", TIPO_CURRICULAR);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public long cantidadASCsPorCarrera(int id_carrera) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT COUNT(a) FROM ASC a JOIN a.carrera c JOIN a.nivelestudio ne WHERE c.id_carrera=:id_carrera AND a.tipo=:tipo");
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("tipo", TIPO_CURRICULAR);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public long cantidadCreditos_CarreraNivel(int id_carrera, int nivel) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT SUM(a.nro_creditos) FROM ASC a JOIN a.carrera c JOIN a.nivelestudio ne WHERE c.id_carrera=:id_carrera AND ne.nivel=:nivel");
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("nivel", nivel);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public List<ASC> ofertaMaterias(int id_carrera, int id_persona, int nivel) {
        List<ASC> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT a FROM ASC a JOIN a.carrera c JOIN a.nivelestudio n WHERE c.id_carrera=:id_carrera AND n.nivel<=:nivel AND (a.id_asc IN "
                    + "(SELECT a.id_asc FROM ASC a JOIN a.carrera c WHERE c.id_carrera=:id_carrera AND "
                    + "a.id_asc NOT IN (SELECT a1.id_asc FROM ASC a1 JOIN a1.carrera c1 JOIN a1.prerequisitos p1 WHERE c1.id_carrera=:id_carrera AND (SELECT COUNT(a2) FROM Nota n2 JOIN n2.asc a2 JOIN a2.carrera c2 JOIN n2.estudiante e2 WHERE c2.id_carrera=:id_carrera AND a2.id_asc=p1.id_asc AND e2.id_persona=:id_persona AND n2.condicion='APROBADO') = 0) AND "
                    + "a.id_asc NOT IN (SELECT a.id_asc FROM Nota n JOIN n.asc a JOIN a.carrera c JOIN n.estudiante e WHERE c.id_carrera=:id_carrera AND e.id_persona=:id_persona AND n.condicion='APROBADO')))"
                    + "ORDER BY n.nivel, a.nombre");
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("id_persona", id_persona);
            q.setParameter("nivel", nivel);

            l = q.getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return l;
    }

    public List<ASC> ofertaMaterias_InscripcionManual(int id_carrera, int id_persona, int nivel, int id_inscrito) {
        List<ASC> l = new ArrayList();

        try {
             Query q = em.createQuery("SELECT a FROM ASC a JOIN a.carrera c JOIN a.nivelestudio n WHERE c.id_carrera=:id_carrera AND n.nivel<=:nivel AND (a.id_asc IN "
                    + "(SELECT a.id_asc FROM ASC a JOIN a.carrera c WHERE c.id_carrera=:id_carrera AND "
                    + "a.id_asc NOT IN (SELECT a1.id_asc FROM ASC a1 JOIN a1.carrera c1 JOIN a1.prerequisitos p1 WHERE c1.id_carrera=:id_carrera AND (SELECT COUNT(a2) FROM Nota n2 JOIN n2.asc a2 JOIN a2.carrera c2 JOIN n2.estudiante e2 WHERE c2.id_carrera=:id_carrera AND a2.id_asc=p1.id_asc AND e2.id_persona=:id_persona AND n2.condicion='APROBADO') = 0) AND "
                    + "a.id_asc NOT IN (SELECT a.id_asc FROM Nota n JOIN n.asc a JOIN a.carrera c JOIN n.estudiante e WHERE c.id_carrera=:id_carrera AND e.id_persona=:id_persona AND n.condicion='APROBADO')) AND "
                    + "a.id_asc NOT IN (SELECT a.id_asc FROM Nota n JOIN n.inscrito i JOIN n.asc a WHERE i.id_inscrito=:id_inscrito)) "
                    + "ORDER BY n.nivel, a.nombre");
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("id_persona", id_persona);
            q.setParameter("nivel", nivel);
            q.setParameter("id_inscrito", id_inscrito);

            l = q.getResultList();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return l;
    }

    public int nivelAsignaturasCurriculares(int id_carrera, int id_persona) {
        int nivel = 0;

        try {
            Query q = em.createQuery("SELECT MIN(ne.nivel) FROM ASC a JOIN a.carrera c JOIN a.nivelestudio ne WHERE c.id_carrera=:id_carrera AND a.tipo='CURRICULAR' AND a.id_asc NOT IN (SELECT a.id_asc FROM Nota n JOIN n.asc a JOIN n.estudiante e WHERE e.id_persona=:id_persona AND a.tipo='CURRICULAR' AND n.condicion='APROBADO')");
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("id_persona", id_persona);

            nivel = (int) q.getSingleResult();
        } catch (Exception e) {

        }

        return nivel;
    }

    public int nivelAsignaturasNoCurriculares(int id_carrera, int id_persona) {
        int nivel = 0;

        try {
            Query q = em.createQuery("SELECT MIN(ne.nivel) FROM ASC a JOIN a.carrera c JOIN a.nivelestudio ne WHERE c.id_carrera=:id_carrera AND a.tipo='NO CURRICULAR' AND a.id_asc NOT IN (SELECT a.id_asc FROM Nota n JOIN n.asc a JOIN n.estudiante e WHERE e.id_persona=:id_persona AND a.tipo='NO CURRICULAR' AND n.condicion='APROBADO')");
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("id_persona", id_persona);

            nivel = (int) q.getSingleResult();
        } catch (Exception e) {

        }

        return nivel;
    }

    public List<ASC> listaASCs_Pendientes(int id_carrera, int id_persona) {
        List<ASC> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT a FROM ASC a JOIN a.carrera c JOIN a.nivelestudio n WHERE c.id_carrera=:id_carrera AND a.id_asc NOT IN (SELECT a.id_asc FROM Nota n JOIN n.asc a JOIN a.carrera c JOIN n.estudiante e WHERE c.id_carrera=:id_carrera AND e.id_persona=:id_persona AND n.condicion=:condicion) ORDER BY n.nivel, a.nombre");
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("id_persona", id_persona);
            q.setParameter("condicion", CONDICION_APROBADO);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

}
