/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import edu.infocalcbba.util.Reloj;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
@Table(name = "egresado", catalog = "infocaloruro", schema = "argos")
public class Egresado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_egresado;
    private String codigo;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @JoinColumn(name = "id_estudiante")
    @ManyToOne
    private Estudiante estudiante;

    @JoinColumn(name = "id_carrera")
    @ManyToOne
    private Carrera carrera;

    private String planestudio;
    private String personal;
    private String dni;
    private String lugar_nacimiento;
    @Temporal(TemporalType.DATE)
    private Date inicio_formacion;
    @Temporal(TemporalType.DATE)
    private Date fin_formacion;
    private Integer copia_cce;
    private String registro;

    private Integer telefono;
    private Integer celular;
    private String email;
    @Temporal(TemporalType.DATE)
    private Date fecha_nacimiento;
    
    @OneToMany(mappedBy = "egresado")
    private List<DefensaGrado> defensasGrado;

    @Transient
    private String orden;

    public Egresado() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id_egresado);
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
        final Egresado other = (Egresado) obj;
        if (!Objects.equals(this.id_egresado, other.id_egresado)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String s;
        if (estudiante != null & carrera != null) {
            s = estudiante.toString() + " [" + carrera.getNombre() + "]";
        } else {
            s = personal + " [" + planestudio + "]";
        }
        return s;
    }

    public String toString_Estudiante() {
        String s;
        if (estudiante != null) {
            s = estudiante.toString();
        } else {
            s = personal;
        }
        return s;
    }

    public String toString_Carrera() {
        String s;
        if (carrera != null) {
            s = carrera.getNombre();
        } else {
            s = planestudio;
        }
        return s;
    }

    public String getFecha_ddMMyyyy() {
        String s = " ";
        if (fecha != null) {
            s = Reloj.formatearFecha_ddMMyyyy(fecha);
        }
        return s;
    }

    public String getInicio_formacion_ddMMyyyy() {
        String s = " ";
        if (inicio_formacion != null) {
            s = Reloj.formatearFecha_ddMMyyyy(inicio_formacion);
        }
        return s;
    }

    public String getFin_formacion_ddMMyyyy() {
        String s = " ";
        if (fin_formacion != null) {
            s = Reloj.formatearFecha_ddMMyyyy(fin_formacion);
        }
        return s;
    }

    public String getFecha_nacimiento_ddMMyyyy() {
        String s = " ";
        if (fecha_nacimiento != null) {
            s = Reloj.formatearFecha_ddMMyyyy(fecha_nacimiento);
        }
        return s;
    }

    /**
     * @return the id_egresado
     */
    public Integer getId_egresado() {
        return id_egresado;
    }

    /**
     * @param id_egresado the id_egresado to set
     */
    public void setId_egresado(Integer id_egresado) {
        this.id_egresado = id_egresado;
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
     * @return the estudiante
     */
    public Estudiante getEstudiante() {
        return estudiante;
    }

    /**
     * @param estudiante the estudiante to set
     */
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
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
     * @return the planestudio
     */
    public String getPlanestudio() {
        return planestudio;
    }

    /**
     * @param planestudio the planestudio to set
     */
    public void setPlanestudio(String planestudio) {
        this.planestudio = planestudio;
    }

    /**
     * @return the personal
     */
    public String getPersonal() {
        return personal;
    }

    /**
     * @param personal the personal to set
     */
    public void setPersonal(String personal) {
        this.personal = personal;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni.toUpperCase();
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
     * @return the inicio_formacion
     */
    public Date getInicio_formacion() {
        return inicio_formacion;
    }

    /**
     * @param inicio_formacion the inicio_formacion to set
     */
    public void setInicio_formacion(Date inicio_formacion) {
        this.inicio_formacion = inicio_formacion;
    }

    /**
     * @return the fin_formacion
     */
    public Date getFin_formacion() {
        return fin_formacion;
    }

    /**
     * @param fin_formacion the fin_formacion to set
     */
    public void setFin_formacion(Date fin_formacion) {
        this.fin_formacion = fin_formacion;
    }

    /**
     * @return the copia_cce
     */
    public Integer getCopia_cce() {
        return copia_cce;
    }

    /**
     * @param copia_cce the copia_cce to set
     */
    public void setCopia_cce(Integer copia_cce) {
        this.copia_cce = copia_cce;
    }

    /**
     * @return the registro
     */
    public String getRegistro() {
        return registro;
    }

    /**
     * @param registro the registro to set
     */
    public void setRegistro(String registro) {
        this.registro = registro;
    }

    /**
     * @return the telefono
     */
    public Integer getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the celular
     */
    public Integer getCelular() {
        return celular;
    }

    /**
     * @param celular the celular to set
     */
    public void setCelular(Integer celular) {
        this.celular = celular;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the fecha_nacimiento
     */
    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    /**
     * @param fecha_nacimiento the fecha_nacimiento to set
     */
    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    /**
     * @return the orden
     */
    public String getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(String orden) {
        this.orden = orden;
    }

    /**
     * @return the defensasGrado
     */
    public List<DefensaGrado> getDefensasGrado() {
        return defensasGrado;
    }

    /**
     * @param defensasGrado the defensasGrado to set
     */
    public void setDefensasGrado(List<DefensaGrado> defensasGrado) {
        this.defensasGrado = defensasGrado;
    }
}
