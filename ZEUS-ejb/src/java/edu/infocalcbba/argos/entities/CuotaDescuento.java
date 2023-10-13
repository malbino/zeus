/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "cuotadescuento", catalog = "infocaloruro", schema = "argos")
public class CuotaDescuento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cuotadescuento;

    private String codigo;
    private String concepto;
    private Integer numero;
    private Integer porcentaje;

    @JoinColumn(name = "id_descuento")
    @ManyToOne
    private Descuento descuento;

    public CuotaDescuento() {

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id_cuotadescuento);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CuotaDescuento other = (CuotaDescuento) obj;
        if (!Objects.equals(this.id_cuotadescuento, other.id_cuotadescuento)) {
            return false;
        }
        return true;
    }

    /**
     * @return the id_cuotadescuento
     */
    public Integer getId_cuotadescuento() {
        return id_cuotadescuento;
    }

    /**
     * @param id_cuotadescuento the id_cuotadescuento to set
     */
    public void setId_cuotadescuento(Integer id_cuotadescuento) {
        this.id_cuotadescuento = id_cuotadescuento;
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
     * @return the porcentaje
     */
    public Integer getPorcentaje() {
        return porcentaje;
    }

    /**
     * @param porcentaje the porcentaje to set
     */
    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * @return the descuento
     */
    public Descuento getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }

}
