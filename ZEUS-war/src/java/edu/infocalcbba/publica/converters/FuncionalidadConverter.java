/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.converters;

import edu.infocalcbba.publica.entities.Funcionalidad;
import edu.infocalcbba.publica.facades.FuncionalidadFacade;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Tincho
 */
@Named(value = "FuncionalidadConverter")
@RequestScoped
public class FuncionalidadConverter implements Converter {

    @EJB
    FuncionalidadFacade funcionalidadFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        Object o;

        if (submittedValue == null || submittedValue.isEmpty()) {
            o = null;
        } else {
            o = funcionalidadFacade.find(Integer.valueOf(submittedValue));
        }

        return o;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        String s;

        if (value == null || value.equals("")) {
            s = "";
        } else {
            s = String.valueOf(((Funcionalidad) value).getId_funcionalidad());
        }

        return s;
    }

}
