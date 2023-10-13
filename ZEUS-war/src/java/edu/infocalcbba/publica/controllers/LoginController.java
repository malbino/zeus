/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.controllers;

import edu.infocalcbba.argos.entities.GestionAcademica;
import edu.infocalcbba.argos.facades.GestionAcademicaFacade;
import edu.infocalcbba.publica.entities.Funcionalidad;
import edu.infocalcbba.publica.entities.Rol;
import edu.infocalcbba.publica.entities.Usuario;
import edu.infocalcbba.publica.facades.FuncionalidadFacade;
import edu.infocalcbba.publica.facades.RolFacade;
import edu.infocalcbba.publica.facades.UsuarioFacade;
import edu.infocalcbba.util.Encriptador;
import edu.infocalcbba.util.Redondeo;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.entities.Factura;
import edu.infocalcbba.zeus.entities.TurnoCaja;
import edu.infocalcbba.zeus.facades.FacturaFacade;
import edu.infocalcbba.zeus.facades.TurnoCajaFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Tincho
 */
@Named("LoginController")
@SessionScoped
public class LoginController extends Controller implements Serializable {

    @EJB
    UsuarioFacade usuarioFacade;
    @EJB
    RolFacade rolFacade;
    @EJB
    FuncionalidadFacade funcionalidadFacade;
    @EJB
    TurnoCajaFacade turnoCajaFacade;
    @EJB
    FacturaFacade facturaFacade;
    @EJB
    GestionAcademicaFacade gestionAcademicaFacade;

    private String usuario;
    private String contrasena;

    private Usuario usr;
    private Rol seleccionRol;
    private List<Rol> listaRoles;
    private MenuModel menuModel;

    public void login() throws IOException {
        usr = usuarioFacade.buscarPorUsuario(usuario);
        if (usr != null) {
            if (Encriptador.comparar(contrasena, usr.getContrasena())) {
                listaRoles = rolFacade.listaRoles(usr.getId_persona());

                if (!listaRoles.isEmpty()) {
                    seleccionRol = listaRoles.get(0);

                    menuPrincipal();
                    toHome();
                } else {
                    limpiar();

                    mensajeDeError("Cuenta invalida.");
                }
            } else {
                limpiar();

                mensajeDeError("Contraseña invalida.");
            }
        } else {
            limpiar();

            mensajeDeError("Usuario invalido.");
        }
    }

    public void limpiar() {
        usuario = null;
        contrasena = null;
        usr = null;
        seleccionRol = null;
    }

    /**
     *
     * @throws IOException
     */
    public void logout() throws IOException {
        usr = null;

        invalidateSession();
        redireccionarViewId("/index.xhtml");
    }

    public boolean verificarPrivilegio(String nombre) {
        Funcionalidad f = funcionalidadFacade.buscarPorRolNombre(seleccionRol.getId_rol(), nombre);

        return f != null;
    }

