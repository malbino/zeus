/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "cuota", catalog = "infocaloruro", schema = "argos")
public class Cuota implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cuota;

    private String codigo;
    private String concepto;
    private Integer numero;
    private Integer monto;
    private Integer pagado;
    private Integer adeudado;
    private String condicion;

    @Transient
    private Integer pago;
    @Transient
    private Integer monto_sindescuento;

    @JoinColumn(name = "id_inscrito")
    @ManyToOne
    private Inscrito inscrito;

    public Cuota() {
    }

    public Cuota(Integer id_cuota) {
        this.id_cuota = id_cuota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId_cuota() != null ? getId_cuota().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuota)) {
            return false;
        }
        Cuota other = (Cuota) object;
        if ((this.getId_cuota() == null && other.getId_cuota() != null) || (this.getId_cuota() != null && !this.id_cuota.equals(other.id_cuota))) {
            return false;
        }
        return true;
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
     * @return the pago
     */
    public Integer getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(Integer pago) {
        this.pago = pago;
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
     * @return the inscrito
     */
    public Inscrito getInscrito() {
        return inscrito;
    }

    /**
     * @param inscrito the inscrito to set
     */
    public void setInscrito(Inscrito inscrito) {
        this.inscrito = inscrito;
    }

    /**
     * @return the monto_sindescuento
     */
    public Integer getMonto_sindescuento() {
        return monto_sindescuento;
    }

    /**
     * @param monto_sindescuento the monto_sindescuento to set
     */
    public void setMonto_sindescuento(Integer monto_sindescuento) {
        this.monto_sindescuento = monto_sindescuento;
    }
}
