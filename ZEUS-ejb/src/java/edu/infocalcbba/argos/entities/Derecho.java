/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "derecho", catalog = "infocaloruro", schema = "argos")
public class Derecho implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_derecho;

    private String codigo;
    private String concepto;
    private Integer numero;
    private Integer monto;
    private Integer pagado;
    private Integer adeudado;
    private String condicion;

    @Transient
    private Integer pago;

    @JoinColumn(name = "id_defensagrado")
    @ManyToOne
    private DefensaGrado defensaGrado;

    public Derecho() {
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id_derecho);
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
        final Derecho other = (Derecho) obj;
        if (!Objects.equals(this.id_derecho, other.id_derecho)) {
            return false;
        }
        return true;
    }

    /**
     * @return the id_derecho
     */
    public Integer getId_derecho() {
        return id_derecho;
    }

    /**
     * @param id_derecho the id_derecho to set
     */
    public void setId_derecho(Integer id_derecho) {
        this.id_derecho = id_derecho;
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
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
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
     * @return the monto
     */
    public Integer getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    /**
     * @return the pagado
     */
    public Integer getPagado() {
        return pagado;
    }

    /**
     * @param pagado the pagado to set
     */
    public void setPagado(Integer pagado) {
        this.pagado = pagado;
    }

    /**
     * @return the adeudado
     */
    public Integer getAdeudado() {
        return adeudado;
    }

    /**
     * @param adeudado the adeudado to set
     */
    public void setAdeudado(Integer adeudado) {
        this.adeudado = adeudado;
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

    /**
     * @return the pago
     */
    public Integer getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(Integer pago) {
        this.pago = pago;
    }

    /**
     * @return the defensaGrado
     */
    public DefensaGrado getDefensaGrado() {
        return defensaGrado;
    }

    /**
     * @param defensaGrado the defensaGrado to set
     */
    public void setDefensaGrado(DefensaGrado defensaGrado) {
        this.defensaGrado = defensaGrado;
    }
    
    
    
}
