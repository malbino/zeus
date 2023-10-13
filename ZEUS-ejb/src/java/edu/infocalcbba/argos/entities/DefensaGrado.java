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
import java.util.Objects;
import javax.persistence.CascadeType;
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
@Table(name = "defensagrado", catalog = "infocaloruro", schema = "argos")
public class DefensaGrado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_defensagrado;
    private String codigo;

    @JoinColumn(name = "id_egresado")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Egresado egresado;
    private String modalidad;
    private String tutor;
    @JoinColumn(name = "id_presidente")
    @ManyToOne
    private Empleado presidente;
    @JoinColumn(name = "id_tribunal1")
    @ManyToOne
    private Empleado tribunal1;
    @JoinColumn(name = "id_tribunal2")
    @ManyToOne
    private Empleado tribunal2;
    private String tribunal3;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "id_aula")
    @ManyToOne
    private Aula aula;
    @JoinColumn(name = "id_convocatoria")
    @ManyToOne
    private Convocatoria convocatoria;
    @OneToMany(mappedBy = "defensaGrado", orphanRemoval = true)
    private List<Derecho> derechos;
    @JoinColumn(name = "id_carrera")
    @ManyToOne(cascade = CascadeType.MERGE)
    private Carrera carrera;

    private String titulo;
    private String titulo_teorico;
    private String titulo_practico;
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fin;
    private Integer nota;
    private Integer nota_teorico;
    private Integer nota_practico;
    private String condicion;
    private String condicion_teorico;
    private String condicion_practico;

    private String libro;
    private String folio;
    private String registro;

    @JoinColumn(name = "id_asc1")
    @ManyToOne(cascade = CascadeType.MERGE)
    private ASC asc1;
    @JoinColumn(name = "id_asc2")
    @ManyToOne(cascade = CascadeType.MERGE)
    private ASC asc2;
    @JoinColumn(name = "id_asc3")
    @ManyToOne(cascade = CascadeType.MERGE)
    private ASC asc3;
    @JoinColumn(name = "id_asc4")
    @ManyToOne(cascade = CascadeType.MERGE)
    private ASC asc4;
    @JoinColumn(name = "id_asc5")
    @ManyToOne(cascade = CascadeType.MERGE)
    private ASC asc5;
    
    private Integer nota_asc1;
    private Integer nota_asc2;
    private Integer nota_asc3;
    private Integer nota_asc4;
    private Integer nota_asc5;
    
    private String condicion_asc1;
    private String condicion_asc2;
    private String condicion_asc3;
    private String condicion_asc4;
    private String condicion_asc5;
    
    @Transient
    private Integer costo_defensa;

    public DefensaGrado() {
    }

    public String getFecha_ddMMyyyy() {
        String s = "";
        if (fecha != null) {
            s = Reloj.formatearFecha_ddMMyyyy(fecha);
        }
        return s;
    }

    public String getFecha_HHmm() {
        String s = "";
        if (fecha != null) {
            s = Reloj.formatearFecha_HHmm(fecha);
        }
        return s;
    }

    public String getFecha_ddMMyyyyHHmm() {
        String s = "";
        if (fecha != null) {
            s = Reloj.formatearFecha_ddMMyyyyHHmm(fecha);
        }
        return s;
    }

    public String getInicio_ddMMyyyyHHmm() {
        String s = " ";
        if (inicio != null) {
            s = Reloj.formatearFecha_ddMMyyyyHHmm(inicio);
        }
        return s;
    }

    public String getFin_ddMMyyyyHHmm() {
        String s = " ";
        if (fin != null) {
            s = Reloj.formatearFecha_ddMMyyyyHHmm(fin);
        }
        return s;
    }

    public String getDerecho() {
        String derecho = "";
        if (!derechos.isEmpty()) {
            derecho = derechos.get(0).getPagado() + " Bs. " + derechos.get(0).getCondicion();
        }
        return derecho;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id_defensagrado);
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
        final DefensaGrado other = (DefensaGrado) obj;
        if (!Objects.equals(this.id_defensagrado, other.id_defensagrado)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String s;
        if (egresado.getEstudiante() != null && egresado.getCarrera() != null) {
            s = egresado.getEstudiante().toString() + " [" + egresado.getCarrera().getNombre() + ", " + this.getConvocatoria().toString() + "]";
        } else {
            s = egresado.getPersonal() + " [" + egresado.getPlanestudio() + ", " + this.getConvocatoria().toString() + "]";
        }
        return s;
    }

    /**
     * @return the id_defensagrado
     */
    public Integer getId_defensagrado() {
        return id_defensagrado;
    }

    /**
     * @param id_defensagrado the id_defensagrado to set
     */
    public void setId_defensagrado(Integer id_defensagrado) {
        this.id_defensagrado = id_defensagrado;
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
     * @return the egresado
     */
    public Egresado getEgresado() {
        return egresado;
    }

    /**
     * @param egresado the egresado to set
     */
    public void setEgresado(Egresado egresado) {
        this.egresado = egresado;
    }

    /**
     * @return the modalidad
     */
    public String getModalidad() {
        return modalidad;
    }

    /**
     * @param modalidad the modalidad to set
     */
    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    /**
     * @return the tutor
     */
    public String getTutor() {
        return tutor;
    }

    /**
     * @param tutor the tutor to set
     */
    public void setTutor(String tutor) {
        this.tutor = tutor.toUpperCase();
    }

    /**
     * @return the presidente
     */
    public Empleado getPresidente() {
        return presidente;
    }

    /**
     * @param presidente the presidente to set
     */
    public void setPresidente(Empleado presidente) {
        this.presidente = presidente;
    }

    /**
     * @return the tribunal1
     */
    public Empleado getTribunal1() {
        return tribunal1;
    }

    /**
     * @param tribunal1 the tribunal1 to set
     */
    public void setTribunal1(Empleado tribunal1) {
        this.tribunal1 = tribunal1;
    }

    /**
     * @return the tribunal2
     */
    public Empleado getTribunal2() {
        return tribunal2;
    }

    /**
     * @param tribunal2 the tribunal2 to set
     */
    public void setTribunal2(Empleado tribunal2) {
        this.tribunal2 = tribunal2;
    }

    /**
     * @return the tribunal3
     */
    public String getTribunal3() {
        return tribunal3;
    }

    /**
     * @param tribunal3 the tribunal3 to set
     */
    public void setTribunal3(String tribunal3) {
        this.tribunal3 = tribunal3.toUpperCase();
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
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo.toUpperCase();
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
     * @return the nota
     */
    public Integer getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(Integer nota) {
        this.nota = nota;
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
     * @return the libro
     */
    public String getLibro() {
        return libro;
    }

    /**
     * @param libro the libro to set
     */
    public void setLibro(String libro) {
        this.libro = libro;
    }

    /**
     * @return the folio
     */
    public String getFolio() {
        return folio;
    }

    /**
     * @param folio the folio to set
     */
    public void setFolio(String folio) {
        this.folio = folio;
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
        this.registro = registro.toUpperCase();
    }

    /**
     * @return the titulo_teorico
     */
    public String getTitulo_teorico() {
        return titulo_teorico;
    }

    /**
     * @param titulo_teorico the titulo_teorico to set
     */
    public void setTitulo_teorico(String titulo_teorico) {
        this.titulo_teorico = titulo_teorico.toUpperCase();
    }

    /**
     * @return the titulo_practico
     */
    public String getTitulo_practico() {
        return titulo_practico;
    }

    /**
     * @param titulo_practico the titulo_practico to set
     */
    public void setTitulo_practico(String titulo_practico) {
        this.titulo_practico = titulo_practico.toUpperCase();
    }

    /**
     * @return the nota_teorico
     */
    public Integer getNota_teorico() {
        return nota_teorico;
    }

    /**
     * @param nota_teorico the nota_teorico to set
     */
    public void setNota_teorico(Integer nota_teorico) {
        this.nota_teorico = nota_teorico;
    }

    /**
     * @return the nota_practico
     */
    public Integer getNota_practico() {
        return nota_practico;
    }

    /**
     * @param nota_practico the nota_practico to set
     */
    public void setNota_practico(Integer nota_practico) {
        this.nota_practico = nota_practico;
    }

    /**
     * @return the condicion_teorico
     */
    public String getCondicion_teorico() {
        return condicion_teorico;
    }

    /**
     * @param condicion_teorico the condicion_teorico to set
     */
    public void setCondicion_teorico(String condicion_teorico) {
        this.condicion_teorico = condicion_teorico;
    }

    /**
     * @return the condicion_practico
     */
    public String getCondicion_practico() {
        return condicion_practico;
    }

    /**
     * @param condicion_practico the condicion_practico to set
     */
    public void setCondicion_practico(String condicion_practico) {
        this.condicion_practico = condicion_practico;
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
     * @return the derechos
     */
    public List<Derecho> getDerechos() {
        return derechos;
    }

    /**
     * @param derechos the derechos to set
     */
    public void setDerechos(List<Derecho> derechos) {
        this.derechos = derechos;
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

    /**
     * @return the nota_asc1
     */
    public Integer getNota_asc1() {
        return nota_asc1;
    }

    /**
     * @param nota_asc1 the nota_asc1 to set
     */
    public void setNota_asc1(Integer nota_asc1) {
        this.nota_asc1 = nota_asc1;
    }

    /**
     * @return the nota_asc2
     */
    public Integer getNota_asc2() {
        return nota_asc2;
    }

    /**
     * @param nota_asc2 the nota_asc2 to set
     */
    public void setNota_asc2(Integer nota_asc2) {
        this.nota_asc2 = nota_asc2;
    }

    /**
     * @return the nota_asc3
     */
    public Integer getNota_asc3() {
        return nota_asc3;
    }

    /**
     * @param nota_asc3 the nota_asc3 to set
     */
    public void setNota_asc3(Integer nota_asc3) {
        this.nota_asc3 = nota_asc3;
    }

    /**
     * @return the nota_asc4
     */
    public Integer getNota_asc4() {
        return nota_asc4;
    }

    /**
     * @param nota_asc4 the nota_asc4 to set
     */
    public void setNota_asc4(Integer nota_asc4) {
        this.nota_asc4 = nota_asc4;
    }

    /**
     * @return the nota_asc5
     */
    public Integer getNota_asc5() {
        return nota_asc5;
    }

    /**
     * @param nota_asc5 the nota_asc5 to set
     */
    public void setNota_asc5(Integer nota_asc5) {
        this.nota_asc5 = nota_asc5;
    }

    /**
     * @return the condicion_asc1
     */
    public String getCondicion_asc1() {
        return condicion_asc1;
    }

    /**
     * @param condicion_asc1 the condicion_asc1 to set
     */
    public void setCondicion_asc1(String condicion_asc1) {
        this.condicion_asc1 = condicion_asc1;
    }

    /**
     * @return the condicion_asc2
     */
    public String getCondicion_asc2() {
        return condicion_asc2;
    }

    /**
     * @param condicion_asc2 the condicion_asc2 to set
     */
    public void setCondicion_asc2(String condicion_asc2) {
        this.condicion_asc2 = condicion_asc2;
    }

    /**
     * @return the condicion_asc3
     */
    public String getCondicion_asc3() {
        return condicion_asc3;
    }

    /**
     * @param condicion_asc3 the condicion_asc3 to set
     */
    public void setCondicion_asc3(String condicion_asc3) {
        this.condicion_asc3 = condicion_asc3;
    }

    /**
     * @return the condicion_asc4
     */
    public String getCondicion_asc4() {
        return condicion_asc4;
    }

    /**
     * @param condicion_asc4 the condicion_asc4 to set
     */
    public void setCondicion_asc4(String condicion_asc4) {
        this.condicion_asc4 = condicion_asc4;
    }

    /**
     * @return the condicion_asc5
     */
    public String getCondicion_asc5() {
        return condicion_asc5;
    }

    /**
     * @param condicion_asc5 the condicion_asc5 to set
     */
    public void setCondicion_asc5(String condicion_asc5) {
        this.condicion_asc5 = condicion_asc5;
    }
}
