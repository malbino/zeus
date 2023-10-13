/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import edu.infocalcbba.util.Reloj;
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
@Table(name = "calendario", catalog = "infocaloruro", schema = "argos")
public class Calendario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_calendario;

    @Column(unique = true)
    private String codigo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fin;

    public Calendario() {
    }

    public Calendario(Integer id_gestionacademica) {
        this.id_calendario = id_gestionacademica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_calendario != null ? id_calendario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calendario)) {
            return false;
        }
        Calendario other = (Calendario) object;
        if ((this.id_calendario == null && other.id_calendario != null) || (this.id_calendario != null && !this.id_calendario.equals(other.id_calendario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getCodigo();
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

    public String getInicio_ddMMyyyyHHmm() {
        return Reloj.formatearFecha_ddMMyyyyHHmm(inicio);
    }

    public String getFin_ddMMyyyyHHmm() {
        return Reloj.formatearFecha_ddMMyyyyHHmm(fin);
    }

    /**
     * @return the id_gestionacademica
     */
    public Integer getId_calendario() {
        return id_calendario;
    }

    /**
     * @param id_calendario the id_gestionacademica to set
     */
    public void setId_calendario(Integer id_calendario) {
        this.id_calendario = id_calendario;
    }

}
