/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.entities;

import edu.infocalcbba.argos.entities.Campus;
import java.io.Serializable;
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
@Table(name = "caja", catalog = "infocaloruro", schema = "zeus")
public class Caja implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_caja;

    private String codigo;
    private String nombre;

    @JoinColumn(name = "id_campus")
    @ManyToOne
    private Campus campus;

    public Caja() {
    }

    public Caja(Integer id_caja) {
        this.id_caja = id_caja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_caja != null ? id_caja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caja)) {
            return false;
        }
        Caja other = (Caja) object;
        if ((this.id_caja == null && other.id_caja != null) || (this.id_caja != null && !this.id_caja.equals(other.id_caja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre + " [" + this.codigo + "]";
    }

    /**
     * @return the id_caja
     */
    public Integer getId_caja() {
        return id_caja;
    }

    /**
     * @param id_caja the id_caja to set
     */
    public void setId_caja(Integer id_caja) {
        this.id_caja = id_caja;
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

}
