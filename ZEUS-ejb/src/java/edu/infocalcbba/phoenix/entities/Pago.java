/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.phoenix.entities;

import java.io.Serializable;
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
@Table(name = "pago", catalog = "infocaloruro", schema = "phoenix")
public class Pago implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pago;

    private String codigo;
    private String concepto;
    private Integer numero;
    private Integer monto;
    private Integer pagado;
    private Integer adeudado;
    private String condicion;

    @Transient
    private Integer monto_edicion;

    @Transient
    private Integer pago;

    @JoinColumn(name = "id_planpago")
    @ManyToOne
    private PlanPagoPh planpago;

    /**
     * @return the id_pago
     */
    public Integer getId_pago() {
        return id_pago;
    }

    /**
     * @param id_pago the id_pago to set
     */
    public void setId_pago(Integer id_pago) {
        this.id_pago = id_pago;
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
     * @return the monto_edicion
     */
    public Integer getMonto_edicion() {
        return monto_edicion;
    }

    /**
     * @param monto_edicion the monto_edicion to set
     */
    public void setMonto_edicion(Integer monto_edicion) {
        this.monto_edicion = monto_edicion;
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
     * @return the planpago
     */
    public PlanPagoPh getPlanpago() {
        return planpago;
    }

    /**
     * @param planpago the planpago to set
     */
    public void setPlanpago(PlanPagoPh planpago) {
        this.planpago = planpago;
    }

 
}
