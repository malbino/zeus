/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.phoenix.converters;

import edu.infocalcbba.phoenix.entities.GrupoPh;
import edu.infocalcbba.phoenix.facades.GrupoPhFacade;
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
@Named(value = "GrupoPhConverter")
@RequestScoped
public class GrupoPhConverter implements Converter {

    @EJB
    GrupoPhFacade grupoPhFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        Object o;

        if (submittedValue == null || submittedValue.isEmpty()) {
            o = null;
        } else {
            o = grupoPhFacade.find(Integer.valueOf(submittedValue));
        }

        return o;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        String s;

        if (value == null || value.equals("")) {
            s = "";
        } else {
            s = String.valueOf(((GrupoPh) value).getId_grupo());
        }

        return s;
    }
}
