/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.facades;

import edu.infocalcbba.publica.entities.Persona;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tincho
 */
@Stateless
@LocalBean
public class PersonaFacade extends AbstractFacade<Persona> {

    @PersistenceContext(unitName = "zeus-pu")
    private EntityManager em;

    public PersonaFacade() {
        super(Persona.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
