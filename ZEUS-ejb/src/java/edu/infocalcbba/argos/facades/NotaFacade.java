/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.facades;

import edu.infocalcbba.argos.entities.Nota;
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
public class NotaFacade extends AbstractFacade<Nota> {

    private static final String CONDICION_APROBADO = "APROBADO";
    private static final String CONDICION_REPROBADO = "REPROBADO";
    private static final String CONDICION_ABIERTA = "ABIERTA";
    private static final String TIPO_CURRICULAR = "CURRICULAR";

    private static final String MOD_CI = "CONVALIDACION INTERNA";

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public NotaFacade() {
        super(Nota.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public Nota buscarNota_EstudianteGrupo(int id_persona, int id_grupo) {
        Nota nota = null;

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.inscrito i JOIN i.estudiante e JOIN n.grupo g WHERE e.id_persona=:id_persona AND g.id_grupo=:id_grupo");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_grupo", id_grupo);

            nota = (Nota) q.getSingleResult();
        } catch (Exception e) {

        }

        return nota;
    }

    public Nota buscarNota_EstudianteASCCondicion(int id_persona, int id_asc, String condicion) {
        Nota nota = null;

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.estudiante e JOIN n.asc a WHERE e.id_persona=:id_persona AND a.id_asc=:id_asc AND n.condicion=:condicion");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_asc", id_asc);
            q.setParameter("condicion", condicion);