    public void menuPrincipal() {
        menuModel = new DefaultMenuModel();

        //Menu admin
        DefaultSubMenu administrador = new DefaultSubMenu("Administrador");
        administrador.setIcon("ui-icon-locked");

        DefaultMenuItem roles = new DefaultMenuItem("Roles");
        roles.setUrl("/faces/admin/rol/Roles.xhtml");
        if (verificarPrivilegio((String) roles.getValue())) {
            administrador.addElement(roles);
        }

        DefaultMenuItem usuarios = new DefaultMenuItem("Usuarios");
        usuarios.setUrl("/faces/admin/usuario/Usuarios.xhtml");
        if (verificarPrivilegio((String) usuarios.getValue())) {
            administrador.addElement(usuarios);
        }

        DefaultMenuItem conf = new DefaultMenuItem("Configuración");
        conf.setUrl("/faces/admin/configuracion/Configuracion.xhtml");
        if (verificarPrivilegio((String) conf.getValue())) {
            administrador.addElement(conf);
        }

        if (administrador.getElementsCount() > 0) {
            menuModel.addElement(administrador);
        }

        //Menu configuracion
        DefaultSubMenu configuracion = new DefaultSubMenu("Configuración");
        configuracion.setIcon("ui-icon-wrench");

        DefaultMenuItem generadorCodigoControl = new DefaultMenuItem("Generador Código de Control");
        generadorCodigoControl.setUrl("/faces/configuracion/generadorCodigoControl.xhtml");
        if (verificarPrivilegio((String) generadorCodigoControl.getValue())) {
            configuracion.addElement(generadorCodigoControl);
        }

        DefaultMenuItem pagosexternos = new DefaultMenuItem("Pagos Externos");
        pagosexternos.setUrl("/faces/configuracion/pagoexterno/PagosExternos.xhtml");
        if (verificarPrivilegio((String) pagosexternos.getValue())) {
            configuracion.addElement(pagosexternos);
        }

        DefaultMenuItem pagostramcert = new DefaultMenuItem("Pagos Tram/Cert");
        pagostramcert.setUrl("/faces/configuracion/pagotramcert/PagosTramCert.xhtml");
        if (verificarPrivilegio((String) pagostramcert.getValue())) {
            configuracion.addElement(pagostramcert);
        }

        DefaultMenuItem pagosextras = new DefaultMenuItem("Pagos Formación");
        pagosextras.setUrl("/faces/configuracion/pagoformacion/PagosFormacion.xhtml");
        if (verificarPrivilegio((String) pagosextras.getValue())) {
            configuracion.addElement(pagosextras);
        }

        DefaultMenuItem dosificaciones = new DefaultMenuItem("Dosificaciones");
        dosificaciones.setUrl("/faces/configuracion/dosificacion/Dosificaciones.xhtml");
        if (verificarPrivilegio((String) dosificaciones.getValue())) {
            configuracion.addElement(dosificaciones);
        }

        if (configuracion.getElementsCount() > 0) {
            menuModel.addElement(configuracion);
        }

        //Menu caja
        DefaultSubMenu caja = new DefaultSubMenu("Caja");
        caja.setIcon("ui-icon-home");

        DefaultMenuItem abrirTurno = new DefaultMenuItem("Abrir Turno");
        abrirTurno.setUrl("/faces/caja/AbrirTurno.xhtml");
        if (verificarPrivilegio((String) abrirTurno.getValue())) {
            caja.addElement(abrirTurno);
        }

        DefaultMenuItem arqueoCaja = new DefaultMenuItem("Arqueo de Caja");
        arqueoCaja.setCommand("#{ArqueoCajaController.init()}");
        if (verificarPrivilegio((String) arqueoCaja.getValue())) {
            caja.addElement(arqueoCaja);
        }

        if (caja.getElementsCount() > 0) {
            menuModel.addElement(caja);
        }

        //Menu facturacion
        DefaultSubMenu facturacion = new DefaultSubMenu("Facturación");
        facturacion.setIcon("ui-icon-print");

        DefaultSubMenu externa = new DefaultSubMenu("Externa");
        externa.setIcon("ui-icon-extlink");

        DefaultMenuItem facturaexterna = new DefaultMenuItem("Factura Externa");
        facturaexterna.setUrl("/faces/facturacion/facturaexterna/FacturaExterna.xhtml");
        if (verificarPrivilegio((String) facturaexterna.getValue())) {
            externa.addElement(facturaexterna);
        }

        DefaultMenuItem facturaexternanuevo = new DefaultMenuItem("Factura Externa Nuevo");
        facturaexternanuevo.setUrl("/faces/facturacion/facturaexternanuevo/FacturaExternaNuevo.xhtml");
        if (verificarPrivilegio((String) facturaexternanuevo.getValue())) {
            externa.addElement(facturaexternanuevo);
        }

        DefaultMenuItem facturaexternaregular = new DefaultMenuItem("Factura Externa Regular");
        facturaexternaregular.setUrl("/faces/facturacion/facturaexternaregular/FacturaExternaRegular.xhtml");
        if (verificarPrivilegio((String) facturaexternaregular.getValue())) {
            externa.addElement(facturaexternaregular);
        }

        DefaultMenuItem facturaexternacapacitacion = new DefaultMenuItem("Factura Externa Capacitación");
        facturaexternacapacitacion.setUrl("/faces/facturacion/facturaexternacapacitacion/FacturaExternaCapacitacion.xhtml");
        if (verificarPrivilegio((String) facturaexternacapacitacion.getValue())) {
            externa.addElement(facturaexternacapacitacion);
        }

        if (externa.getElementsCount() > 0) {
            facturacion.addElement(externa);
        }

        DefaultMenuItem facturatramcert = new DefaultMenuItem("Factura Tram/Cert");
        facturatramcert.setUrl("/faces/facturacion/facturatramcert/FacturaTramCert.xhtml");
        if (verificarPrivilegio((String) facturatramcert.getValue())) {
            facturacion.addElement(facturatramcert);
        }

        DefaultMenuItem facturaCapacitacion = new DefaultMenuItem("Factura Capacitación");
        facturaCapacitacion.setUrl("/faces/facturacion/facturacapacitacion/FacturaCapacitacion.xhtml");
        if (verificarPrivilegio((String) facturaCapacitacion.getValue())) {
            facturacion.addElement(facturaCapacitacion);
        }

        DefaultMenuItem facturaFormacion = new DefaultMenuItem("Factura Formación");
        facturaFormacion.setUrl("/faces/facturacion/facturaformacion/FacturaFormacion.xhtml");
        if (verificarPrivilegio((String) facturaFormacion.getValue())) {
            facturacion.addElement(facturaFormacion);
        }

        DefaultMenuItem facturaDefensaGrado = new DefaultMenuItem("Factura Defensa Grado");
        facturaDefensaGrado.setUrl("/faces/facturacion/facturadefensagrado/FacturaDefensaGrado.xhtml");
        if (verificarPrivilegio((String) facturaDefensaGrado.getValue())) {
            facturacion.addElement(facturaDefensaGrado);
        }

        DefaultMenuItem facturaRecuperacion = new DefaultMenuItem("Factura Recuperación");
        facturaRecuperacion.setUrl("/faces/facturacion/facturarecuperacion/FacturaRecuperacion.xhtml");
        if (verificarPrivilegio((String) facturaRecuperacion.getValue())) {
            facturacion.addElement(facturaRecuperacion);
        }

        DefaultMenuItem anularFactura = new DefaultMenuItem("Anular Factura");
        anularFactura.setUrl("/faces/facturacion/AnularFactura.xhtml");
        if (verificarPrivilegio((String) anularFactura.getValue())) {
            facturacion.addElement(anularFactura);
        }

        if (facturacion.getElementsCount() > 0) {
            menuModel.addElement(facturacion);
        }

        //Menu reportes
        DefaultSubMenu reportes = new DefaultSubMenu("Reportes");
        reportes.setIcon("ui-icon-image");

        DefaultMenuItem impresionFactura = new DefaultMenuItem("Reporte Impresión Factura");
        impresionFactura.setUrl("/faces/reportes/ReporteImpresionFactura.xhtml");
        if (verificarPrivilegio((String) impresionFactura.getValue())) {
            reportes.addElement(impresionFactura);
        }

        DefaultMenuItem reporteLibroDeVentasIVA = new DefaultMenuItem("Reporte Libro de Ventas IVA");
        reporteLibroDeVentasIVA.setUrl("/faces/reportes/ReporteLibroDeVentasIVA.xhtml");
        if (verificarPrivilegio((String) reporteLibroDeVentasIVA.getValue())) {
            reportes.addElement(reporteLibroDeVentasIVA);
        }

        DefaultMenuItem reporteHojaColectaIngresos = new DefaultMenuItem("Reporte Hoja de Colecta/Ingresos");
        reporteHojaColectaIngresos.setUrl("/faces/reportes/ReporteHojaColectaIngresos.xhtml");
        if (verificarPrivilegio((String) reporteHojaColectaIngresos.getValue())) {
            reportes.addElement(reporteHojaColectaIngresos);
        }

        DefaultMenuItem reporteFacturacionCajero = new DefaultMenuItem("Reporte Facturación Cajero");
        reporteFacturacionCajero.setUrl("/faces/reportes/ReporteFacturacionCajero.xhtml");
        if (verificarPrivilegio((String) reporteFacturacionCajero.getValue())) {
            reportes.addElement(reporteFacturacionCajero);
        }

        if (reportes.getElementsCount() > 0) {
            menuModel.addElement(reportes);
        }

        //Menu ayuda
        DefaultSubMenu ayuda = new DefaultSubMenu("Ayuda");
        ayuda.setIcon("ui-icon-help");

        DefaultMenuItem acercaDe = new DefaultMenuItem("Acerca de");
        acercaDe.setUrl("/faces/ayuda/AcercaDe.xhtml");
        ayuda.addElement(acercaDe);

        if (ayuda.getElementsCount() > 0) {
            menuModel.addElement(ayuda);
        }

    }

