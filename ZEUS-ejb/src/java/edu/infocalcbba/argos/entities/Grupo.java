/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import edu.infocalcbba.kardia.entities.Empleado;
import edu.infocalcbba.util.Reloj;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "grupo", catalog = "infocaloruro", schema = "argos")
public class Grupo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_grupo;

    private String codigo;
    private Integer capacidad;
    private String condicion;
    
    @Transient
    private long cantidadPeriodosDocente;
    @Transient
    private long cantidadInscritosRegulares;
    @Transient
    private long cantidadInscritos;

    @JoinColumn(name = "id_paralelo")
    @ManyToOne
    private Paralelo paralelo;

    @JoinColumn(name = "id_asc")
    @ManyToOne
    private ASC asc;

    @JoinColumn(name = "id_persona")
    @ManyToOne
    private Empleado empleado;

    @OneToMany(mappedBy = "grupo")
    private List<Clase> clases;

    @JoinColumn(name = "id_aula")
    @ManyToOne
    private Aula aula;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    
    public Grupo() {
    }

    public Grupo(Integer idGrupo) {
        this.id_grupo = idGrupo;
    }

    public Integer getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(Integer id_grupo) {
        this.id_grupo = id_grupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_grupo != null ? id_grupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.id_grupo == null && other.id_grupo != null) || (this.id_grupo != null && !this.id_grupo.equals(other.id_grupo))) {
            return false;
        }
        return true;
    }

    public String toString_Turno() {
        return this.codigo + " [" + this.getParalelo().getTurno().getNombre() + "]";
    }

    public String toString_ASCTurno() {
        return this.codigo + " [" + this.getAsc().getNombre() + ", " + this.getParalelo().getTurno().getNombre() + "]";
    }

    public String toString_ASCEmpleado() {
        String res;

        if (empleado != null) {
            res = this.codigo + " [" + this.getAsc().getNombre() + ", " + this.empleado.toString() + "]";
        } else {
            res = this.codigo + " [" + this.getAsc().getNombre() + ", POR DESIGNAR...]";
        }

        return res;
    }

    public String toString_ASCTurnoEmpleado() {
        String res;

        if (empleado != null) {
            res = this.codigo + " [" + this.getAsc().getNombre() + ", " + this.getParalelo().getTurno().getNombre() + ", " + this.empleado.toString() + "]";
        } else {
            res = this.codigo + " [" + this.getAsc().getNombre() + ", " + this.getParalelo().getTurno().getNombre() + ", POR DESIGNAR...]";
        }

        return res;
    }

    public String toString_TurnoEmpleado() {
        String res;

        if (empleado != null) {
            res = this.codigo + " [" + this.getParalelo().getTurno().getNombre() + ", " + this.empleado.toString() + "]";
        } else {
            res = this.codigo + " [" + this.getParalelo().getTurno().getNombre() + ", POR DESIGNAR...]";
        }

        return res;
    }

    public String toString_GACarreraNETurnoASC() {
        return this.codigo + " [" + this.getParalelo().getGestionacademica().getCodigo() + ", " + this.getParalelo().getCarrera().getNombre() + ", " + this.getParalelo().getNivelestudio().getNombre() + ", " + this.getParalelo().getTurno().getNombre() + ", " + this.getAsc().getNombre() + "]";
    }
    
     public String getFecha_ddMMyyyyHHmm() {
         String f = "";
         if(fecha != null){
             f = Reloj.formatearFecha_ddMMyyyyHHmm(fecha);
         }   
        return f;
    }

    /**
     * @return the idASC
     */
    public ASC getAsc() {
        return asc;
    }

    /**
     * @param asc the idASC to set
     */
    public void setAsc(ASC asc) {
        this.asc = asc;
    }

    /**
     * @return the idPersona
     */
    public Empleado getEmpleado() {
        return empleado;
    }

    /**
     * @param empleado the idPersona to set
     */
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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
     * @return the capacidad
     */
    public Integer getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * @return the paralelo
     */
    public Paralelo getParalelo() {
        return paralelo;
    }

    /**
     * @param paralelo the paralelo to set
     */
    public void setParalelo(Paralelo paralelo) {
        this.paralelo = paralelo;
    }

    /**
     * @return the condicion
     */
    public String getCondicion() {
        return condicion;
    }

    /**
     * @param condicion the condicion to set
     */
    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    /**
     * @return the clases
     */
    public List<Clase> getClases() {
        return clases;
    }

    /**
     * @param clases the clases to set
     */
    public void setClases(List<Clase> clases) {
        this.clases = clases;
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
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the cantidadInscritosRegulares
     */
    public long getCantidadInscritosRegulares() {
        return cantidadInscritosRegulares;
    }

    /**
     * @param cantidadInscritosRegulares the cantidadInscritosRegulares to set
     */
    public void setCantidadInscritosRegulares(long cantidadInscritosRegulares) {
        this.cantidadInscritosRegulares = cantidadInscritosRegulares;
    }

    /**
     * @return the cantidadInscritos
     */
    public long getCantidadInscritos() {
        return cantidadInscritos;
    }

    /**
     * @param cantidadInscritos the cantidadInscritos to set
     */
    public void setCantidadInscritos(long cantidadInscritos) {
        this.cantidadInscritos = cantidadInscritos;
    }

    /**
     * @return the cantidadPeriodosDocente
     */
    public long getCantidadPeriodosDocente() {
        return cantidadPeriodosDocente;
    }

    /**
     * @param cantidadPeriodosDocente the cantidadPeriodosDocente to set
     */
    public void setCantidadPeriodosDocente(long cantidadPeriodosDocente) {
        this.cantidadPeriodosDocente = cantidadPeriodosDocente;
    }
}
