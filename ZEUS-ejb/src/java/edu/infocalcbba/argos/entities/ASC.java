/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "asc", catalog = "infocaloruro", schema = "argos")
public class ASC implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_asc;

    private String codigo;
    private String nombre;
    private Integer hp;
    private Integer ht;
    private Integer th;
    private Integer th_csc;
    private Integer nro_creditos;
    private Boolean regular;
    private Boolean mesa_examen;
    private Boolean eximicion;
    private String tipo;
    private Integer numero;

    @JoinColumn(name = "id_csc")
    @ManyToOne
    private CSC csc;

    @JoinColumn(name = "id_nivelestudio")
    @ManyToOne
    private NivelEstudio nivelestudio;

    @JoinColumn(name = "id_carrera")
    @ManyToOne
    private Carrera carrera;

    @JoinTable(name = "prerequisito", catalog = "infocaloruro", schema = "argos", joinColumns = {
        @JoinColumn(name = "id_asc", referencedColumnName = "id_asc")}, inverseJoinColumns = {
        @JoinColumn(name = "id_prerequisito", referencedColumnName = "id_asc")})
    @ManyToMany
    private List<ASC> prerequisitos;

    public ASC() {
    }

    public ASC(Integer id_asc) {
        this.id_asc = id_asc;
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
        hash += (id_asc != null ? id_asc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ASC)) {
            return false;
        }
        ASC other = (ASC) object;
        if ((this.id_asc == null && other.id_asc != null) || (this.id_asc != null && !this.id_asc.equals(other.id_asc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre + " [" + this.codigo + "]";
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
     * @return the hp
     */
    public Integer getHp() {
        return hp;
    }

    /**
     * @param hp the hp to set
     */
    public void setHp(Integer hp) {
        this.hp = hp;
    }

    /**
     * @return the ht
     */
    public Integer getHt() {
        return ht;
    }

    /**
     * @param ht the ht to set
     */
    public void setHt(Integer ht) {
        this.ht = ht;
    }

    /**
     * @return the th
     */
    public Integer getTh() {
        return th;
    }

    /**
     * @param th the th to set
     */
    public void setTh(Integer th) {
        this.th = th;
    }

    public String getPrerequisitos_Cadena() {
        String s = "";

        for (ASC m : prerequisitos) {
            if (s.compareTo("") == 0) {
                s += m.getCodigo();
            } else {
                s += ", " + m.getCodigo();
            }
        }

        return s;
    }

    /**
     * @return the id_asc
     */
    public Integer getId_asc() {
        return id_asc;
    }

    /**
     * @param id_asc the id_asc to set
     */
    public void setId_asc(Integer id_asc) {
        this.id_asc = id_asc;
    }

    /**
     * @return the csc
     */
    public CSC getCsc() {
        return csc;
    }

    /**
     * @param csc the csc to set
     */
    public void setCsc(CSC csc) {
        this.csc = csc;
    }

    /**
     * @return the nivelestudio
     */
    public NivelEstudio getNivelestudio() {
        return nivelestudio;
    }

    /**
     * @param nivelestudio the nivelestudio to set
     */
    public void setNivelestudio(NivelEstudio nivelestudio) {
        this.nivelestudio = nivelestudio;
    }

    /**
     * @return the carrera
     */
    public Carrera getCarrera() {
        return carrera;
    }

    /**
     * @param carrera the carrera to set
     */
    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    /**
     * @return the th_csc
     */
    public Integer getTh_csc() {
        return th_csc;
    }

    /**
     * @param th_csc the th_csc to set
     */
    public void setTh_csc(Integer th_csc) {
        this.th_csc = th_csc;
    }

    /**
     * @return the nro_creditos
     */
    public Integer getNro_creditos() {
        return nro_creditos;
    }

    /**
     * @param nro_creditos the nro_creditos to set
     */
    public void setNro_creditos(Integer nro_creditos) {
        this.nro_creditos = nro_creditos;
    }

    /**
     * @return the prerequisitos
     */
    public List<ASC> getPrerequisitos() {
        return prerequisitos;
    }

    /**
     * @param prerequisitos the prerequisitos to set
     */
    public void setPrerequisitos(List<ASC> prerequisitos) {
        this.prerequisitos = prerequisitos;
    }

    /**
     * @return the mesa_examen
     */
    public Boolean getMesa_examen() {
        return mesa_examen;
    }

    /**
     * @param mesa_examen the mesa_examen to set
     */
    public void setMesa_examen(Boolean mesa_examen) {
        this.mesa_examen = mesa_examen;
    }

    /**
     * @return the eximicion
     */
    public Boolean getEximicion() {
        return eximicion;
    }

    /**
     * @param eximicion the eximicion to set
     */
    public void setEximicion(Boolean eximicion) {
        this.eximicion = eximicion;
    }
    
    /**
     * @return the regular
     */
    public Boolean getRegular() {
        return regular;
    }

    /**
     * @param regular the regular to set
     */
    public void setRegular(Boolean regular) {
        this.regular = regular;
    }
    
    public String getRegular_SiNo(){
        return regular ? "Sí" : "No";
    }

    public String getMesa_examen_SiNo(){
        return mesa_examen ? "Sí" : "No";
    }
    
    public String getEximicion_SiNo(){
        return eximicion ? "Sí" : "No";
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
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}
