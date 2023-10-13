/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.facades;

import edu.infocalcbba.publica.entities.Parametro;
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
public class ParametroFacade extends AbstractFacade<Parametro> {

    public static final int COSTO_CREDITO = 1;
    public static final int HOST_CORREO = 2;
    public static final int PUERTO_CORREO = 3;
    public static final int MAIL_REMITENTE = 4;
    public static final int PWD_REMITENTE = 5;
    public static final int NOMBRE_REMITENTE = 6;
    
    public static final int CI_RESP_LIBROVENTASIVA = 7;
    public static final int NOM_RESP_LIBROVENTASIVA = 8;
    
    public static final int HOST = 16;
    public static final int USERNAME = 17;
    public static final int PASSWORD = 18;
    public static final int SERVER_ESTUDIANTE = 19;
    public static final int SERVER_EMPLEADO = 20;
    public static final int PROFILE_ESTUDIANTE = 21;
    public static final int PROFILE_EMPLEADO = 22;

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public ParametroFacade() {
        super(Parametro.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Parametro> listaParametros() {
        List<Parametro> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT p FROM Parametro p ORDER BY p.nombre");
            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

    public List<Parametro> listaParametros(int id_tipoparametro) {
        List<Parametro> l = new ArrayList();

        try {
            Query q = em.createQuery("SELECT p FROM Parametro p JOIN p.tipoparametro tp WHERE tp.id_tipoparametro=:id_tipoparametro ORDER BY p.nombre");
            q.setParameter("id_tipoparametro", id_tipoparametro);

            l = q.getResultList();
        } catch (Exception e) {

        }

        return l;
    }

}
