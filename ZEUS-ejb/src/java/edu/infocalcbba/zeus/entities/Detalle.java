/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.entities;

import edu.infocalcbba.argos.entities.Carrera;
import edu.infocalcbba.argos.entities.Cuota;
import edu.infocalcbba.argos.entities.Derecho;
import edu.infocalcbba.argos.entities.Estudiante;
import edu.infocalcbba.argos.entities.Nota;
import edu.infocalcbba.phoenix.entities.Pago;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "detalle", catalog = "infocaloruro", schema = "zeus")
public class Detalle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_detalle;

    private Integer cantidad;
    private String codigo;
    private String concepto;
    private Double unitario;
    private Double total;

    @JoinColumn(name = "id_factura")
    @ManyToOne
    private Factura factura;

    @JoinColumn(name = "id_pago")
    @ManyToOne
    private Pago pago;

    @JoinColumn(name = "id_cuota")
    @ManyToOne
    private Cuota cuota;

    @JoinColumn(name = "id_persona")
    @ManyToOne
    private Estudiante estudiante;

    @JoinColumn(name = "id_carrera")
    @ManyToOne
    private Carrera carrera;

    @JoinColumn(name = "id_derecho")
    @ManyToOne
    private Derecho derecho;

    @JoinColumn(name = "id_nota")
    @ManyToOne
    private Nota nota;

    public Detalle() {
    }

    public Detalle(Integer id_detalle) {
        this.id_detalle = id_detalle;
    }

    /**
     * @return the id_detalle
     */
    public Integer getId_detalle() {
        return id_detalle;
    }

    /**
     * @param id_detalle the id_detalle to set
     */
    public void setId_detalle(Integer id_detalle) {
        this.id_detalle = id_detalle;
    }

    /**
     * @return the cantidad
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the factura
     */
    public Factura getFactura() {
        return factura;
    }

    /**
     * @param factura the factura to set
     */
    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    /**
     * @return the unitario
     */
    public Double getUnitario() {
        return unitario;
    }

    /**
     * @param unitario the unitario to set
     */
    public void setUnitario(Double unitario) {
        this.unitario = unitario;
    }

    /**
     * @return the total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Double total) {
        this.total = total;
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
     * @return the cuota
     */
    public Cuota getCuota() {
        return cuota;
    }

    /**
     * @param cuota the cuota to set
     */
    public void setCuota(Cuota cuota) {
        this.cuota = cuota;
    }

    /**
     * @return the estudiante
     */
    public Estudiante getEstudiante() {
        return estudiante;
    }

    /**
     * @param estudiante the estudiante to set
     */
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    /**
     * @return the carrera
     */
    public Carrera getCarrera() {
        return carrera;
    }

    /**
     * @param carrera the carrera to set
     */
    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id_detalle);
        hash = 97 * hash + Objects.hashCode(this.codigo);
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
        final Detalle other = (Detalle) obj;
        if (!Objects.equals(this.id_detalle, other.id_detalle)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    /**
     * @return the pago
     */
    public Pago getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(Pago pago) {
        this.pago = pago;
    }

    /**
     * @return the derecho
     */
    public Derecho getDerecho() {
        return derecho;
    }

    /**
     * @param derecho the derecho to set
     */
    public void setDerecho(Derecho derecho) {
        this.derecho = derecho;
    }

    /**
     * @return the nota
     */
    public Nota getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(Nota nota) {
        this.nota = nota;
    }

}
