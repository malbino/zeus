/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "cliente", catalog = "infocaloruro", schema = "zeus")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cliente;

    private Long nit_ci;
    private String nombre_razonsocial;

    public Cliente() {
    }

    public Cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId_cliente() != null ? getId_cliente().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.getId_cliente() == null && other.getId_cliente() != null) || (this.getId_cliente() != null && !this.id_cliente.equals(other.id_cliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nit_ci + " [" + this.nombre_razonsocial + "]";
    }

    /**
     * @return the id_cliente
     */
    public Integer getId_cliente() {
        return id_cliente;
    }

    /**
     * @param id_cliente the id_cliente to set
     */
    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    /**
     * @return the razon_social
     */
    public String getNombre_razonsocial() {
        return nombre_razonsocial;
    }

    /**
     * @param nombre_razonsocial the razon_social to set
     */
    public void setNombre_razonsocial(String nombre_razonsocial) {
        this.nombre_razonsocial = nombre_razonsocial.toUpperCase();
    }

    /**
     * @return the nit
     */
    public Long getNit_ci() {
        return nit_ci;
    }

    /**
     * @param nit_ci the nit to set
     */
    public void setNit_ci(Long nit_ci) {
        this.nit_ci = nit_ci;
    }

}
