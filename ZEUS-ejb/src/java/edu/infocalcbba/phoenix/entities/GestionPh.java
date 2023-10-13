/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.phoenix.entities;

import edu.infocalcbba.util.Reloj;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
 * @author Tincho
 */
@Entity
@Table(name = "gestion", catalog = "infocaloruro", schema = "phoenix")
public class GestionPh implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_gestion;
    @Column(unique = true)
    private String codigo;
    @Temporal(TemporalType.DATE)
    private Date inicio;
    @Temporal(TemporalType.DATE)
    private Date fin;
    private String condicion;

    /**
     * @return the id_gestion
     */
    public Integer getId_gestion() {
        return id_gestion;
    }

    /**
     * @param id_gestion the id_gestion to set
     */
    public void setId_gestion(Integer id_gestion) {
        this.id_gestion = id_gestion;
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
     * @return the inicio
     */
    public Date getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the fin
     */
    public Date getFin() {
        return fin;
    }

    /**
     * @param fin the fin to set
     */
    public void setFin(Date fin) {
        this.fin = fin;
    }
    
    public String getInicio_ddMMyyyy() {
        return Reloj.formatearFecha_ddMMyyyy(inicio);
    }

    public String getFin_ddMMyyyy() {
        return Reloj.formatearFecha_ddMMyyyy(fin);
    }

    @Override
    public String toString() {
        return this.codigo + " [" + this.getInicio_ddMMyyyy() + " - " + this.getInicio_ddMMyyyy() + "]";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id_gestion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GestionPh other = (GestionPh) obj;
        if (!Objects.equals(this.id_gestion, other.id_gestion)) {
            return false;
        }
        return true;
    }

    /**
     * @return the condicion
     */
    public String getCondicion() {
        return condicion;
    }

    /**
     * @param condicion the condicion to set
     */
    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

}
