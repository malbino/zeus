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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "aula", catalog = "infocaloruro", schema = "argos")
public class Aula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_aula;

    @Column(unique = true)
    private String codigo;
    private Integer capacidad;
    private String nombre;

    @JoinColumn(name = "id_bloque")
    @ManyToOne
    private Bloque bloque;

    public Aula() {
    }

    public Aula(Integer id_aula) {
        this.id_aula = id_aula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_aula != null ? id_aula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aula)) {
            return false;
        }
        Aula other = (Aula) object;
        if ((this.id_aula == null && other.id_aula != null) || (this.id_aula != null && !this.id_aula.equals(other.id_aula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getCodigo() + " [CAP. " + this.getCapacidad() + ", " + this.bloque.getNombre() + ", " + this.bloque.getCampus().getNombre() + "]";
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
     * @return the capacidad
     */
    public Integer getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * @return the id_aula
     */
    public Integer getId_aula() {
        return id_aula;
    }

    /**
     * @param id_aula the id_aula to set
     */
    public void setId_aula(Integer id_aula) {
        this.id_aula = id_aula;
    }

    /**
     * @return the bloque
     */
    public Bloque getBloque() {
        return bloque;
    }

    /**
     * @param bloque the bloque to set
     */
    public void setBloque(Bloque bloque) {
        this.bloque = bloque;
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

}
