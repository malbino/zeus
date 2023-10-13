/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Tincho
 */
public class Mailing {

    String host_correo;
    String puerto_correo;
    String mail_remitente;
    String pwd_remitente;
    String nombre_remitente;

    String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n"
            + "<html>\n"
            + "<head>\n"
            + "<meta content=\"text/html; charset=UTF-8\" http-equiv=\"Content-Type\" />\n"
            + "<title>Email Template - Geometric</title>\n"
            + "<style type=\"text/css\">\n"
            + "a:hover { color: #09F !important; text-decoration: underline !important; }\n"
            + "a:hover#vw { background-color: #CCC !important; text-decoration: none !important; color:#000 !important; }\n"
            + "a:hover#ff { background-color: #6CF !important; text-decoration: none !important; color:#FFF !important; }\n"
            + "</style>\n"
            + "</head>\n"
            + "<body marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" style=\"margin: 0px; background-color: #FFFFFF;\" bgcolor=\"#FFFFFF\" leftmargin=\"0\">\n"
            + "<!--100% body table-->\n"
            + "<table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#FFFFFF\">\n"
            + "    <tr>\n"
            + "        <td>\n"
            + "            <!--email container-->\n"
            + "            <table cellspacing=\"0\" border=\"0\" align=\"center\" cellpadding=\"0\" width=\"624\">\n"
            + "                <tr>\n"
            + "                    <td>\n"
            + "                        <!--header-->\n"
            + "                        <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"624\">\n"
            + "                            <tr>\n"
            + "                                <td valign=\"top\">\n"
            + "                                    <!--top links-->\n"
            + "                                    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"624\">\n"
            + "                                        <tr>\n"
            + "                                            <td valign=\"middle\" width=\"221\">\n"
            + "                                                <br/>\n"
            + "                                                <p style=\"font-size: 12px; font-family: Helvetica, Arial, sans-serif; color: #333; margin: 0px;\">Correo electronico enviado por <a href=\"https://siic.infocaloruro.edu.bo/zeus\" style=\"color: #4b98d7; text-decoration: none;\">ZEUS subsede Oruro</a></p>\n"
            + "                                            </td>\n"
            + "                                        </tr>\n"
            + "                                    </table>\n"
            + "                                    <!--/top links-->\n"
            + "                                    <!--line break-->\n"
            + "                                    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"624\">\n"
            + "                                        <tr>\n"
            + "                                            <hr/>\n"
            + "                                        </tr>\n"
            + "                                    </table>\n"
            + "                                    <!--/line break-->\n"
            + "                                    <!--header content-->\n"
            + "                                    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"624\">\n"
            + "                                        <tr>\n"
            + "                                            <td>\n"
            + "                                                <img src=\"https://siic.infocaloruro.edu.bo/zeus/resources/images/logo_infocal.png\" width=\"624\" height=\"231\"/>"
            + "                                                <h2 style=\"color: #333; margin: 0px; font-weight: normal; font-size: 12px; font-family: Helvetica, Arial, sans-serif;\">INFOCAL, <fecha></h2>\n"
            + "                                            </td>\n"
            + "                                            \n"
            + "                                        </tr>\n"
            + "                                    </table>\n"
            + "                                    <!--/header content-->\n"
            + "                                </td>\n"
            + "                            </tr>\n"
            + "                        </table>\n"
            + "                        <!--/header-->\n"
            + "                        <!--line break-->\n"
            + "                        <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"624\">\n"
            + "                            <tr>\n"
            + "                                <hr/>\n"
            + "                            </tr>\n"
            + "                        </table>\n"
            + "                        <!--/line break-->\n"
            + "                        <!--email content-->\n"
            + "                        <table cellspacing=\"0\" border=\"0\" id=\"email-content\" cellpadding=\"0\" width=\"624\">\n"
            + "                            <tr>\n"
            + "                                <td>\n"
            + "                                    <!--section 1-->\n"
            + "                                    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"624\">\n"
            + "                                        <tr>\n"
            + "                                            <td>\n"
            + "                                                <h2 style=\"font-size: 36px; font-family: Helvetica, Arial, sans-serif; color: #333 !important; margin: 0px;\"><asunto></h2>\n"
            + "                                                <p style=\"font-size: 17px; line-height: 24px; font-family: Georgia, 'Times New Roman', Times, serif; color: #333; margin: 0px; text-align: left;\">Estimado(a) <nombre>,</p>\n"
            + "						<p style=\"font-size: 17px; line-height: 24px; font-family: Georgia, 'Times New Roman', Times, serif; color: #333; margin: 0px; text-align: justify;\"><mensaje></p>\n"
            + "\n"
            + "						<br/>\n"
            + "						<p style=\"font-size: 17px; line-height: 24px; font-family: Georgia, 'Times New Roman', Times, serif; color: #333; margin: 0px; text-align: center;\"><a href=\"https://siic.infocaloruro.edu.bo/zeus\" style=\"color: #4b98d7; text-decoration: none;\">ZEUS</a></p>\n"
            + "                                            </td>\n"
            + "                                        </tr>\n"
            + "                                    </table>\n"
            + "                                    <!--/section 1-->\n"
            + "                                    <!--line break-->\n"
            + "                        	    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"624\">\n"
            + "                                    	<tr>\n"
            + "                                		<hr/>\n"
            + "                            		</tr>\n"
            + "                                    </table>\n"
            + "                                    <!--/line break-->\n"
            + "                                    <!--footer-->\n"
            + "                                    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"624\">\n"
            + "                                        <tr>\n"
            + "                                            <td>\n"
            + "                                                <p style=\"font-size: 12px; font-family: Georgia, 'Times New Roman', Times, serif; color: #333; margin: 0px;\">Av. Jaime Paz N° 2739 Frente a la Rotonda del Aeropuerto Teléfono:6633830 WhatsApp:78259906\n"
            + "						<br/>\n"
            + "						<br/>\n"
            + "						Web: <a href=\"http://www.infocaloruro.edu.bo/\" style=\"color: #4b98d7; text-decoration: none;\">Instituto Tecnológico Infocal Oruro</a> Facebook: infocal.tec</p>\n"
            + "                                            </td>\n"
            + "                                        </tr>\n"
            + "                                    </table>\n"
            + "                                    <!--/footer-->\n"
            + "                                </td>\n"
            + "                            </tr>\n"
            + "                        </table>\n"
            + "                        <!--/email content-->\n"
            + "                    </td>\n"
            + "                </tr>\n"
            + "            </table>\n"
            + "            <!--/email container-->\n"
            + "        </td>\n"
            + "    </tr>\n"
            + "</table>\n"
            + "<!--/100% body table-->\n"
            + "</body>\n"
            + "</html>";