            nota = (Nota) q.getSingleResult();
        } catch (Exception e) {

        }

        return nota;
    }

    public Nota buscarNotaActiva_EstudianteASC(int id_persona, int id_asc) {
        Nota nota = null;

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN n.gestionacademica ga WHERE e.id_persona=:id_persona AND a.id_asc=:id_asc AND ga.condicion=:condicion");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_asc", id_asc);
            q.setParameter("condicion", CONDICION_ABIERTA);

            nota = (Nota) q.getSingleResult();
        } catch (Exception e) {

        }

        return nota;
    }

    public List<Nota> listaNotas(int id_grupo) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.grupo g JOIN n.inscrito i JOIN i.estudiante e WHERE g.id_grupo=:id_grupo ORDER BY e.papellido, e.sapellido, e.nombre");
            q.setParameter("id_grupo", id_grupo);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Nota> listaNotasRecuperacion(int id_gestionacademica, int id_persona) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT DISTINCT n FROM Detalle d JOIN d.nota n JOIN n.grupo g JOIN g.paralelo p JOIN p.gestionacademica e JOIN n.estudiante s JOIN g.empleado m WHERE e.id_gestionacademica=:id_gestionacademica AND m.id_persona=:id_persona ORDER BY s.papellido, s.sapellido, s.nombre");
            q.setParameter("id_gestionacademica", id_gestionacademica);
            q.setParameter("id_persona", id_persona);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Nota> listaNotasRecuperacion(int id_inscrito) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.gestionacademica g JOIN g.evaluacion e JOIN n.inscrito i WHERE i.id_inscrito=:id_inscrito AND (n.nf>=e.nota_min_ins AND n.nf<e.nota_min_apr) AND n.ins IS NULL");
            q.setParameter("id_inscrito", id_inscrito);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Nota> listaNotas(int id_grupo, String modalidad) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.grupo g JOIN n.inscrito i JOIN i.estudiante e WHERE g.id_grupo=:id_grupo AND n.modalidad=:modalidad ORDER BY e.papellido, e.sapellido, e.nombre");
            q.setParameter("id_grupo", id_grupo);
            q.setParameter("modalidad", modalidad);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Nota> listaNotasInscrito(int id_inscrito) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.inscrito i JOIN n.grupo g JOIN g.asc a JOIN a.nivelestudio ne WHERE i.id_inscrito=:id_inscrito ORDER BY ne.nivel, a.nombre");
            q.setParameter("id_inscrito", id_inscrito);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Nota> kardexEstudiante(int id_persona, int id_carrera) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN n.gestionacademica ga JOIN a.nivelestudio ne WHERE e.id_persona=:id_persona AND c.id_carrera=:id_carrera ORDER BY ga.inicio, ne.nivel, a.nombre");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Nota> listaNotas(int id_persona, int id_gestionacademica, int id_asc) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.estudiante e JOIN n.gestionacademica ga JOIN n.asc a WHERE e.id_persona=:id_persona AND ga.id_gestionacademica=:id_gestionacademica AND a.id_asc=:id_asc");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_gestionacademica", id_gestionacademica);
            q.setParameter("id_asc", id_asc);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Nota> listaNotas(int id_persona, int id_asc, String condicion) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.estudiante e JOIN n.asc a WHERE e.id_persona=:id_persona AND a.id_asc=:id_asc AND n.condicion=:condicion");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_asc", id_asc);
            q.setParameter("condicion", condicion);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Nota> listaNotas_EstudianteASC(int id_persona, int id_asc) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN n.inscrito i WHERE e.id_persona=:id_persona AND a.id_asc=:id_asc ORDER BY i.fecha");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_asc", id_asc);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Nota> listaNotas_EstudianteGA(int id_persona, int id_carrera, int id_gestionacademica) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN a.nivelestudio i JOIN n.gestionacademica g WHERE e.id_persona=:id_persona AND c.id_carrera=:id_carrera AND g.id_gestionacademica=:id_gestionacademica ORDER BY i.nivel, a.nombre");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("id_gestionacademica", id_gestionacademica);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
    
     public List<Nota> listaNotas_HistorialAcademico(int id_persona, int id_carrera, int periodo) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN a.nivelestudio i JOIN n.gestionacademica g WHERE e.id_persona=:id_persona AND c.id_carrera=:id_carrera AND g.periodo<=:periodo AND a.tipo=:tipo ORDER BY i.nivel, a.nombre");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("periodo", periodo);
            q.setParameter("tipo", TIPO_CURRICULAR);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public long numeroMateriasCursadas(int id_persona, int id_carrera) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT COUNT(n) FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN n.gestionacademica ga JOIN a.nivelestudio ne WHERE e.id_persona=:id_persona AND c.id_carrera=:id_carrera");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public long numeroMateriasPR(int id_persona, int id_carrera) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT COUNT(n) FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN n.gestionacademica ga JOIN a.nivelestudio ne WHERE e.id_persona=:id_persona AND c.id_carrera=:id_carrera AND n.ins != null");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public long numeroMateriasAprobadas(int id_persona, int id_carrera) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT COUNT(n) FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN n.gestionacademica ga JOIN a.nivelestudio ne WHERE e.id_persona=:id_persona AND c.id_carrera=:id_carrera AND n.condicion=:condicion");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("condicion", CONDICION_APROBADO);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public long numeroMateriasReprobadas(int id_persona, int id_carrera) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT COUNT(n) FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN n.gestionacademica ga JOIN a.nivelestudio ne WHERE e.id_persona=:id_persona AND c.id_carrera=:id_carrera AND n.condicion=:condicion");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("condicion", CONDICION_REPROBADO);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public double promedioGeneral(int id_persona, int id_carrera) {
        double res = 0;

        try {
            Query q = em.createQuery("SELECT AVG(COALESCE(n.ins, n.nf)) FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN n.gestionacademica ga JOIN a.nivelestudio ne WHERE e.id_persona=:id_persona AND c.id_carrera=:id_carrera");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);

            res = (double) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }
    
     public double promedioGeneral_HistorialAcademico(int id_persona, int id_carrera, int periodo) {
        double res = 0;

        try {
            Query q = em.createQuery("SELECT AVG(COALESCE(n.ins, n.nf)) FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN n.gestionacademica ga JOIN a.nivelestudio ne WHERE e.id_persona=:id_persona AND c.id_carrera=:id_carrera AND ga.periodo<=:periodo AND a.tipo=:tipo");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("periodo", periodo);
            q.setParameter("tipo", TIPO_CURRICULAR);

            res = (double) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public double promedioMateriasAprobadas(int id_persona, int id_carrera) {
        double res = 0;

        try {
            Query q = em.createQuery("SELECT AVG(COALESCE(n.ins, n.nf)) FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN n.gestionacademica ga JOIN a.nivelestudio ne WHERE e.id_persona=:id_persona AND c.id_carrera=:id_carrera AND n.condicion=:condicion");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("condicion", CONDICION_APROBADO);

            res = (double) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public long contarNotas(int id_grupo, String modalidad) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT COUNT(n) FROM Nota n JOIN n.grupo g WHERE g.id_grupo=:id_grupo AND n.modalidad=:modalidad");
            q.setParameter("id_grupo", id_grupo);
            q.setParameter("modalidad", modalidad);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public long contarNotas(int id_grupo) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT COUNT(n) FROM Nota n JOIN n.grupo g WHERE g.id_grupo=:id_grupo");
            q.setParameter("id_grupo", id_grupo);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public long contarNotas_EstudianteASC(int id_persona, int id_asc) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT COUNT(n) FROM Nota n JOIN n.estudiante e JOIn n.asc a WHERE e.id_persona=:id_persona AND a.id_asc=:id_asc");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_asc", id_asc);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public long cantidadNotasAprobadas_EstudianteNivel(int id_persona, int id_carrera, int nivel) {
        long res = -1;

        try {
            Query q = em.createQuery("SELECT COUNT(n) FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN a.nivelestudio i WHERE e.id_persona=:id_persona AND c.id_carrera=:id_carrera AND i.nivel=:nivel AND n.condicion=:condicion AND a.tipo=:tipo");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("nivel", nivel);
            q.setParameter("condicion", CONDICION_APROBADO);
            q.setParameter("tipo", TIPO_CURRICULAR);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }
    
    public long cantidadNotasAprobadas_EstudianteCarrera(int id_persona, int id_carrera, int periodo) {
        long res = -1;

        try {
            Query q = em.createQuery("SELECT COUNT(n) FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN a.nivelestudio i JOIN n.gestionacademica g WHERE e.id_persona=:id_persona AND c.id_carrera=:id_carrera AND g.periodo<=:periodo AND n.condicion=:condicion AND a.tipo=:tipo");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("periodo", periodo);
            q.setParameter("condicion", CONDICION_APROBADO);
            q.setParameter("tipo", TIPO_CURRICULAR);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public long cantidadNotasReprobadas_EstudianteNivel(int id_persona, int id_carrera, int nivel) {
        long res = -1;

        try {
            Query q = em.createQuery("SELECT COUNT(n) FROM Nota n JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN a.nivelestudio i WHERE e.id_persona=:id_persona AND c.id_carrera=:id_carrera AND i.nivel=:nivel AND n.condicion=:condicion AND a.tipo=:tipo");
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("nivel", nivel);
            q.setParameter("condicion", CONDICION_REPROBADO);
            q.setParameter("tipo", TIPO_CURRICULAR);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public List<Nota> notasCurriculares_PerEstCarNiv(int periodo, int id_persona, int id_carrera, int nivel) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.gestionacademica ga JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN a.nivelestudio ne WHERE a.tipo=:tipo_curricular AND ga.periodo<=:periodo AND e.id_persona=:id_persona AND c.id_carrera=:id_carrera AND ne.nivel=:nivel");
            q.setParameter("tipo_curricular", TIPO_CURRICULAR);
            q.setParameter("periodo", periodo);
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("nivel", nivel);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Nota> notasCurricularesApr_PerEstCarNiv(int periodo, int id_persona, int id_carrera, int nivel) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.gestionacademica ga JOIN ga.evaluacion v JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN a.nivelestudio ne WHERE a.tipo=:tipo_curricular AND ga.periodo<=:periodo AND e.id_persona=:id_persona AND c.id_carrera=:id_carrera AND ne.nivel=:nivel AND (n.nf>=v.nota_min_apr OR n.ins>=v.nota_min_apr)");
            q.setParameter("tipo_curricular", TIPO_CURRICULAR);
            q.setParameter("periodo", periodo);
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("nivel", nivel);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
    
    public List<Nota> notasCurricularesRep_PerEstCarNiv(int periodo, int id_persona, int id_carrera, int nivel) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.gestionacademica ga JOIN ga.evaluacion v JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN a.nivelestudio ne WHERE a.tipo=:tipo_curricular AND ga.periodo<=:periodo AND e.id_persona=:id_persona AND c.id_carrera=:id_carrera AND ne.nivel=:nivel AND ((n.nf<v.nota_min_apr AND n.ins IS NULL) OR (n.nf<v.nota_min_apr AND n.ins<v.nota_min_apr))");
            q.setParameter("tipo_curricular", TIPO_CURRICULAR);
            q.setParameter("periodo", periodo);
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("nivel", nivel);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }
    
    public List<Nota> notasCurricularesApr_GesEstCarNiv(int id_gestionacademica, int id_persona, int id_carrera, int nivel) {
        List<Nota> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT n FROM Nota n JOIN n.gestionacademica ga JOIN ga.evaluacion v JOIN n.estudiante e JOIN n.asc a JOIN a.carrera c JOIN a.nivelestudio ne WHERE a.tipo=:tipo_curricular AND ga.id_gestionacademica=:id_gestionacademica AND e.id_persona=:id_persona AND c.id_carrera=:id_carrera AND ne.nivel=:nivel AND (n.nf>=v.nota_min_apr OR n.ins>=v.nota_min_apr)");
            q.setParameter("tipo_curricular", TIPO_CURRICULAR);
            q.setParameter("id_gestionacademica", id_gestionacademica);
            q.setParameter("id_persona", id_persona);
            q.setParameter("id_carrera", id_carrera);
            q.setParameter("nivel", nivel);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public int cursoInscrito(int id_inscrito) {
        int res = 0;

        try {
            Query q = em.createQuery("SELECT MIN(ne.nivel) FROM Nota n JOIN n.inscrito i JOIN n.asc a JOIN a.nivelestudio ne WHERE i.id_inscrito=:id_inscrito");
            q.setParameter("id_inscrito", id_inscrito);

            res = (int) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public double promedioInscrito(int id_inscrito) {
        double res = 0;

        try {
            Query q = em.createQuery("SELECT AVG(COALESCE(n.ins, n.nf)) FROM Nota n JOIN n.inscrito i JOIN n.grupo g JOIN g.asc a JOIN a.nivelestudio ne WHERE i.id_inscrito=:id_inscrito");
            q.setParameter("id_inscrito", id_inscrito);

            res = (double) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

    public long colegiaturaInscrito_NoCI(int id_inscrito) {
        long colegiatura = 0;

        try {
            Query q = em.createQuery("SELECT SUM(a.nro_creditos * p.costo_credito) FROM Nota n JOIN n.grupo g JOIN g.paralelo p JOIN n.asc a JOIN n.inscrito i WHERE i.id_inscrito=:id_inscrito AND n.modalidad!=:modalidad");
            q.setParameter("id_inscrito", id_inscrito);
            q.setParameter("modalidad", MOD_CI);

            colegiatura = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return colegiatura;
    }

    public long colegiaturaInscrito_CI(int id_inscrito) {
        long colegiatura = 0;

        try {
            Query q = em.createQuery("SELECT SUM(p.costo_credito) FROM Nota n JOIN n.grupo g JOIN g.paralelo p JOIN n.asc a JOIN n.inscrito i WHERE i.id_inscrito=:id_inscrito AND n.modalidad=:modalidad");
            q.setParameter("id_inscrito", id_inscrito);
            q.setParameter("modalidad", MOD_CI);

            colegiatura = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return colegiatura;
    }

    public long numeroMateriasReprobadas(int id_inscrito) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT COUNT(n) FROM Nota n JOIN n.inscrito i JOIN n.asc a WHERE i.id_inscrito=:id_inscrito AND a.tipo='CURRICULAR' AND n.condicion=:condicion");
            q.setParameter("id_inscrito", id_inscrito);
            q.setParameter("condicion", CONDICION_REPROBADO);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }
    
    public long numeroMateriasCI(int id_inscrito) {
        long res = 0;

        try {
            Query q = em.createQuery("SELECT COUNT(n) FROM Nota n JOIN n.inscrito i WHERE i.id_inscrito=:id_inscrito AND n.modalidad=:modalidad");
            q.setParameter("id_inscrito", id_inscrito);
            q.setParameter("modalidad", MOD_CI);

            res = (long) q.getSingleResult();
        } catch (Exception e) {

        }

        return res;
    }

}
