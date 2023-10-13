/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.phoenix.entities;

import edu.infocalcbba.kardia.entities.Empleado;
import edu.infocalcbba.util.Reloj;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
 * @author Tincho
 */
@Entity
@Table(name = "grupo", catalog = "infocaloruro", schema = "phoenix")
public class GrupoPh implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_grupo;
    private String codigo;
    private Integer horas;
    private Integer costo;
    @Temporal(TemporalType.DATE)
    private Date inicio;
    @Temporal(TemporalType.DATE)
    private Date fin;
    private Integer nro_min_estudiantes;
    private Integer nro_max_estudiantes;
    private Integer numero_pagos;

    @JoinColumn(name = "id_gestion")
    @ManyToOne
    private GestionPh gestion;

    @JoinColumn(name = "id_curso")
    @ManyToOne
    private Curso curso;

    @JoinColumn(name = "id_persona")
    @ManyToOne
    private Empleado empleado;

    /**
     * @return the id_grupo
     */
    public Integer getId_grupo() {
        return id_grupo;
    }

    /**
     * @param id_grupo the id_grupo to set
     */
    public void setId_grupo(Integer id_grupo) {
        this.id_grupo = id_grupo;
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
     * @return the horas
     */
    public Integer getHoras() {
        return horas;
    }

    /**
     * @param horas the horas to set
     */
    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    /**
     * @return the costo
     */
    public Integer getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(Integer costo) {
        this.costo = costo;
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
     * @return the nro_min_estudiantes
     */
    public Integer getNro_min_estudiantes() {
        return nro_min_estudiantes;
    }

    /**
     * @param nro_min_estudiantes the nro_min_estudiantes to set
     */
    public void setNro_min_estudiantes(Integer nro_min_estudiantes) {
        this.nro_min_estudiantes = nro_min_estudiantes;
    }

    /**
     * @return the nro_max_estudiantes
     */
    public Integer getNro_max_estudiantes() {
        return nro_max_estudiantes;
    }

    /**
     * @param nro_max_estudiantes the nro_max_estudiantes to set
     */
    public void setNro_max_estudiantes(Integer nro_max_estudiantes) {
        this.nro_max_estudiantes = nro_max_estudiantes;
    }

    /**
     * @return the numero_pagos
     */
    public Integer getNumero_pagos() {
        return numero_pagos;
    }

    /**
     * @param numero_pagos the numero_pagos to set
     */
    public void setNumero_pagos(Integer numero_pagos) {
        this.numero_pagos = numero_pagos;
    }

    /**
     * @return the gestion
     */
    public GestionPh getGestion() {
        return gestion;
    }

    /**
     * @param gestion the gestion to set
     */
    public void setGestion(GestionPh gestion) {
        this.gestion = gestion;
    }

    /**
     * @return the curso
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     * @return the empleado
     */
    public Empleado getEmpleado() {
        return empleado;
    }

    /**
     * @param empleado the empleado to set
     */
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getInicio_ddMMyyyy() {
        return Reloj.formatearFecha_ddMMyyyy(inicio);
    }

    public String getFin_ddMMyyyy() {
        return Reloj.formatearFecha_ddMMyyyy(fin);
    }
    
    @Override
    public String toString() {
        return this.codigo + " [ " + this.curso.getNombre() + " v" + this.curso.getVersion() + "]";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id_grupo);
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
        final GrupoPh other = (GrupoPh) obj;
        if (!Objects.equals(this.id_grupo, other.id_grupo)) {
            return false;
        }
        return true;
    }
    
}
