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

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "bloque", catalog = "infocaloruro", schema = "argos")
public class Bloque implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_bloque;

    private String codigo;
    private String nombre;

    @JoinColumn(name = "id_campus")
    @ManyToOne
    private Campus campus;

    public Bloque() {
    }

    public Bloque(Integer id_bloque) {
        this.id_bloque = id_bloque;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_bloque != null ? id_bloque.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bloque)) {
            return false;
        }
        Bloque other = (Bloque) object;
        if ((this.id_bloque == null && other.id_bloque != null) || (this.id_bloque != null && !this.id_bloque.equals(other.id_bloque))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre + " [" + this.codigo + "]";
    }

    /**
     * @return the id_bloque
     */
    public Integer getId_bloque() {
        return id_bloque;
    }

    /**
     * @param id_bloque the id_bloque to set
     */
    public void setId_bloque(Integer id_bloque) {
        this.id_bloque = id_bloque;
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    /**
     * @return the campus
     */
    public Campus getCampus() {
        return campus;
    }

    /**
     * @param campus the campus to set
     */
    public void setCampus(Campus campus) {
        this.campus = campus;
    }
}
