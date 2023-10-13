/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.controllers;

import edu.infocalcbba.argos.entities.GestionAcademica;
import edu.infocalcbba.argos.facades.GestionAcademicaFacade;
import edu.infocalcbba.publica.entities.Funcionalidad;
import edu.infocalcbba.publica.entities.Rol;
import edu.infocalcbba.publica.facades.FuncionalidadFacade;
import edu.infocalcbba.publica.facades.RolFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Tincho
 */
@Named("RolController")
@SessionScoped
public class RolController extends Controller implements Serializable {

    @EJB
    RolFacade rolFacade;
    @EJB
    FuncionalidadFacade funcionalidadFacade;
    @EJB
    GestionAcademicaFacade gestionAcademicaFacade;

    private Rol nuevoRol;
    private Rol seleccionRol;
    private List<Rol> roles;

    @PostConstruct
    public void init() {
        nuevoRol = new Rol();
        seleccionRol = null;
        roles = rolFacade.listaRoles();
    }

    public void reinit() {
        nuevoRol = new Rol();
        seleccionRol = null;
        roles = rolFacade.listaRoles();
    }

    public List<Funcionalidad> listaFuncionalidades() {
        return funcionalidadFacade.listaFuncionalidades();
    }

    public List<GestionAcademica> listaGestionesAcademicas() {
        return gestionAcademicaFacade.listaGestionesAcademicas();
    }

    public void crearRol() throws IOException {
        if (rolFacade.buscarRol(nuevoRol.getCodigo()) == null) {
            if (rolFacade.create(nuevoRol)) {
                reinit();

                this.redireccionarViewId("/admin/rol/Roles.xhtml");
            } else {
                this.mensajeDeError("No se pudo crear el rol.");
            }
        } else {
            this.mensajeDeError("Codigo repetido.");
        }
    }

    public void editarRol() throws IOException {
        if (rolFacade.buscarRol(seleccionRol.getCodigo(), seleccionRol.getId_rol()) == null) {
            if (rolFacade.edit(seleccionRol)) {
                reinit();

                this.redireccionarViewId("/admin/rol/Roles.xhtml");
            } else {
                this.mensajeDeError("No se pudo editar el rol.");
            }
        } else {
            this.mensajeDeError("Codigo repetido.");
        }
    }

    public void toRoles() throws IOException {
        this.redireccionarViewId("/admin/rol/Roles.xhtml");
    }

    public void toNuevoRol() throws IOException {
        this.redireccionarViewId("/admin/rol/nuevoRol.xhtml");
    }

    public void toEditarRol() throws IOException {
        this.redireccionarViewId("/admin/rol/editarRol.xhtml");
    }

    /**
     * @return the nuevoRol
     */
    public Rol getNuevoRol() {
        return nuevoRol;
    }

    /**
     * @param nuevoRol the nuevoRol to set
     */
    public void setNuevoRol(Rol nuevoRol) {
        this.nuevoRol = nuevoRol;
    }

    /**
     * @return the seleccionRol
     */
    public Rol getSeleccionRol() {
        return seleccionRol;
    }

    /**
     * @param seleccionRol the seleccionRol to set
     */
    public void setSeleccionRol(Rol seleccionRol) {
        this.seleccionRol = seleccionRol;
    }

    /**
     * @return the roles
     */
    public List<Rol> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

}
