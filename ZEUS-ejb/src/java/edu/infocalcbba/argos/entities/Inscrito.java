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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "inscrito", catalog = "infocaloruro", schema = "argos", uniqueConstraints = @UniqueConstraint(columnNames = {"id_gestionacademica", "id_carrera", "id_persona"}))
public class Inscrito implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_inscrito;

    @Temporal(TemporalType.DATE)
    private Date fecha;
    private String condicion;
    private int numero;

    private String codigo1;
    private String codigo2;
    private String codigo3;
    private String codigo4;
    private String codigo5;
    @Transient
    private String codigo1SinEncriptar;
    @Transient
    private String codigo2SinEncriptar;
    @Transient
    private String codigo3SinEncriptar;
    @Transient
    private String codigo4SinEncriptar;
    @Transient
    private String codigo5SinEncriptar;

    @JoinColumn(name = "id_gestionacademica")
    @ManyToOne
    private GestionAcademica gestionacademica;

    @JoinColumn(name = "id_carrera")
    @ManyToOne
    private Carrera carrera;

    @JoinColumn(name = "id_persona")
    @ManyToOne
    private Estudiante estudiante;

    @JoinColumn(name = "id_descuento")
    @ManyToOne
    private Descuento descuento;

    @JoinColumn(name = "id_anterior")
    @ManyToOne
    private Inscrito anterior;

    @OneToMany(mappedBy = "inscrito", orphanRemoval = true)
    private List<Nota> notas;

    @OneToMany(mappedBy = "inscrito", cascade = {CascadeType.MERGE}, orphanRemoval = true)
    @OrderBy("numero")
    private List<Cuota> cuotas;

    public Inscrito() {
        this.estudiante = new Estudiante();
    }

    public Inscrito(Integer idInscrito) {
        this.id_inscrito = idInscrito;
    }

    public Inscrito(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Integer getId_inscrito() {
        return id_inscrito;
    }

    public void setId_inscrito(Integer id_inscrito) {
        this.id_inscrito = id_inscrito;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId_inscrito() != null ? getId_inscrito().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inscrito)) {
            return false;
        }
        Inscrito other = (Inscrito) object;
        if ((this.getId_inscrito() == null && other.getId_inscrito() != null) || (this.getId_inscrito() != null && !this.id_inscrito.equals(other.id_inscrito))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getCarrera().getNombre() + " [" + this.getGestionacademica().getCodigo() + "]";
    }

    /**
     * @return the idGestionAcademica
     */
    public GestionAcademica getGestionacademica() {
        return gestionacademica;
    }

    /**
     * @param gestionacademica the idGestionAcademica to set
     */
    public void setGestionacademica(GestionAcademica gestionacademica) {
        this.gestionacademica = gestionacademica;
    }

    /**
     * @return the idPersona
     */
    public Estudiante getEstudiante() {
        return estudiante;
    }

    /**
     * @param estudiante the idPersona to set
     */
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    /**
     * @return the idCarrera
     */
    public Carrera getCarrera() {
        return carrera;
    }

    /**
     * @param carrera the idCarrera to set
     */
    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    /**
     * @return the ingreso
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the ingreso to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFecha_mmDDyyyy() {
        return Reloj.formatearFecha_ddMMyyyy(fecha);
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
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the codigo1
     */
    public String getCodigo1() {
        return codigo1;
    }

    /**
     * @param codigo1 the codigo1 to set
     */
    public void setCodigo1(String codigo1) {
        this.codigo1 = codigo1;
    }

    /**
     * @return the codigo2
     */
    public String getCodigo2() {
        return codigo2;
    }

    /**
     * @param codigo2 the codigo2 to set
     */
    public void setCodigo2(String codigo2) {
        this.codigo2 = codigo2;
    }

    /**
     * @return the codigo3
     */
    public String getCodigo3() {
        return codigo3;
    }

    /**
     * @param codigo3 the codigo3 to set
     */
    public void setCodigo3(String codigo3) {
        this.codigo3 = codigo3;
    }

    /**
     * @return the codigo4
     */
    public String getCodigo4() {
        return codigo4;
    }

    /**
     * @param codigo4 the codigo4 to set
     */
    public void setCodigo4(String codigo4) {
        this.codigo4 = codigo4;
    }

    /**
     * @return the codigo5
     */
    public String getCodigo5() {
        return codigo5;
    }

    /**
     * @param codigo5 the codigo5 to set
     */
    public void setCodigo5(String codigo5) {
        this.codigo5 = codigo5;
    }

    /**
     * @return the codigo1SinEncriptar
     */
    public String getCodigo1SinEncriptar() {
        return codigo1SinEncriptar;
    }

    /**
     * @param codigo1SinEncriptar the codigo1SinEncriptar to set
     */
    public void setCodigo1SinEncriptar(String codigo1SinEncriptar) {
        this.codigo1SinEncriptar = codigo1SinEncriptar;
    }

    /**
     * @return the codigo2SinEncriptar
     */
    public String getCodigo2SinEncriptar() {
        return codigo2SinEncriptar;
    }

    /**
     * @param codigo2SinEncriptar the codigo2SinEncriptar to set
     */
    public void setCodigo2SinEncriptar(String codigo2SinEncriptar) {
        this.codigo2SinEncriptar = codigo2SinEncriptar;
    }

    /**
     * @return the codigo3SinEncriptar
     */
    public String getCodigo3SinEncriptar() {
        return codigo3SinEncriptar;
    }

    /**
     * @param codigo3SinEncriptar the codigo3SinEncriptar to set
     */
    public void setCodigo3SinEncriptar(String codigo3SinEncriptar) {
        this.codigo3SinEncriptar = codigo3SinEncriptar;
    }

    /**
     * @return the codigo4SinEncriptar
     */
    public String getCodigo4SinEncriptar() {
        return codigo4SinEncriptar;
    }

    /**
     * @param codigo4SinEncriptar the codigo4SinEncriptar to set
     */
    public void setCodigo4SinEncriptar(String codigo4SinEncriptar) {
        this.codigo4SinEncriptar = codigo4SinEncriptar;
    }

    /**
     * @return the codigo5SinEncriptar
     */
    public String getCodigo5SinEncriptar() {
        return codigo5SinEncriptar;
    }

    /**
     * @param codigo5SinEncriptar the codigo5SinEncriptar to set
     */
    public void setCodigo5SinEncriptar(String codigo5SinEncriptar) {
        this.codigo5SinEncriptar = codigo5SinEncriptar;
    }

    /**
     * @return the anterior
     */
    public Inscrito getAnterior() {
        return anterior;
    }

    /**
     * @param anterior the anterior to set
     */
    public void setAnterior(Inscrito anterior) {
        this.anterior = anterior;
    }

    /**
     * @return the descuento
     */
    public Descuento getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }

    /**
     * @return the notas
     */
    public List<Nota> getNotas() {
        return notas;
    }

    /**
     * @param notas the notas to set
     */
    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    /**
     * @return the cuotas
     */
    public List<Cuota> getCuotas() {
        return cuotas;
    }

    /**
     * @param cuotas the cuotas to set
     */
    public void setCuotas(List<Cuota> cuotas) {
        this.cuotas = cuotas;
    }
}
