/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import edu.infocalcbba.util.Reloj;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
@Table(name = "planpago", catalog = "infocaloruro", schema = "argos")
public class PlanPago implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_planpago;

    private String codigo;
    private String concepto;
    private Integer numero;
    @Temporal(TemporalType.TIMESTAMP)
    private Date vencimiento; 

    @JoinColumn(name = "id_regimen")
    @ManyToOne
    private Regimen regimen;

    @JoinColumn(name = "id_gestionacademica")
    @ManyToOne
    private GestionAcademica gestionacademica;


    public PlanPago() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id_planpago);
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
        final PlanPago other = (PlanPago) obj;
        if (!Objects.equals(this.id_planpago, other.id_planpago)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.concepto + "[ " + this.getVencimiento_ddMMyyyy() + "]";
    }
    
    

    /**
     * @return the id_planpago
     */
    public Integer getId_planpago() {
        return id_planpago;
    }

    /**
     * @param id_planpago the id_planpago to set
     */
    public void setId_planpago(Integer id_planpago) {
        this.id_planpago = id_planpago;
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
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto.toUpperCase();
    }

    /**
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * @return the vencimiento
     */
    public Date getVencimiento() {
        return vencimiento;
    }

    /**
     * @param vencimiento the vencimiento to set
     */
    public void setVencimiento(Date vencimiento) {
        this.vencimiento = vencimiento;
    }

    /**
     * @return the regimen
     */
    public Regimen getRegimen() {
        return regimen;
    }

    /**
     * @param regimen the regimen to set
     */
    public void setRegimen(Regimen regimen) {
        this.regimen = regimen;
    }

    /**
     * @return the gestionacademica
     */
    public GestionAcademica getGestionacademica() {
        return gestionacademica;
    }

    /**
     * @param gestionacademica the gestionacademica to set
     */
    public void setGestionacademica(GestionAcademica gestionacademica) {
        this.gestionacademica = gestionacademica;
    }
    
    public String getVencimiento_ddMMyyyy() {
        return Reloj.formatearFecha_ddMMyyyy(vencimiento);
    }
 
}
