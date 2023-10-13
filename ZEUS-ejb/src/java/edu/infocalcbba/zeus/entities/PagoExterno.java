/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "pagoexterno", catalog = "infocaloruro", schema = "zeus")
public class PagoExterno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pagoexterno;

    @Column(unique = true)
    private String codigo;
    private String concepto;
    private Double monto;

    public PagoExterno() {
    }

    public PagoExterno(Integer id_pago) {
        this.id_pagoexterno = id_pago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_pagoexterno != null ? id_pagoexterno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoExterno)) {
            return false;
        }
        PagoExterno other = (PagoExterno) object;
        if ((this.id_pagoexterno == null && other.id_pagoexterno != null) || (this.id_pagoexterno != null && !this.id_pagoexterno.equals(other.id_pagoexterno))) {
            return false;
        }
        return true;
    }

    /**
     * @return the id_pagoexterno
     */
    public Integer getId_pagoexterno() {
        return id_pagoexterno;
    }

    /**
     * @param id_pagoexterno the id_pagoexterno to set
     */
    public void setId_pagoexterno(Integer id_pagoexterno) {
        this.id_pagoexterno = id_pagoexterno;
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
}
