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
@Table(name = "regimen", catalog = "infocaloruro", schema = "argos")
public class Regimen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_regimen;

    @Column(unique = true)
    private String codigo;
    private String nombre;
    private Integer numero_cuotas;

    public Regimen() {
    }

    public Regimen(Integer id_regimen) {
        this.id_regimen = id_regimen;
    }

    public Integer getId_regimen() {
        return id_regimen;
    }

    public void setId_regimen(Integer id_regimen) {
        this.id_regimen = id_regimen;
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
        hash += (id_regimen != null ? id_regimen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Regimen)) {
            return false;
        }
        Regimen other = (Regimen) object;
        if ((this.id_regimen == null && other.id_regimen != null) || (this.id_regimen != null && !this.id_regimen.equals(other.id_regimen))) {
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
     * @return the cuotas
     */
    public Integer getNumero_cuotas() {
        return numero_cuotas;
    }

    /**
     * @param numero_cuotas the cuotas to set
     */
    public void setNumero_cuotas(Integer numero_cuotas) {
        this.numero_cuotas = numero_cuotas;
    }

}
