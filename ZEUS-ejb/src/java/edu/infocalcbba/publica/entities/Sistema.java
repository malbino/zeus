/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.entities;

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
@Table(name = "sistema", catalog = "infocaloruro", schema = "public")
public class Sistema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_sistema;

    @Column(unique = true)
    private String codigo;
    private String nombre;

    public Sistema() {
    }

    public Sistema(Integer id_sistema) {
        this.id_sistema = id_sistema;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId_sistema() != null ? getId_sistema().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sistema)) {
            return false;
        }
        Sistema other = (Sistema) object;
        if ((this.getId_sistema() == null && other.getId_sistema() != null) || (this.getId_sistema() != null && !this.id_sistema.equals(other.id_sistema))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre + " [" + this.codigo + "]";
    }

    /**
     * @return the id_sistema
     */
    public Integer getId_bloque() {
        return getId_sistema();
    }

    /**
     * @param id_sistema the id_sistema to set
     */
    public void setId_bloque(Integer id_sistema) {
        this.setId_sistema(id_sistema);
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
     * @return the id_sistema
     */
    public Integer getId_sistema() {
        return id_sistema;
    }

    /**
     * @param id_sistema the id_sistema to set
     */
    public void setId_sistema(Integer id_sistema) {
        this.id_sistema = id_sistema;
    }
}
