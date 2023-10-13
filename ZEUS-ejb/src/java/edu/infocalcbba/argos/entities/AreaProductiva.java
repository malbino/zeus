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
@Table(name = "areaproductiva", catalog = "infocaloruro", schema = "argos")
public class AreaProductiva implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_areaproductiva;

    @Column(unique = true)
    private String codigo;
    private String nombre;

    public AreaProductiva() {
    }

    public AreaProductiva(Integer id_areaproductiva) {
        this.id_areaproductiva = id_areaproductiva;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_areaproductiva != null ? id_areaproductiva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AreaProductiva)) {
            return false;
        }
        AreaProductiva other = (AreaProductiva) object;
        if ((this.id_areaproductiva == null && other.id_areaproductiva != null) || (this.id_areaproductiva != null && !this.id_areaproductiva.equals(other.id_areaproductiva))) {
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
     * @return the id_areaproductiva
     */
    public Integer getId_areaproductiva() {
        return id_areaproductiva;
    }

    /**
     * @param id_areaproductiva the id_areaproductiva to set
     */
    public void setId_areaproductiva(Integer id_areaproductiva) {
        this.id_areaproductiva = id_areaproductiva;
    }

}
