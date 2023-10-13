/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.entities;

import edu.infocalcbba.argos.entities.Campus;
import edu.infocalcbba.util.Reloj;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "dosificacion", catalog = "infocaloruro", schema = "zeus")
public class Dosificacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_dosificacion;

    private Long numero_tramite;
    @Column(unique = true)
    private Long numero_autorizacion;
    @Temporal(TemporalType.DATE)
    private Date flimite_emision;
    private String llave_dosificacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date factivacion;
    private String leyenda;

    @JoinColumn(name = "id_campus")
    @ManyToOne
    private Campus campus;

    public Dosificacion() {
    }

    public Dosificacion(Integer idRol) {
        this.id_dosificacion = idRol;
    }

    public Integer getId_dosificacion() {
        return id_dosificacion;
    }

    public void setId_dosificacion(Integer id_dosificacion) {
        this.id_dosificacion = id_dosificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_dosificacion != null ? id_dosificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dosificacion)) {
            return false;
        }
        Dosificacion other = (Dosificacion) object;
        if ((this.id_dosificacion == null && other.id_dosificacion != null) || (this.id_dosificacion != null && !this.id_dosificacion.equals(other.id_dosificacion))) {
            return false;
        }
        return true;
    }

    /**
     * @return the llave_dosificacion
     */
    public String getLlave_dosificacion() {
        return llave_dosificacion;
    }

    /**
     * @param llave_dosificacion the llave_dosificacion to set
     */
    public void setLlave_dosificacion(String llave_dosificacion) {
        this.llave_dosificacion = llave_dosificacion;
    }

    /**
     * @return the campus
     */
    public Campus getCampus() {
        return campus;
    }

    /**
     * @param campus the campus to set
     */
    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    /**
     * @return the flimite_emision
     */
    public Date getFlimite_emision() {
        return flimite_emision;
    }

    /**
     * @param flimite_emision the flimite_emision to set
     */
    public void setFlimite_emision(Date flimite_emision) {
        this.flimite_emision = flimite_emision;
    }

    /**
     * @return the numero_autorizacion
     */
    public Long getNumero_autorizacion() {
        return numero_autorizacion;
    }

    /**
     * @param numero_autorizacion the numero_autorizacion to set
     */
    public void setNumero_autorizacion(Long numero_autorizacion) {
        this.numero_autorizacion = numero_autorizacion;
    }

    /**
     * @return the factivacion
     */
    public Date getFactivacion() {
        return factivacion;
    }

    /**
     * @param factivacion the factivacion to set
     */
    public void setFactivacion(Date factivacion) {
        this.factivacion = factivacion;
    }

    public String getFlimite_emision_ddMMyyyy() {
        return Reloj.formatearFecha_ddMMyyyy(flimite_emision);
    }

    /**
     * @return the leyenda
     */
    public String getLeyenda() {
        return leyenda;
    }

    /**
     * @param leyenda the leyenda to set
     */
    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }

    /**
     * @return the numero_tramite
     */
    public Long getNumero_tramite() {
        return numero_tramite;
    }

    /**
     * @param numero_tramite the numero_tramite to set
     */
    public void setNumero_tramite(Long numero_tramite) {
        this.numero_tramite = numero_tramite;
    }

}
