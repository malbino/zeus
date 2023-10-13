/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.filters;

import edu.infocalcbba.publica.controllers.LoginController;
import edu.infocalcbba.publica.entities.Funcionalidad;
import edu.infocalcbba.publica.entities.Rol;
import edu.infocalcbba.publica.entities.Usuario;
import edu.infocalcbba.publica.facades.FuncionalidadFacade;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author malbino
 */
@WebFilter(urlPatterns = {"/faces/home.xhtml", "/faces/admin/*", "/faces/caja/*", "/faces/configuracion/*", "/faces/facturacion/*", "/faces/reportes/*", "/reportes/*"})
public class LoginFilter implements Filter {

    @EJB
    FuncionalidadFacade funcionalidadFacade;

    @Inject
    LoginController loginController;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        Usuario usuario = loginController.getUsr();
        Rol rol = loginController.getSeleccionRol();
        if (usuario != null && rol != null) {
            String uri = req.getRequestURI();
            if (uri.endsWith("home.xhtml")) {
                chain.doFilter(request, response);
            } else {
                List<Funcionalidad> l = funcionalidadFacade.buscarPorRolURI(rol.getId_rol(), uri);
                if (!l.isEmpty()) {
                    chain.doFilter(request, response);
                } else {
                    res.sendRedirect("/zeus/faces/home.xhtml");
                }
            }
        } else {
            res.sendRedirect("/zeus");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
