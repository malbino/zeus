/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.entities;

import edu.infocalcbba.util.Reloj;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "persona", catalog = "infocaloruro", schema = "public")

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo")
@DiscriminatorValue("Persona")
public class Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_persona;

    private String nombre;
    private String papellido;
    private String sapellido;
    @Column(unique = true)
    private String dni;
    private String lugar;
    @Temporal(TemporalType.DATE)
    private Date fnacimiento;
    private String sexo;
    private String direccion;
    private Integer telefono;
    private Integer celular;
    private String email;
    private String foto;

    @JoinColumn(name = "id_pais")
    @ManyToOne
    private Pais pais;

    public Persona() {
    }

    public Persona(Integer idPersona) {
        this.id_persona = idPersona;
    }

    public Persona(Integer idPersona, String nombre, String papellido, String dni, Date fnacimiento, String sexo, String direccion, String email) {
        this.id_persona = idPersona;
        this.nombre = nombre;
        this.papellido = papellido;
        this.dni = dni;
        this.fnacimiento = fnacimiento;
        this.sexo = sexo;
        this.direccion = direccion;
        this.email = email;
    }

    public Integer getId_persona() {
        return id_persona;
    }

    public void setId_persona(Integer id_persona) {
        this.id_persona = id_persona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }

    public String getPapellido() {
        return papellido;
    }

    public void setPapellido(String papellido) {
        this.papellido = papellido.toUpperCase();
    }

    public String getSapellido() {
        return sapellido;
    }

    public void setSapellido(String sapellido) {
        this.sapellido = sapellido.toUpperCase();
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFnacimiento() {
        return fnacimiento;
    }

    public void setFnacimiento(Date fnacimiento) {
        this.fnacimiento = fnacimiento;
    }

    public String getFnacimiento_ddMMyyyy() {
        return Reloj.formatearFecha_ddMMyyyy(fnacimiento);
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion.toUpperCase();
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public Integer getCelular() {
        return celular;
    }

    public void setCelular(Integer celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_persona != null ? id_persona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.id_persona == null && other.id_persona != null) || (this.id_persona != null && !this.id_persona.equals(other.id_persona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String s = this.getPapellido();
        if (this.getSapellido() != null && !this.getSapellido().isEmpty()) {
            s += " " + this.getSapellido();
        }
        s += " " + this.nombre;
        return s;
    }

    /**
     * @return the lugar
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * @param lugar the lugar to set
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * @return the pais
     */
    public Pais getPais() {
        return pais;
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public boolean emailIsEmpty() {
        boolean res = true;

        if (email != null) {
            res = email.isEmpty();
        }
        return res;
    }
    
    public String iniciales() {
        String s = this.getPapellido().substring(0, 1);
        if (this.getSapellido() != null && !this.getSapellido().isEmpty()) {
            s += this.getSapellido().substring(0, 1);
        }
        s += this.nombre.substring(0, 1);
        return s;
    }

    /**
     * @return the foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }
}
