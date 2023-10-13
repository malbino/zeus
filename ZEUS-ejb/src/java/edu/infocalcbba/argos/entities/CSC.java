/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

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
@Table(name = "csc", catalog = "infocaloruro", schema = "argos")
public class CSC implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_csc;

    @Column(unique = true)
    private String codigo;
    private String nombre;

    public CSC() {
    }

    public CSC(Integer id_csc) {
        this.id_csc = id_csc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_csc != null ? id_csc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CSC)) {
            return false;
        }
        CSC other = (CSC) object;
        if ((this.id_csc == null && other.id_csc != null) || (this.id_csc != null && !this.id_csc.equals(other.id_csc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre + " [" + this.codigo + "]";
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
     * @return the id_csc
     */
    public Integer getId_csc() {
        return id_csc;
    }

    /**
     * @param id_csc the id_csc to set
     */
    public void setId_csc(Integer id_csc) {
        this.id_csc = id_csc;
    }

}
