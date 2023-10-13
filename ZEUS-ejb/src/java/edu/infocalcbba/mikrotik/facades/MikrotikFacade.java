/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.mikrotik.facades;

import edu.infocalcbba.publica.facades.ParametroFacade;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.net.SocketFactory;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

/**
 *
 * @author tincho
 */
@Stateless
@LocalBean
public class MikrotikFacade {

    @EJB
    ParametroFacade parametroFacade;

    public void editLimitBytesOutHotspotUser(String name, long monto) {
        try {
            String h = parametroFacade.find(ParametroFacade.HOST).getValor();
            String u = parametroFacade.find(ParametroFacade.USERNAME).getValor();
            String p = parametroFacade.find(ParametroFacade.PASSWORD).getValor();

            try (ApiConnection apiConn = ApiConnection.connect(SocketFactory.getDefault(), h, ApiConnection.DEFAULT_PORT, ApiConnection.DEFAULT_CONNECTION_TIMEOUT)) {
                apiConn.login(u, p);

                long limitBytesOut = monto * 1048576;
                apiConn.execute("/ip/hotspot/user/set"
                        + " .id=" + name
                        + " limit-bytes-out=" + limitBytesOut);
            } catch (MikrotikApiException ex2) {

            }
        } catch (Exception ex1) {

        }
    }

    public void createEditPasswordHotspotUserEstudiante(String name, String password, String email, long monto, String comment) {
        try {
            String h = parametroFacade.find(ParametroFacade.HOST).getValor();
            String u = parametroFacade.find(ParametroFacade.USERNAME).getValor();
            String p = parametroFacade.find(ParametroFacade.PASSWORD).getValor();
            String s = parametroFacade.find(ParametroFacade.SERVER_ESTUDIANTE).getValor();
            String r = parametroFacade.find(ParametroFacade.PROFILE_ESTUDIANTE).getValor();

            try (ApiConnection apiConn = ApiConnection.connect(SocketFactory.getDefault(), h, ApiConnection.DEFAULT_PORT, ApiConnection.DEFAULT_CONNECTION_TIMEOUT)) {
                apiConn.login(u, p);

                apiConn.execute("/ip/hotspot/user/set"
                        + " .id=" + name
                        + " password=" + password);
            } catch (MikrotikApiException ex2) {
                
                try (ApiConnection apiConn = ApiConnection.connect(SocketFactory.getDefault(), h, ApiConnection.DEFAULT_PORT, ApiConnection.DEFAULT_CONNECTION_TIMEOUT)) {
                    apiConn.login(u, p);

                    long limitBytesOut = monto * 1048576;
                    if (email != null && !email.isEmpty()) {
                        apiConn.execute("/ip/hotspot/user/add"
                                + " name=" + name
                                + " password=" + password
                                + " email=" + email
                                + " server=" + s
                                + " profile=" + r
                                + " limit-bytes-out=" + limitBytesOut
                                + " comment='" + comment + "'");
                    } else {
                        apiConn.execute("/ip/hotspot/user/add"
                                + " name=" + name
                                + " password=" + password
                                + " server=" + s
                                + " profile=" + r
                                + " limit-bytes-out=" + limitBytesOut
                                + " comment='" + comment + "'");
                    }
                } catch (MikrotikApiException ex3) {
                    
                }
            }
        } catch (Exception ex1) {
            
        }
    }

    public void createEditPasswordHotspotUserEmpleado(String name, String password, String email, String comment) {
        try {
            String h = parametroFacade.find(ParametroFacade.HOST).getValor();
            String u = parametroFacade.find(ParametroFacade.USERNAME).getValor();
            String p = parametroFacade.find(ParametroFacade.PASSWORD).getValor();
            String s = parametroFacade.find(ParametroFacade.SERVER_EMPLEADO).getValor();
            String r = parametroFacade.find(ParametroFacade.PROFILE_EMPLEADO).getValor();

            try (ApiConnection apiConn = ApiConnection.connect(SocketFactory.getDefault(), h, ApiConnection.DEFAULT_PORT, ApiConnection.DEFAULT_CONNECTION_TIMEOUT)) {
                apiConn.login(u, p);

                apiConn.execute("/ip/hotspot/user/set"
                        + " .id=" + name
                        + " password=" + password);
            } catch (MikrotikApiException ex2) {
                try (ApiConnection apiConn = ApiConnection.connect(SocketFactory.getDefault(), h, ApiConnection.DEFAULT_PORT, ApiConnection.DEFAULT_CONNECTION_TIMEOUT)) {
                    apiConn.login(u, p);

                    if (email != null && !email.isEmpty()) {
                        apiConn.execute("/ip/hotspot/user/add"
                                + " name=" + name
                                + " password=" + password
                                + " email=" + email
                                + " server=" + s
                                + " profile=" + r
                                + " comment='" + comment + "'");
                    } else {
                        apiConn.execute("/ip/hotspot/user/add"
                                + " name=" + name
                                + " password=" + password
                                + " server=" + s
                                + " profile=" + r
                                + " comment='" + comment + "'");
                    }
                } catch (MikrotikApiException ex3) {

                }
            }
        } catch (Exception ex1) {

        }
    }
}
