/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "colegio", catalog = "infocaloruro", schema = "argos")
public class Colegio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_colegio;

    private String distrito;
    @Column(unique = true)
    private String codigo;
    private String nombre;
    private String educacion;
    private String dependencia;
    private String resolucion;
    @Temporal(TemporalType.DATE)
    private Date fecha_resolucion;

    public Colegio() {
    }

    public Colegio(Integer id_colegio) {
        this.id_colegio = id_colegio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_colegio != null ? id_colegio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Colegio)) {
            return false;
        }
        Colegio other = (Colegio) object;
        if ((this.id_colegio == null && other.id_colegio != null) || (this.id_colegio != null && !this.id_colegio.equals(other.id_colegio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String s;
        if (this.resolucion == null) {
            s = this.nombre + " [" + this.distrito + ", " + this.educacion + ", " + this.dependencia + ", Sin resolución]";
        } else {
            s = this.nombre + " [" + this.distrito + ", " + this.educacion + ", " + this.dependencia + ", " + this.resolucion + "]";
        }
        return s;
    }

    /**
     * @return the id_colegio
     */
    public Integer getId_colegio() {
        return id_colegio;
    }

    /**
     * @param id_colegio the id_colegio to set
     */
    public void setId_colegio(Integer id_colegio) {
        this.id_colegio = id_colegio;
    }

    /**
     * @return the distrito
     */
    public String getDistrito() {
        return distrito;
    }

    /**
     * @param distrito the distrito to set
     */
    public void setDistrito(String distrito) {
        this.distrito = distrito;
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

    /**
     * @return the educacion
     */
    public String getEducacion() {
        return educacion;
    }

    /**
     * @param educacion the educacion to set
     */
    public void setEducacion(String educacion) {
        this.educacion = educacion;
    }

    /**
     * @return the dependencia
     */
    public String getDependencia() {
        return dependencia;
    }

    /**
     * @param dependencia the dependencia to set
     */
    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    /**
     * @return the resolucion
     */
    public String getResolucion() {
        return resolucion;
    }

    /**
     * @param resolucion the resolucion to set
     */
    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    /**
     * @return the fecha_resolucion
     */
    public Date getFecha_resolucion() {
        return fecha_resolucion;
    }

    /**
     * @param fecha_resolucion the fecha_resolucion to set
     */
    public void setFecha_resolucion(Date fecha_resolucion) {
        this.fecha_resolucion = fecha_resolucion;
    }

}
