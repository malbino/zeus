/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.entities;

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
@Table(name = "parametro", catalog = "infocaloruro", schema = "public")
public class Parametro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_parametro;

    private String nombre;
    private String valor;
    private String regex;

    @JoinColumn(name = "id_tipoparametro")
    @ManyToOne
    private TipoParametro tipoparametro;

    public Parametro() {
    }

    public Parametro(Integer id_variable) {
        this.id_parametro = id_variable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_parametro != null ? id_parametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametro)) {
            return false;
        }
        Parametro other = (Parametro) object;
        if ((this.id_parametro == null && other.id_parametro != null) || (this.id_parametro != null && !this.id_parametro.equals(other.id_parametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    /**
     * @return the id_variable
     */
    public Integer getId_parametro() {
        return id_parametro;
    }

    /**
     * @param id_parametro the id_variable to set
     */
    public void setId_parametro(Integer id_parametro) {
        this.id_parametro = id_parametro;
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

    /**
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * @return the regex
     */
    public String getRegex() {
        return regex;
    }

    /**
     * @param regex the regex to set
     */
    public void setRegex(String regex) {
        this.regex = regex;
    }

    /**
     * @return the tipoparametro
     */
    public TipoParametro getTipoparametro() {
        return tipoparametro;
    }

    /**
     * @param tipoparametro the tipoparametro to set
     */
    public void setTipoparametro(TipoParametro tipoparametro) {
        this.tipoparametro = tipoparametro;
    }
}
