/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.controllers;

import edu.infocalcbba.publica.facades.FuncionalidadFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Tincho
 */

@Named("FuncionalidadController")
@SessionScoped
public class FuncionalidadController extends Controller implements Serializable {
    
    @EJB
    FuncionalidadFacade rolFacade;
    

    public Object getFuncionalidad(Integer key) {
        return rolFacade.find(key);
    }

}
