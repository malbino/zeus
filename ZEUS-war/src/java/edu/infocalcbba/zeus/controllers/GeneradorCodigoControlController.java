/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.controllers;

import edu.infocalcbba.publica.controllers.Controller;
import edu.infocalcbba.util.Redondeo;
import edu.infocalcbba.util.Reloj;
import edu.infocalcbba.zeus.facturacion.cc7.CC7;
import java.io.Serializable;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Tincho
 */
@Named("GeneradorCodigoControlController")
@SessionScoped
public class GeneradorCodigoControlController extends Controller implements Serializable {

    private Long numeroAutorizacion;
    private Integer numeroFactura;
    private Long nitCiCliente;
    private Date fecha;
    private Double totalFactura;
    private String llaveDosificacion;

    private String codigoControl;

    public void generar() {
        try {
            String numero_autorizacion = String.valueOf(numeroAutorizacion);
            String numero_factura = String.valueOf(numeroFactura);
            String nit_ci = String.valueOf(nitCiCliente);
            String fecha_transaccion = Reloj.formatearFecha_yyyyMMdd(fecha);
            String monto_transaccion = String.valueOf((int) Redondeo.redondear_HALFUP(totalFactura, 0));
            String llave_dosificacion = llaveDosificacion;

            codigoControl = CC7.obtener(numero_autorizacion, numero_factura, nit_ci, fecha_transaccion, monto_transaccion, llave_dosificacion);
        } catch (Exception e) {
            this.mensajeDeError("Error al generar el codigo de control.");
        }
    }

    public void limpiar() {
        numeroAutorizacion = null;
        numeroFactura = null;
        nitCiCliente = null;
        fecha = null;
        totalFactura = null;
        llaveDosificacion = null;

        codigoControl = null;
    }

    /**
     * @return the numeroAutorizacion
     */
    public Long getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    /**
     * @param numeroAutorizacion the numeroAutorizacion to set
     */
    public void setNumeroAutorizacion(Long numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    /**
     * @return the numeroFactura
     */
    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    /**
     * @param numeroFactura the numeroFactura to set
     */
    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    /**
     * @return the nitCiCliente
     */
    public Long getNitCiCliente() {
        return nitCiCliente;
    }

    /**
     * @param nitCiCliente the nitCiCliente to set
     */
    public void setNitCiCliente(Long nitCiCliente) {
        this.nitCiCliente = nitCiCliente;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the totalFactura
     */
    public Double getTotalFactura() {
        return totalFactura;
    }

    /**
     * @param totalFactura the totalFactura to set
     */
    public void setTotalFactura(Double totalFactura) {
        this.totalFactura = totalFactura;
    }

    /**
     * @return the llaveDosificacion
     */
    public String getLlaveDosificacion() {
        return llaveDosificacion;
    }

    /**
     * @param llaveDosificacion the llaveDosificacion to set
     */
    public void setLlaveDosificacion(String llaveDosificacion) {
        this.llaveDosificacion = llaveDosificacion;
    }

    /**
     * @return the codigoControl
     */
    public String getCodigoControl() {
        return codigoControl;
    }

    /**
     * @param codigoControl the codigoControl to set
     */
    public void setCodigoControl(String codigoControl) {
        this.codigoControl = codigoControl;
    }

}
