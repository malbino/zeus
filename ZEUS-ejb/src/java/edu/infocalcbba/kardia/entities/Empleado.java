/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.kardia.entities;

import edu.infocalcbba.publica.entities.Usuario;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "empleado", catalog = "infocaloruro", schema = "kardia")

@PrimaryKeyJoinColumn(name = "id_persona")
@DiscriminatorValue("Empleado")
public class Empleado extends Usuario implements Serializable {

    //datos personales
    @Temporal(TemporalType.DATE)
    private Date fecha_ingreso;
    private String codigo;
    private String apellido_casada;
    private String lugar_nacimiento;
    private String estado_civil;
    private String tipo_sangre;
    private String tipo_casa;
    private String telefono_emergencia;
    private String contacto_emergencia;
    private String email_corporativo;

    //datos contractuales
    private String afp;
    private String nua;
    private Boolean cps;
    private String numero_asegurado;
    private String banco;
    private String numero_cuenta;
    private Boolean estado;

    @JoinColumn(name = "id_cargo")
    @ManyToOne
    private Cargo cargo;

    public Empleado() {
    }

    /**
     * @return the fecha_ingreso
     */
    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    /**
     * @param fecha_ingreso the fecha_ingreso to set
     */
    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
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
     * @return the apellido_casada
     */
    public String getApellido_casada() {
        return apellido_casada;
    }

    /**
     * @param apellido_casada the apellido_casada to set
     */
    public void setApellido_casada(String apellido_casada) {
        this.apellido_casada = apellido_casada.toUpperCase();
    }

    /**
     * @return the lugar_nacimiento
     */
    public String getLugar_nacimiento() {
        return lugar_nacimiento;
    }

    /**
     * @param lugar_nacimiento the lugar_nacimiento to set
     */
    public void setLugar_nacimiento(String lugar_nacimiento) {
        this.lugar_nacimiento = lugar_nacimiento.toUpperCase();
    }

    /**
     * @return the estado_civil
     */
    public String getEstado_civil() {
        return estado_civil;
    }

    /**
     * @param estado_civil the estado_civil to set
     */
    public void setEstado_civil(String estado_civil) {
        this.estado_civil = estado_civil;
    }

    /**
     * @return the tipo_sangre
     */
    public String getTipo_sangre() {
        return tipo_sangre;
    }

    /**
     * @param tipo_sangre the tipo_sangre to set
     */
    public void setTipo_sangre(String tipo_sangre) {
        this.tipo_sangre = tipo_sangre;
    }

    /**
     * @return the tipo_casa
     */
    public String getTipo_casa() {
        return tipo_casa;
    }

    /**
     * @param tipo_casa the tipo_casa to set
     */
    public void setTipo_casa(String tipo_casa) {
        this.tipo_casa = tipo_casa;
    }

    /**
     * @return the telefono_emergencia
     */
    public String getTelefono_emergencia() {
        return telefono_emergencia;
    }

    /**
     * @param telefono_emergencia the telefono_emergencia to set
     */
    public void setTelefono_emergencia(String telefono_emergencia) {
        this.telefono_emergencia = telefono_emergencia;
    }

    /**
     * @return the contacto_emergencia
     */
    public String getContacto_emergencia() {
        return contacto_emergencia;
    }

    /**
     * @param contacto_emergencia the contacto_emergencia to set
     */
    public void setContacto_emergencia(String contacto_emergencia) {
        this.contacto_emergencia = contacto_emergencia.toUpperCase();
    }

    /**
     * @return the afp
     */
    public String getAfp() {
        return afp;
    }

    /**
     * @param afp the afp to set
     */
    public void setAfp(String afp) {
        this.afp = afp;
    }

    /**
     * @return the nua
     */
    public String getNua() {
        return nua;
    }

    /**
     * @param nua the nua to set
     */
    public void setNua(String nua) {
        this.nua = nua;
    }

    /**
     * @return the cps
     */
    public Boolean getCps() {
        return cps;
    }

    /**
     * @param cps the cps to set
     */
    public void setCps(Boolean cps) {
        this.cps = cps;
    }

    /**
     * @return the numero_asegurado
     */
    public String getNumero_asegurado() {
        return numero_asegurado;
    }

    /**
     * @param numero_asegurado the numero_asegurado to set
     */
    public void setNumero_asegurado(String numero_asegurado) {
        this.numero_asegurado = numero_asegurado;
    }

    /**
     * @return the banco
     */
    public String getBanco() {
        return banco;
    }

    /**
     * @param banco the banco to set
     */
    public void setBanco(String banco) {
        this.banco = banco.toUpperCase();
    }

    /**
     * @return the numero_cuenta
     */
    public String getNumero_cuenta() {
        return numero_cuenta;
    }

    /**
     * @param numero_cuenta the numero_cuenta to set
     */
    public void setNumero_cuenta(String numero_cuenta) {
        this.numero_cuenta = numero_cuenta;
    }

    /**
     * @return the cargo
     */
    public Cargo getCargo() {
        return cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getCps_SiNo() {
        return cps ? "Sí" : "No";
    }

    /**
     * @return the email_corporativo
     */
    public String getEmail_corporativo() {
        return email_corporativo;
    }

    /**
     * @param email_corporativo the email_corporativo to set
     */
    public void setEmail_corporativo(String email_corporativo) {
        this.email_corporativo = email_corporativo;
    }

    /**
     * @return the estado
     */
    public Boolean getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

}