    public Mailing(String host_correo, String puerto_correo, String mail_remitente, String pwd_remitente, String nombre_remitente) {
        this.host_correo = host_correo;
        this.puerto_correo = puerto_correo;
        this.mail_remitente = mail_remitente;
        this.pwd_remitente = pwd_remitente;
        this.nombre_remitente = nombre_remitente;
    }

    public boolean enviarCorreo(String mail_destino, String asunto, String titulo, String nombre, String mensaje) {
        try {
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", host_correo);
            props.setProperty("mail.smtp.ssl.enable", "true");
            props.setProperty("mail.smtp.port", puerto_correo);
            props.setProperty("mail.smtp.auth", "true");

            // La autentificacion
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mail_remitente, pwd_remitente);
                }
            };

            // Preparamos la sesion
            Session session = Session.getInstance(props, auth);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail_remitente, nombre_remitente));
            message.setReplyTo(InternetAddress.parse(mail_remitente, false));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail_destino, false));
            message.setSubject(asunto);

            html = html.replaceAll("<fecha>", Reloj.getFecha_ddMMyyyy());
            html = html.replaceAll("<titulo>", titulo);
            html = html.replaceAll("<nombre>", nombre);
            html = html.replaceAll("<mensaje>", mensaje);
            message.setContent(html, "text/html");

            // Lo enviamos.
            Transport.send(message);

            return true;
        } catch (UnsupportedEncodingException | MessagingException e) {
            return false;
        }
    }

    public boolean enviarCorreo(String[] mail_destino, String asunto, String titulo, String nombre, String mensaje) {
        try {
            // Propiedades de la conexión
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", host_correo);
            props.setProperty("mail.smtp.ssl.enable", "true");
            props.setProperty("mail.smtp.port", puerto_correo);
            props.setProperty("mail.smtp.auth", "true");

            // La autentificacion
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mail_remitente, pwd_remitente);
                }
            };

            // Preparamos la sesion
            Session session = Session.getInstance(props, auth);

            //Configuramos los destinos
            Address[] destinos = new Address[mail_destino.length];
            for (int i = 0; i < mail_destino.length; i++) {
                destinos[i] = new InternetAddress(mail_destino[i]);
            }

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail_remitente, nombre_remitente));
            message.setReplyTo(InternetAddress.parse(mail_remitente, false));
            message.setRecipients(Message.RecipientType.TO, destinos);
            message.setSubject(asunto);

            html = html.replaceAll("<fecha>", Reloj.getFecha_ddMMyyyy());
            html = html.replaceAll("<titulo>", titulo);
            html = html.replaceAll("<nombre>", nombre);
            html = html.replaceAll("<mensaje>", mensaje);
            message.setContent(html, "text/html");

            // Lo enviamos.
            Transport.send(message);

            return true;
        } catch (UnsupportedEncodingException | MessagingException e) {
            return false;
        }
    }

}
