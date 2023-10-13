/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "usuario", catalog = "infocaloruro", schema = "public")

@PrimaryKeyJoinColumn(name = "id_persona")
@DiscriminatorValue("Usuario")
public class Usuario extends Persona implements Serializable {

    @Column(unique = true)
    private String usuario;
    private String contrasena;

    @Transient
    private String contrasenaSinEncriptar;

    @JoinTable(name = "cuenta", catalog = "infocaloruro", schema = "public", joinColumns = {
        @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")}, inverseJoinColumns = {
        @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")})
    @ManyToMany
    private List<Rol> roles;

    public Usuario() {
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the codigosis
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the codigosis to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
     * @return the contrasenaSinEncriptar
     */
    public String getContrasenaSinEncriptar() {
        return contrasenaSinEncriptar;
    }

    /**
     * @param contrasenaSinEncriptar the contrasenaSinEncriptar to set
     */
    public void setContrasenaSinEncriptar(String contrasenaSinEncriptar) {
        this.contrasenaSinEncriptar = contrasenaSinEncriptar;
    }
}
