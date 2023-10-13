/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.log_argos.entities;

import edu.infocalcbba.util.Reloj;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "logcuota", catalog = "infocaloruro", schema = "log_argos")
public class LogCuota implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_logcuota;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    private String usuario;
    private String ip;
    private String evento;

    private Integer id_cuota;
    private String codigo;
    private String concepto;
    private Integer numero;
    private Integer monto;
    private Integer pagado;
    private Integer adeudado;
    private String condicion;
    
    public LogCuota() {
    }

    public LogCuota(Date fecha, String usuario, String ip, String evento, Integer id_cuota, String codigo, String concepto, Integer numero, Integer monto, Integer pagado, Integer adeudado, String condicion) {
        this.fecha = fecha;
        this.usuario = usuario;
        this.ip = ip;
        this.evento = evento;
        this.id_cuota = id_cuota;
        this.codigo = codigo;
        this.concepto = concepto;
        this.numero = numero;
        this.monto = monto;
        this.pagado = pagado;
        this.adeudado = adeudado;
        this.condicion = condicion;
    }

    public String getFecha_ddMMyyyyHHmmss() {
        return Reloj.formatearFecha_ddMMyyyyHHmmss(fecha);
    }

    /**
     * @return the id_logcuota
     */
    public Integer getId_logcuota() {
        return id_logcuota;
    }

    /**
     * @param id_logcuota the id_logcuota to set
     */
    public void setId_logcuota(Integer id_logcuota) {
        this.id_logcuota = id_logcuota;
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
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the evento
     */
    public String getEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(String evento) {
        this.evento = evento;
    }

    /**
     * @return the id_cuota
     */
    public Integer getId_cuota() {
        return id_cuota;
    }

    /**
     * @param id_cuota the id_cuota to set
     */
    public void setId_cuota(Integer id_cuota) {
        this.id_cuota = id_cuota;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * @return the monto
     */
    public Integer getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    /**
     * @return the pagado
     */
    public Integer getPagado() {
        return pagado;
    }

    /**
     * @param pagado the pagado to set
     */
    public void setPagado(Integer pagado) {
        this.pagado = pagado;
    }

    /**
     * @return the adeudado
     */
    public Integer getAdeudado() {
        return adeudado;
    }

    /**
     * @param adeudado the adeudado to set
     */
    public void setAdeudado(Integer adeudado) {
        this.adeudado = adeudado;
    }

    /**
     * @return the condicion
     */
    public String getCondicion() {
        return condicion;
    }

    /**
     * @param condicion the condicion to set
     */
    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }
}
