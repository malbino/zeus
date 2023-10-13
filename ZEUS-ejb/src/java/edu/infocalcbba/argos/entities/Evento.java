/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import edu.infocalcbba.publica.entities.Funcionalidad;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "evento", catalog = "infocaloruro", schema = "argos")
public class Evento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_evento;

    private String nombre;

    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fin;

    @JoinColumn(name = "id_calendario")
    @ManyToOne
    private Calendario calendario;

    @JoinColumn(name = "id_gestionacademica")
    @ManyToOne
    private GestionAcademica gestionacademica;

    @JoinColumn(name = "id_funcionalidad")
    @ManyToOne
    private Funcionalidad funcionalidad;

    public Evento() {
    }

    public Evento(Integer id_evento) {
        this.id_evento = id_evento;
    }

    public Evento(String nombre, Date inicio, Date fin, GestionAcademica gestionacademica) {
        this.nombre = nombre;
        this.inicio = inicio;
        this.fin = fin;
        this.gestionacademica = gestionacademica;
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
        hash += (id_evento != null ? id_evento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.id_evento == null && other.id_evento != null) || (this.id_evento != null && !this.id_evento.equals(other.id_evento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    /**
     * @return the inicio
     */
    public Date getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the fin
     */
    public Date getFin() {
        return fin;
    }

    /**
     * @param fin the fin to set
     */
    public void setFin(Date fin) {
        this.fin = fin;
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
     * @return the calendarioacademico
     */
    public Calendario getCalendario() {
        return calendario;
    }

    /**
     * @param calendario the calendarioacademico to set
     */
    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    /**
     * @return the funcionalidad
     */
    public Funcionalidad getFuncionalidad() {
        return funcionalidad;
    }

    /**
     * @param funcionalidad the funcionalidad to set
     */
    public void setFuncionalidad(Funcionalidad funcionalidad) {
        this.funcionalidad = funcionalidad;
    }
}
