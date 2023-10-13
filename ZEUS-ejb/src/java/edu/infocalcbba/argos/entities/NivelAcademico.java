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
@Table(name = "nivelacademico", catalog = "infocaloruro", schema = "argos")
public class NivelAcademico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_nivelacademico;

    @Column(unique = true)
    private String codigo;
    private String nombre;

    public NivelAcademico() {
    }

    public NivelAcademico(Integer idNivelAcademico) {
        this.id_nivelacademico = idNivelAcademico;
    }

    public Integer getId_nivelacademico() {
        return id_nivelacademico;
    }

    public void setId_nivelacademico(Integer id_nivelacademico) {
        this.id_nivelacademico = id_nivelacademico;
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
        hash += (id_nivelacademico != null ? id_nivelacademico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NivelAcademico)) {
            return false;
        }
        NivelAcademico other = (NivelAcademico) object;
        if ((this.id_nivelacademico == null && other.id_nivelacademico != null) || (this.id_nivelacademico != null && !this.id_nivelacademico.equals(other.id_nivelacademico))) {
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
}
