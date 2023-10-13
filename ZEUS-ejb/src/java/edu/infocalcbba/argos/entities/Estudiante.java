/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import edu.infocalcbba.publica.entities.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "estudiante", catalog = "infocaloruro", schema = "argos")

@PrimaryKeyJoinColumn(name = "id_persona")
@DiscriminatorValue("Estudiante")
public class Estudiante extends Usuario implements Serializable {

    private Integer egreso_colegio;
    private String lugar_nacimiento;

    @JoinTable(name = "file", catalog = "infocaloruro", schema = "argos", joinColumns = {
        @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")}, inverseJoinColumns = {
        @JoinColumn(name = "id_documento", referencedColumnName = "id_documento")})
    @ManyToMany
    private List<Documento> documentos;

    @JoinColumn(name = "id_colegio")
    @ManyToOne
    private Colegio colegio;

    @JoinTable(name = "estudia", catalog = "infocaloruro", schema = "argos", joinColumns = {
        @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")}, inverseJoinColumns = {
        @JoinColumn(name = "id_carrera", referencedColumnName = "id_carrera")})
    @ManyToMany
    private List<Carrera> carreras;

    @OneToMany(mappedBy = "estudiante", orphanRemoval = true)
    private List<Inscrito> inscritos;
    

    public Estudiante() {
    }

    public String getDniLugar() {
        return this.getDni() + " " + this.getLugar();
    }

    /**
     * @return the documentos
     */
    public List<Documento> getDocumentos() {
        return documentos;
    }

    /**
     * @param documentos the documentos to set
     */
    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    /**
     * @return the colegio
     */
    public Colegio getColegio() {
        return colegio;
    }

    /**
     * @param colegio the colegio to set
     */
    public void setColegio(Colegio colegio) {
        this.colegio = colegio;
    }

    /**
     * @return the egreso_colegio
     */
    public Integer getEgreso_colegio() {
        return egreso_colegio;
    }

    /**
     * @param egreso_colegio the egreso_colegio to set
     */
    public void setEgreso_colegio(Integer egreso_colegio) {
        this.egreso_colegio = egreso_colegio;
    }

    /**
     * @return the carreras
     */
    public List<Carrera> getCarreras() {
        return carreras;
    }

    /**
     * @param carreras the carreras to set
     */
    public void setCarreras(List<Carrera> carreras) {
        this.carreras = carreras;
    }

    /**
     * @return the inscritos
     */
    public List<Inscrito> getInscritos() {
        return inscritos;
    }

    /**
     * @param inscritos the inscritos to set
     */
    public void setInscritos(List<Inscrito> inscritos) {
        this.inscritos = inscritos;
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
        this.lugar_nacimiento = lugar_nacimiento;
    }

}