    public void cambiarRol() throws IOException {
        this.menuPrincipal();
        this.toHome();
    }

    public void toOpciones() throws IOException {
        this.redireccionarViewId("/restore/Opciones.xhtml");
    }

    public void toHome() throws IOException {
        this.redireccionarViewId("/home.xhtml");
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the codigosiic
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the codigosiic to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
     * @param usr the usuario to set
     */
    public void setUsr(Usuario usr) {
        this.usr = usr;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsr() {
        return usr;
    }

    /**
     * @return the menuModel
     */
    public MenuModel getMenuModel() {
        return menuModel;
    }

    /**
     * @param menuModel the menuModel to set
     */
    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }

    /**
     * @return the listaRoles
     */
    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    /**
     * @param listaRoles the listaRoles to set
     */
    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public TurnoCaja getTurnoCaja() {
        return turnoCajaFacade.turnoCajaAbierto(usr.getId_persona());
    }

    public String[] getInformacion() {
        String[] res = new String[4];

        TurnoCaja turnoCaja = turnoCajaFacade.turnoCajaAbierto(usr.getId_persona());
        if (turnoCaja != null) {
            res[0] = turnoCaja.getCaja().getCampus().getNombre();
            res[1] = turnoCaja.getCaja().getNombre();
            res[2] = Reloj.formatearFecha_ddMMyyyyHHmm(turnoCaja.getInicio());

            List<Factura> listaFacturas = facturaFacade.listaFacturas(turnoCaja.getId_turnocaja());
            double suma = 0;
            for (Factura factura : listaFacturas) {
                suma += factura.getMonto();
            }
            res[3] = String.valueOf(Redondeo.redondear_HALFUP(suma, 2));
        }

        return res;
    }

    public List<GestionAcademica> listaGestionesAcademicas() {
        return gestionAcademicaFacade.listaGestionesAcademicas(seleccionRol.getId_rol());
    }
}
