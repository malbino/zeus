/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.entities;

import edu.infocalcbba.argos.entities.GestionAcademica;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "rol", catalog = "infocaloruro", schema = "public")
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_rol;

    @Column(unique = true)
    private String codigo;
    private String nombre;

    @JoinTable(name = "privilegio", catalog = "infocaloruro", schema = "public", joinColumns = {
        @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")}, inverseJoinColumns = {
        @JoinColumn(name = "id_funcionalidad", referencedColumnName = "id_funcionalidad")})
    @ManyToMany
    private List<Funcionalidad> funcionalidades;

    @JoinTable(name = "gestiona", catalog = "infocaloruro", schema = "argos", joinColumns = {
        @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")}, inverseJoinColumns = {
        @JoinColumn(name = "id_gestionacademica", referencedColumnName = "id_gestionacademica")})
    @ManyToMany
    private List<GestionAcademica> gestionesAcademicas;

    @ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;

    public Rol() {
    }

    public Rol(Integer idRol) {
        this.id_rol = idRol;
    }

    public Integer getId_rol() {
        return id_rol;
    }

    public void setId_rol(Integer id_rol) {
        this.id_rol = id_rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_rol != null ? id_rol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.id_rol == null && other.id_rol != null) || (this.id_rol != null && !this.id_rol.equals(other.id_rol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    /**
     * @return the funcionalidades
     */
    public List<Funcionalidad> getFuncionalidades() {
        return funcionalidades;
    }

    /**
     * @param funcionalidades the funcionalidades to set
     */
    public void setFuncionalidades(List<Funcionalidad> funcionalidades) {
        this.funcionalidades = funcionalidades;
    }

    /**
     * @return the usuarios
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
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
     * @return the gestionesAcademicas
     */
    public List<GestionAcademica> getGestionesAcademicas() {
        return gestionesAcademicas;
    }

    /**
     * @param gestionesAcademicas the gestionesAcademicas to set
     */
    public void setGestionesAcademicas(List<GestionAcademica> gestionesAcademicas) {
        this.gestionesAcademicas = gestionesAcademicas;
    }

}
