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
@Table(name = "programacionasc", catalog = "infocaloruro", schema = "argos")
public class ProgramacionASC implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_programacionasc;

    @JoinColumn(name = "id_convocatoria")
    @ManyToOne
    private Convocatoria convocatoria;

    @JoinColumn(name = "id_carrera")
    @ManyToOne
    private Carrera carrera;

    @JoinColumn(name = "id_asc1")
    @ManyToOne
    private ASC asc1;

    @JoinColumn(name = "id_asc2")
    @ManyToOne
    private ASC asc2;

    @JoinColumn(name = "id_asc3")
    @ManyToOne
    private ASC asc3;

    @JoinColumn(name = "id_asc4")
    @ManyToOne
    private ASC asc4;

    @JoinColumn(name = "id_asc5")
    @ManyToOne
    private ASC asc5;

    /**
     * @return the id_programacionasc
     */
    public Integer getId_programacionasc() {
        return id_programacionasc;
    }

    /**
     * @param id_programacionasc the id_programacionasc to set
     */
    public void setId_programacionasc(Integer id_programacionasc) {
        this.id_programacionasc = id_programacionasc;
    }

    /**
     * @return the convocatoria
     */
    public Convocatoria getConvocatoria() {
        return convocatoria;
    }

    /**
     * @param convocatoria the convocatoria to set
     */
    public void setConvocatoria(Convocatoria convocatoria) {
        this.convocatoria = convocatoria;
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
     * @return the asc1
     */
    public ASC getAsc1() {
        return asc1;
    }

    /**
     * @param asc1 the asc1 to set
     */
    public void setAsc1(ASC asc1) {
        this.asc1 = asc1;
    }

    /**
     * @return the asc2
     */
    public ASC getAsc2() {
        return asc2;
    }

    /**
     * @param asc2 the asc2 to set
     */
    public void setAsc2(ASC asc2) {
        this.asc2 = asc2;
    }

    /**
     * @return the asc3
     */
    public ASC getAsc3() {
        return asc3;
    }

    /**
     * @param asc3 the asc3 to set
     */
    public void setAsc3(ASC asc3) {
        this.asc3 = asc3;
    }

    /**
     * @return the asc4
     */
    public ASC getAsc4() {
        return asc4;
    }

    /**
     * @param asc4 the asc4 to set
     */
    public void setAsc4(ASC asc4) {
        this.asc4 = asc4;
    }

    /**
     * @return the asc5
     */
    public ASC getAsc5() {
        return asc5;
    }

    /**
     * @param asc5 the asc5 to set
     */
    public void setAsc5(ASC asc5) {
        this.asc5 = asc5;
    }

}
