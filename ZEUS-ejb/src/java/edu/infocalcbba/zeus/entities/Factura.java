/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.entities;

import edu.infocalcbba.util.Reloj;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "factura", catalog = "infocaloruro", schema = "zeus", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numero", "id_dosificacion"})})
public class Factura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_factura;

    private Integer numero;
    private String codigo_control;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    private Double monto;
    private String condicion;
    private Double efectivo;
    private Double cambio;
    private Boolean valorada;

    @JoinColumn(name = "id_dosificacion")
    @ManyToOne
    private Dosificacion dosificacion;

    @OneToMany(mappedBy = "factura")
    @OrderBy("codigo")
    private List<Detalle> detalles;

    @JoinColumn(name = "id_cliente")
    @ManyToOne
    private Cliente cliente;

    @JoinColumn(name = "id_turnocaja")
    @ManyToOne
    private TurnoCaja turnocaja;

    public Factura() {
    }

    public Factura(Integer idRol) {
        this.id_factura = idRol;
    }

    public Integer getId_factura() {
        return id_factura;
    }

    public void setId_factura(Integer id_factura) {
        this.id_factura = id_factura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_factura != null ? id_factura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.id_factura == null && other.id_factura != null) || (this.id_factura != null && !this.id_factura.equals(other.id_factura))) {
            return false;
        }
        return true;
    }

    /**
     * @return the codigo_control
     */
    public String getCodigo_control() {
        return codigo_control;
    }

    /**
     * @param codigo_control the codigo_control to set
     */
    public void setCodigo_control(String codigo_control) {
        this.codigo_control = codigo_control;
    }

    /**
     * @return the dosificacion
     */
    public Dosificacion getDosificacion() {
        return dosificacion;
    }

    /**
     * @param dosificacion the dosificacion to set
     */
    public void setDosificacion(Dosificacion dosificacion) {
        this.dosificacion = dosificacion;
    }

    /**
     * @return the numero_factura
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * @param numero the numero_factura to set
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
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
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    /**
     * @return the monto
     */
    public Double getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(Double monto) {
        this.monto = monto;
    }

    /**
     * @return the detalles
     */
    public List<Detalle> getDetalles() {
        return detalles;
    }

    /**
     * @param detalles the detalles to set
     */
    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }

    /**
     * @return the turnocaja
     */
    public TurnoCaja getTurnocaja() {
        return turnocaja;
    }

    /**
     * @param turnocaja the turnocaja to set
     */
    public void setTurnocaja(TurnoCaja turnocaja) {
        this.turnocaja = turnocaja;
    }

    public String getCondicionAbreviada() {
        return condicion.substring(0, 1);
    }

    public String getFecha_ddMMyyyHHmm() {
        return Reloj.formatearFecha_ddMMyyyyHHmm(fecha);
    }

    /**
     * @return the efectivo
     */
    public Double getEfectivo() {
        return efectivo;
    }

    /**
     * @param efectivo the efectivo to set
     */
    public void setEfectivo(Double efectivo) {
        this.efectivo = efectivo;
    }

    /**
     * @return the cambio
     */
    public Double getCambio() {
        return cambio;
    }

    /**
     * @param cambio the cambio to set
     */
    public void setCambio(Double cambio) {
        this.cambio = cambio;
    }

    /**
     * @return the valorada
     */
    public Boolean getValorada() {
        return valorada;
    }

    /**
     * @param valorada the valorada to set
     */
    public void setValorada(Boolean valorada) {
        this.valorada = valorada;
    }
}
