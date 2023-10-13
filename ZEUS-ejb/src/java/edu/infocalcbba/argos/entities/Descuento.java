/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "descuento", catalog = "infocaloruro", schema = "argos")
public class Descuento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_descuento;

    @Column(unique = true)
    private String codigo;
    private String nombre;
    private Integer promedio;

    @JoinColumn(name = "id_regimen")
    @ManyToOne
    private Regimen regimen;
    
    @OneToMany(mappedBy = "descuento", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @OrderBy("numero")
    private List<CuotaDescuento> cuotasDescuento;

    public Descuento() {

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id_descuento);
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
        final Descuento other = (Descuento) obj;
        if (!Objects.equals(this.id_descuento, other.id_descuento)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre + " [PROMEDIO >= " + promedio + "]";
    }

    
    /**
     * @return the id_descuento
     */
    public Integer getId_descuento() {
        return id_descuento;
    }

    /**
     * @param id_descuento the id_descuento to set
     */
    public void setId_descuento(Integer id_descuento) {
        this.id_descuento = id_descuento;
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    /**
     * @return the promedio
     */
    public Integer getPromedio() {
        return promedio;
    }

    /**
     * @param promedio the promedio to set
     */
    public void setPromedio(Integer promedio) {
        this.promedio = promedio;
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
     * @return the cuotasDescuento
     */
    public List<CuotaDescuento> getCuotasDescuento() {
        return cuotasDescuento;
    }

    /**
     * @param cuotasDescuento the cuotasDescuento to set
     */
    public void setCuotasDescuento(List<CuotaDescuento> cuotasDescuento) {
        this.cuotasDescuento = cuotasDescuento;
    }

   
}
