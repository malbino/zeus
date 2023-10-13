/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

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
@Table(name = "paralelo", catalog = "infocaloruro", schema = "argos")
public class Paralelo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_paralelo;

    private String codigo;
    private Integer costo_credito;

    @JoinColumn(name = "id_gestionacademica")
    @ManyToOne
    private GestionAcademica gestionacademica;

    @JoinColumn(name = "id_carrera")
    @ManyToOne
    private Carrera carrera;

    @JoinColumn(name = "id_nivelestudio")
    @ManyToOne
    private NivelEstudio nivelestudio;

    @JoinColumn(name = "id_turno")
    @ManyToOne
    private Turno turno;

    public Paralelo() {
    }

    public Paralelo(Integer idGrupo) {
        this.id_paralelo = idGrupo;
    }

    public Integer getId_paralelo() {
        return id_paralelo;
    }

    public void setId_paralelo(Integer id_paralelo) {
        this.id_paralelo = id_paralelo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_paralelo != null ? id_paralelo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Paralelo)) {
            return false;
        }
        Paralelo other = (Paralelo) object;
        if ((this.id_paralelo == null && other.id_paralelo != null) || (this.id_paralelo != null && !this.id_paralelo.equals(other.id_paralelo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.codigo + " [" + this.nivelestudio.getNombre() + ", " + this.turno.getNombre() + "]";
    }
    
    public String toString_NivelEstudioCodigoTurno() {
        return this.nivelestudio.getNombre() + ", " + this.codigo + ", " + this.turno.getNombre();
    }
    
    public String toString_Centralizador() {
        return this.nivelestudio.getNombre().split(" ")[0] + " " + this.codigo.split("-")[1];
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
     * @return the turno
     */
    public Turno getTurno() {
        return turno;
    }

    /**
     * @param turno the turno to set
     */
    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    /**
     * @return the gestionacademica
     */
    public GestionAcademica getGestionacademica() {
        return gestionacademica;
    }

    /**
     * @param gestionacademica the gestionacademica to set
     */
    public void setGestionacademica(GestionAcademica gestionacademica) {
        this.gestionacademica = gestionacademica;
    }

    /**
     * @return the costo_credito
     */
    public Integer getCosto_credito() {
        return costo_credito;
    }

    /**
     * @param costo_credito the costo_credito to set
     */
    public void setCosto_credito(Integer costo_credito) {
        this.costo_credito = costo_credito;
    }

}
