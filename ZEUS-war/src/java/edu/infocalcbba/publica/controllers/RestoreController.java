/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.controllers;

import edu.infocalcbba.publica.entities.Usuario;
import edu.infocalcbba.publica.facades.ParametroFacade;
import edu.infocalcbba.publica.facades.UsuarioFacade;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import edu.infocalcbba.util.Encriptador;
import edu.infocalcbba.util.Generador;
import edu.infocalcbba.util.Mailing;

/**
 *
 * @author Tincho
 */
@Named("RestoreController")
@SessionScoped
public class RestoreController extends Controller implements Serializable {

    private int opcion;
    private String usuario;
    private String email;
    private String pin_original;
    private String pin_usuario;

    @EJB
    UsuarioFacade usuarioFacade;

    @EJB
    ParametroFacade parametroFacade;

    public void continuar() throws IOException {
        if (opcion == 1) {
            this.redireccionarViewId("/restore/EnviarPIN.xhtml");
        } else if (opcion == 2) {
            this.redireccionarViewId("/restore/Oops.xhtml");
        }
    }

    public void enviarPIN() throws IOException {
        Usuario usr = usuarioFacade.buscarPorUsuario(getUsuario());
        if (usr != null) {
            if (usr.getEmail() != null) {
                pin_original = Generador.generarPIN();

                String host_correo = parametroFacade.find(ParametroFacade.HOST_CORREO).getValor();
                String puerto_correo = parametroFacade.find(ParametroFacade.PUERTO_CORREO).getValor();
                String mail_remitente = parametroFacade.find(ParametroFacade.MAIL_REMITENTE).getValor();
                String pwd_remitente = parametroFacade.find(ParametroFacade.PWD_REMITENTE).getValor();
                String nombre_remitente = parametroFacade.find(ParametroFacade.NOMBRE_REMITENTE).getValor();
                Mailing mailing = new Mailing(host_correo, puerto_correo, mail_remitente, pwd_remitente, nombre_remitente);

                String asunto = "Numero PIN ZEUS!";
                String titulo = "Numero PIN ZEUS!";
                String nombre = usr.getNombre();
                String mensaje = "Para restaurar su contrase&ntilde;a utilice el numero PIN."
                        + "<br/>"
                        + "<br/>"
                        + "Numero PIN: " + pin_original
                        + "<br/>"
                        + "<br/>"
                        + "Atentamente,"
                        + "<br/>"
                        + "Instituto Tecnol&oacute;gico Infocal Oruro";

                if (mailing.enviarCorreo(usr.getEmail(), asunto, titulo, nombre, mensaje)) {
                    this.redireccionarViewId("/restore/Restaurar.xhtml");
                } else {
                    this.redireccionarViewId("/restore/Error.xhtml");
                }
            } else {
                this.redireccionarViewId("/restore/ErrorMail.xhtml");
            }
        } else {
            this.redireccionarViewId("/restore/ErrorDNI.xhtml");
        }

    }

    public void restaurar() throws IOException {
        Usuario usr = usuarioFacade.buscarPorUsuario(getUsuario());
        if (usr != null) {
            if (usr.getEmail() != null) {
                if (pin_original.compareTo(pin_usuario) == 0) {
                    String contrasena = Generador.generarContrasena();
                    usr.setContrasena(Encriptador.encriptar(contrasena));

                    String host_correo = parametroFacade.find(ParametroFacade.HOST_CORREO).getValor();
                    String puerto_correo = parametroFacade.find(ParametroFacade.PUERTO_CORREO).getValor();
                    String mail_remitente = parametroFacade.find(ParametroFacade.MAIL_REMITENTE).getValor();
                    String pwd_remitente = parametroFacade.find(ParametroFacade.PWD_REMITENTE).getValor();
                    String nombre_remitente = parametroFacade.find(ParametroFacade.NOMBRE_REMITENTE).getValor();
                    Mailing mailing = new Mailing(host_correo, puerto_correo, mail_remitente, pwd_remitente, nombre_remitente);

                    String asunto = "Nueva contraseña ZEUS!";
                    String titulo = "Nueva contraseña ZEUS!";
                    String nombre = usr.getNombre();
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

                    if (usuarioFacade.edit(usr) && mailing.enviarCorreo(usr.getEmail(), asunto, titulo, nombre, mensaje)) {
                        this.redireccionarViewId("/restore/Enviado.xhtml");
                    } else {
                        this.redireccionarViewId("/restore/Error.xhtml");
                    }
                } else {
                    this.redireccionarViewId("/restore/ErrorPIN.xhtml");
                }
            } else {
                this.redireccionarViewId("/restore/ErrorMail.xhtml");
            }
        } else {
            this.redireccionarViewId("/restore/ErrorDNI.xhtml");
        }
    }

    public void toIndex() throws IOException {
        this.redireccionarViewId("/index.xhtml");
    }

    /**
     * @return the opcion
     */
    public int getOpcion() {
        return opcion;
    }

    /**
     * @param opcion the opcion to set
     */
    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the pin_original
     */
    public String getPin_original() {
        return pin_original;
    }

    /**
     * @param pin_original the pin_original to set
     */
    public void setPin_original(String pin_original) {
        this.pin_original = pin_original;
    }

    /**
     * @return the pin_usuario
     */
    public String getPin_usuario() {
        return pin_usuario;
    }

    /**
     * @param pin_usuario the pin_usuario to set
     */
    public void setPin_usuario(String pin_usuario) {
        this.pin_usuario = pin_usuario;
    }
}
