/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "funcionalidad", catalog = "infocaloruro", schema = "public")
public class Funcionalidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_funcionalidad;

    private String nombre;
    private String tipo;

    @ManyToMany(mappedBy = "funcionalidades")
    private List<Rol> roles;
    
    @JoinColumn(name = "id_sistema")
    @ManyToOne
    private Sistema sistema;
    
    @OneToMany(mappedBy = "funcionalidad")
    private List<Pagina> paginas;

    public Funcionalidad() {
    }

    public Funcionalidad(Integer idFuncionalidad) {
        this.id_funcionalidad = idFuncionalidad;
    }

    public Integer getId_funcionalidad() {
        return id_funcionalidad;
    }

    public void setId_funcionalidad(Integer id_funcionalidad) {
        this.id_funcionalidad = id_funcionalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_funcionalidad != null ? id_funcionalidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Funcionalidad)) {
            return false;
        }
        Funcionalidad other = (Funcionalidad) object;
        if ((this.id_funcionalidad == null && other.id_funcionalidad != null) || (this.id_funcionalidad != null && !this.id_funcionalidad.equals(other.id_funcionalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre + " [" + sistema.getNombre() + "]";
    }

    /**
     * @return the roles
     */
    public List<Rol> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    /**
     * @return the sistema
     */
    public Sistema getSistema() {
        return sistema;
    }

    /**
     * @param sistema the sistema to set
     */
    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the paginas
     */
    public List<Pagina> getPaginas() {
        return paginas;
    }

    /**
     * @param paginas the paginas to set
     */
    public void setPaginas(List<Pagina> paginas) {
        this.paginas = paginas;
    }

}
