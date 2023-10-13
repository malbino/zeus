/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.entities;

import edu.infocalcbba.kardia.entities.Empleado;
import edu.infocalcbba.util.Reloj;
import java.io.Serializable;
import java.util.Date;
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
@Table(name = "turnocaja", catalog = "infocaloruro", schema = "zeus")
public class TurnoCaja implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_turnocaja;

    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fin;

    private String condicion;

    @JoinColumn(name = "id_caja")
    @ManyToOne
    private Caja caja;

    @JoinColumn(name = "id_persona")
    @ManyToOne
    private Empleado empleado;

    public TurnoCaja() {
    }

    public TurnoCaja(Integer id_turnocaja) {
        this.id_turnocaja = id_turnocaja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_turnocaja != null ? id_turnocaja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TurnoCaja)) {
            return false;
        }
        TurnoCaja other = (TurnoCaja) object;
        if ((this.id_turnocaja == null && other.id_turnocaja != null) || (this.id_turnocaja != null && !this.id_turnocaja.equals(other.id_turnocaja))) {
            return false;
        }
        return true;
    }

    /**
     * @return the id_turnocaja
     */
    public Integer getId_turnocaja() {
        return id_turnocaja;
    }

    /**
     * @param id_turnocaja the id_turnocaja to set
     */
    public void setId_turnocaja(Integer id_turnocaja) {
        this.id_turnocaja = id_turnocaja;
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

    /**
     * @return the caja
     */
    public Caja getCaja() {
        return caja;
    }

    /**
     * @param caja the caja to set
     */
    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    /**
     * @return the empleado
     */
    public Empleado getEmpleado() {
        return empleado;
    }

    /**
     * @param empleado the empleado to set
     */
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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

    public String getInicio_ddMMyyyHHmm() {
        return Reloj.formatearFecha_ddMMyyyyHHmm(inicio);
    }
}
