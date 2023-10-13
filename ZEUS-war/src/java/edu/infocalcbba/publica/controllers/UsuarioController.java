/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.controllers;

import edu.infocalcbba.publica.entities.Rol;
import edu.infocalcbba.publica.entities.Usuario;
import edu.infocalcbba.publica.facades.ParametroFacade;
import edu.infocalcbba.publica.facades.RolFacade;
import edu.infocalcbba.publica.facades.UsuarioFacade;
import edu.infocalcbba.util.Encriptador;
import edu.infocalcbba.util.Generador;
import edu.infocalcbba.util.Mailing;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Tincho
 */
@Named("UsuarioController")
@SessionScoped
public class UsuarioController extends Controller implements Serializable {

    @EJB
    UsuarioFacade usuarioFacade;
    @EJB
    RolFacade rolFacade;
    @EJB
    ParametroFacade parametroFacade;

    private String consulta;
    private List<Usuario> usuarios;
    private Usuario seleccionUsuario;
    private boolean restaurar_contrasena;

    public void buscarUsuarios() {
        String c = consulta.replace(" ", "").toUpperCase();
        if (!c.isEmpty()) {
            usuarios = usuarioFacade.buscarUsuarios(c);
        } else {
            usuarios = new ArrayList();
        }
    }

    public List<Rol> listaRoles() {
        return rolFacade.listaRoles();
    }

    public void editarUsuario() throws IOException {
        if (usuarioFacade.buscarPorUsuario(seleccionUsuario.getUsuario(), seleccionUsuario.getId_persona()) == null) {
            if (restaurar_contrasena) {
                if (seleccionUsuario.getEmail() != null) {
                    String contrasena = Generador.generarContrasena();
                    seleccionUsuario.setContrasena(Encriptador.encriptar(contrasena));

                    String host_correo = parametroFacade.find(ParametroFacade.HOST_CORREO).getValor();
                    String puerto_correo = parametroFacade.find(ParametroFacade.PUERTO_CORREO).getValor();
                    String mail_remitente = parametroFacade.find(ParametroFacade.MAIL_REMITENTE).getValor();
                    String pwd_remitente = parametroFacade.find(ParametroFacade.PWD_REMITENTE).getValor();
                    String nombre_remitente = parametroFacade.find(ParametroFacade.NOMBRE_REMITENTE).getValor();
                    Mailing mailing = new Mailing(host_correo, puerto_correo, mail_remitente, pwd_remitente, nombre_remitente);

                    String asunto = "Nueva contraseña ARGOS!";
                    String titulo = "Nueva contraseña ARGOS!";
                    String nombre = seleccionUsuario.getNombre();
                    String mensaje = "Tu contrase&ntilde;a ha sido restaurada exitosamente, para ingresar al sistema"
                            + " utilice su nueva contrase&ntilde;a."
                            + "<br/>"
                            + "<br/>"
                            + "Nueva contrase&ntilde;a: " + contrasena
                            + "<br/>"
                            + "<br/>"
                            + "Atentamente,"
                            + "<br/>"
                            + "Instituto Tecnol&oacute;gico Infocal Oruro";

                    if (usuarioFacade.edit(seleccionUsuario) && mailing.enviarCorreo(seleccionUsuario.getEmail(), asunto, titulo, nombre, mensaje)) {
                        restaurar_contrasena = false;

                        this.redireccionarViewId("/admin/usuario/Usuarios.xhtml");
                    } else {
                        this.mensajeDeError("No se pudo editar el usuario.");
                    }
                } else {
                    this.mensajeDeError("No se puede restaurar la cotraseña. El usuario no tiene email.");
                }
            } else {
                if (usuarioFacade.edit(seleccionUsuario)) {
                    this.redireccionarViewId("/admin/usuario/Usuarios.xhtml");
                } else {
                    this.mensajeDeError("No se pudo editar el usuario.");
                }
            }
        } else {
            this.mensajeDeError("Usuario repetido.");
        }
    }

    public void toUsuarios() throws IOException {
        this.redireccionarViewId("/admin/usuario/Usuarios.xhtml");
    }

    public void toEditarUsuario() throws IOException {
        this.redireccionarViewId("/admin/usuario/editarUsuario.xhtml");
    }

    /**
     * @return the seleccionUsuario
     */
    public Usuario getSeleccionUsuario() {
        return seleccionUsuario;
    }

    /**
     * @param seleccionUsuario the seleccionUsuario to set
     */
    public void setSeleccionUsuario(Usuario seleccionUsuario) {
        this.seleccionUsuario = seleccionUsuario;
    }

    /**
     * @return the consulta
     */
    public String getConsulta() {
        return consulta;
    }

    /**
     * @param consulta the consulta to set
     */
    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    /**
     * @return the reset
     */
    public boolean isRestaurar_contrasena() {
        return restaurar_contrasena;
    }

    /**
     * @param restaurar_contrasena the reset to set
     */
    public void setRestaurar_contrasena(boolean restaurar_contrasena) {
        this.restaurar_contrasena = restaurar_contrasena;
    }

    /**
     * @return the usuarios
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
