/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "pais", catalog = "infocaloruro", schema = "public")
public class Pais implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pais;

    @Column(unique = true)
    private String codigo;
    private String nombre;

    public Pais() {
    }

    public Pais(Integer id_campus) {
        this.id_pais = id_campus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId_pais() != null ? getId_pais().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.getId_pais() == null && other.getId_pais() != null) || (this.getId_pais() != null && !this.id_pais.equals(other.id_pais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getNombre() + " [" + this.codigo + "]";
    }

    /**
     * @return the id_campus
     */
    public Integer getId_pais() {
        return id_pais;
    }

    /**
     * @param id_pais the id_campus to set
     */
    public void setId_pais(Integer id_pais) {
        this.id_pais = id_pais;
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
