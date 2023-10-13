/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "pagoformacion", catalog = "infocaloruro", schema = "zeus")
public class PagoFormacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pagoformacion;

    private String codigo;
    private String concepto;
    private Integer monto;
    @Transient
    private Integer pago;

    public PagoFormacion() {
    }

    public PagoFormacion(Integer id_pago) {
        this.id_pagoformacion = id_pago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_pagoformacion != null ? id_pagoformacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoFormacion)) {
            return false;
        }
        PagoFormacion other = (PagoFormacion) object;
        if ((this.id_pagoformacion == null && other.id_pagoformacion != null) || (this.id_pagoformacion != null && !this.id_pagoformacion.equals(other.id_pagoformacion))) {
            return false;
        }
        return true;
    }

    /**
     * @return the id_pagoformacion
     */
    public Integer getId_pagoformacion() {
        return id_pagoformacion;
    }

    /**
     * @param id_pagoformacion the id_pagoformacion to set
     */
    public void setId_pagoformacion(Integer id_pagoformacion) {
        this.id_pagoformacion = id_pagoformacion;
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
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo.toUpperCase();
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
}
