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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "planpago", catalog = "infocaloruro", schema = "phoenix")
public class PlanPagoPh implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_planpago;

    private Integer monto;
    private Integer pagado;
    private Integer adeudado;
    private String condicion;

    @OneToOne
    @JoinColumn(name = "id_inscrito")
    private InscritoPh inscrito;

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
     * @return the inscrito
     */
    public InscritoPh getInscrito() {
        return inscrito;
    }

    /**
     * @param inscrito the inscrito to set
     */
    public void setInscrito(InscritoPh inscrito) {
        this.inscrito = inscrito;
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
