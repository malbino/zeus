/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "clase", catalog = "infocaloruro", schema = "argos")
public class Clase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_clase;

    @JoinColumn(name = "id_grupo")
    @ManyToOne
    private Grupo grupo;

    @JoinColumn(name = "id_aula")
    @ManyToOne
    private Aula aula;

    @JoinColumn(name = "id_periodo")
    @ManyToOne
    private Periodo periodo;

    @Transient
    private String label;

    public Clase() {
    }

    public Clase(Integer id_bloque) {
        this.id_clase = id_bloque;
    }
    
    public Clase(String label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.grupo);
        hash = 71 * hash + Objects.hashCode(this.aula);
        hash = 71 * hash + Objects.hashCode(this.periodo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Clase other = (Clase) obj;
        if (!Objects.equals(this.grupo, other.grupo)) {
            return false;
        }
        if (!Objects.equals(this.aula, other.aula)) {
            return false;
        }
        if (!Objects.equals(this.periodo, other.periodo)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return grupo.getAsc().getNombre() + "\n" + grupo.getEmpleado().toString() + "\n" + aula.getCodigo();
    }

    /**
     * @return the id_clase
     */
    public Integer getId_clase() {
        return id_clase;
    }

    /**
     * @param id_clase the id_clase to set
     */
    public void setId_clase(Integer id_clase) {
        this.id_clase = id_clase;
    }

    /**
     * @return the grupo
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the aula
     */
    public Aula getAula() {
        return aula;
    }

    /**
     * @param aula the aula to set
     */
    public void setAula(Aula aula) {
        this.aula = aula;
    }

    /**
     * @return the periodo
     */
    public Periodo getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }
}
