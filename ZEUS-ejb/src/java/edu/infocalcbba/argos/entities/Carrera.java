/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "carrera", catalog = "infocaloruro", schema = "argos")
public class Carrera implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_carrera;

    @Column(unique = true)
    private String codigo;
    private String nombre;
    private Integer version;
    private Integer costo_defensa;
    private String resolucion;

    @JoinColumn(name = "id_areaproductiva")
    @ManyToOne
    private AreaProductiva areaproductiva;

    @JoinColumn(name = "id_nivelacademico")
    @ManyToOne
    private NivelAcademico nivelacademico;

    @JoinColumn(name = "id_regimen")
    @ManyToOne
    private Regimen regimen;

    @JoinColumn(name = "id_campus")
    @ManyToOne
    private Campus campus;

    @ManyToMany(mappedBy = "carreras")
    private List<Estudiante> estudiantes;

    public Carrera() {
    }

    public Carrera(Integer idCarrera) {
        this.id_carrera = idCarrera;
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
        hash += (id_carrera != null ? id_carrera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carrera)) {
            return false;
        }
        Carrera other = (Carrera) object;
        if ((this.id_carrera == null && other.id_carrera != null) || (this.id_carrera != null && !this.id_carrera.equals(other.id_carrera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre + " [v" + this.version + "]";
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
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
     * @return the id_carrera
     */
    public Integer getId_carrera() {
        return id_carrera;
    }

    /**
     * @param id_carrera the id_carrera to set
     */
    public void setId_carrera(Integer id_carrera) {
        this.id_carrera = id_carrera;
    }

    /**
     * @return the areaproductiva
     */
    public AreaProductiva getAreaproductiva() {
        return areaproductiva;
    }

    /**
     * @param areaproductiva the areaproductiva to set
     */
    public void setAreaproductiva(AreaProductiva areaproductiva) {
        this.areaproductiva = areaproductiva;
    }

    /**
     * @return the nivelacademico
     */
    public NivelAcademico getNivelacademico() {
        return nivelacademico;
    }

    /**
     * @param nivelacademico the nivelacademico to set
     */
    public void setNivelacademico(NivelAcademico nivelacademico) {
        this.nivelacademico = nivelacademico;
    }

    /**
     * @return the regimen
     */
    public Regimen getRegimen() {
        return regimen;
    }

    /**
     * @param regimen the regimen to set
     */
    public void setRegimen(Regimen regimen) {
        this.regimen = regimen;
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

    /**
     * @return the estudiantes
     */
    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    /**
     * @param estudiantes the estudiantes to set
     */
    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    /**
     * @return the costo_defensa
     */
    public Integer getCosto_defensa() {
        return costo_defensa;
    }

    /**
     * @param costo_defensa the costo_defensa to set
     */
    public void setCosto_defensa(Integer costo_defensa) {
        this.costo_defensa = costo_defensa;
    }

    /**
     * @return the resolucion
     */
    public String getResolucion() {
        return resolucion;
    }

    /**
     * @param resolucion the resolucion to set
     */
    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

}
