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
@Table(name = "institucion", catalog = "infocaloruro", schema = "argos")
public class Institucion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_institucion;

    @Column(unique = true)
    private Long nit;
    private String razon_social;
    private String actividad_economica;
    private String razon_social_dde;
    private String departamento;
    private String caracter;

    public Institucion() {
    }

    public Institucion(Integer id_institucion) {
        this.id_institucion = id_institucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId_institucion() != null ? getId_institucion().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Institucion)) {
            return false;
        }
        Institucion other = (Institucion) object;
        if ((this.getId_institucion() == null && other.getId_institucion() != null) || (this.getId_institucion() != null && !this.id_institucion.equals(other.id_institucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.razon_social + " [" + this.nit + "]";
    }

    /**
     * @return the id_institucion
     */
    public Integer getId_institucion() {
        return id_institucion;
    }

    /**
     * @param id_institucion the id_institucion to set
     */
    public void setId_institucion(Integer id_institucion) {
        this.id_institucion = id_institucion;
    }

    /**
     * @return the razon_social
     */
    public String getRazon_social() {
        return razon_social;
    }

    /**
     * @param razon_social the razon_social to set
     */
    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social.toUpperCase();
    }

    /**
     * @return the nit
     */
    public Long getNit() {
        return nit;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(Long nit) {
        this.nit = nit;
    }

    /**
     * @return the actividad_economica
     */
    public String getActividad_economica() {
        return actividad_economica;
    }

    /**
     * @param actividad_economica the actividad_economica to set
     */
    public void setActividad_economica(String actividad_economica) {
        this.actividad_economica = actividad_economica;
    }

    /**
     * @return the razon_social_dde
     */
    public String getRazon_social_dde() {
        return razon_social_dde;
    }

    /**
     * @param razon_social_dde the razon_social_dde to set
     */
    public void setRazon_social_dde(String razon_social_dde) {
        this.razon_social_dde = razon_social_dde;
    }

    /**
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the caracter
     */
    public String getCaracter() {
        return caracter;
    }

    /**
     * @param caracter the caracter to set
     */
    public void setCaracter(String caracter) {
        this.caracter = caracter;
    }
    
}
