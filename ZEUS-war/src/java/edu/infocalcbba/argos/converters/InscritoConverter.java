/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.converters;

import edu.infocalcbba.argos.entities.Inscrito;
import edu.infocalcbba.argos.facades.InscritoFacade;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

/**
 *
 * @author Tincho
 */
@Named(value = "InscritoConverter")
@RequestScoped
public class InscritoConverter implements Converter {

    @EJB
    InscritoFacade inscritoFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        Object o;

        if (submittedValue == null || submittedValue.isEmpty()) {
            o = null;
        } else {
            o = inscritoFacade.find(Integer.valueOf(submittedValue));
        }

        return o;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        String s;

        if (value == null || value.equals("")) {
            s = "";
        } else {
            s = String.valueOf(((Inscrito) value).getId_inscrito());
        }

        return s;
    }
}
