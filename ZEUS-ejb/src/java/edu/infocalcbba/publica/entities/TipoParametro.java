/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.entities;

import java.io.Serializable;
import javax.persistence.Column;
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
@Table(name = "tipoparametro", catalog = "infocaloruro", schema = "public")
public class TipoParametro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tipoparametro;

    @Column(unique = true)
    private String codigo;
    private String nombre;

    public TipoParametro() {
    }

    public TipoParametro(Integer id_tipoparametro) {
        this.id_tipoparametro = id_tipoparametro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId_tipoparametro() != null ? getId_tipoparametro().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoParametro)) {
            return false;
        }
        TipoParametro other = (TipoParametro) object;
        if ((this.getId_tipoparametro() == null && other.getId_tipoparametro() != null) || (this.getId_tipoparametro() != null && !this.id_tipoparametro.equals(other.id_tipoparametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getNombre() + " [" + this.codigo + "]";
    }

    /**
     * @return the id_tipoparametro
     */
    public Integer getId_tipoparametro() {
        return id_tipoparametro;
    }

    /**
     * @param id_tipoparametro the id_tipoparametro to set
     */
    public void setId_tipoparametro(Integer id_tipoparametro) {
        this.id_tipoparametro = id_tipoparametro;
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
