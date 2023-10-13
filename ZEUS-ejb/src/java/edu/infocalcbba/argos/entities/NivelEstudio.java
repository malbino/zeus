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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "nivelestudio", catalog = "infocaloruro", schema = "argos", uniqueConstraints = @UniqueConstraint(columnNames = {"nivel","id_regimen"}))
public class NivelEstudio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_nivelestudio;
    
    private Integer nivel;
    private String codigo;
    private String nombre;

    @JoinColumn(name = "id_regimen")
    @ManyToOne
    private Regimen regimen;

    public NivelEstudio() {
    }

    public NivelEstudio(Integer idNivelEstudio) {
        this.id_nivelestudio = idNivelEstudio;
    }

    public Integer getId_nivelestudio() {
        return id_nivelestudio;
    }

    public void setId_nivelestudio(Integer id_nivelestudio) {
        this.id_nivelestudio = id_nivelestudio;
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
        hash += (id_nivelestudio != null ? id_nivelestudio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NivelEstudio)) {
            return false;
        }
        NivelEstudio other = (NivelEstudio) object;
        if ((this.id_nivelestudio == null && other.id_nivelestudio != null) || (this.id_nivelestudio != null && !this.id_nivelestudio.equals(other.id_nivelestudio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    /**
     * @return the idRegimen
     */
    public Regimen getRegimen() {
        return regimen;
    }

    /**
     * @param regimen the idRegimen to set
     */
    public void setRegimen(Regimen regimen) {
        this.regimen = regimen;
    }

    /**
     * @return the nivel
     */
    public Integer getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(Integer nivel) {
        this.nivel = nivel;
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
