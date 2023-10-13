 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import edu.infocalcbba.publica.entities.Rol;
import edu.infocalcbba.util.Reloj;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "gestionacademica", catalog = "infocaloruro", schema = "argos")
public class GestionAcademica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_gestionacademica;

    private String codigo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fin;
    private Boolean estado;
    private Integer periodo;

    @JoinColumn(name = "id_regimen")
    @ManyToOne
    private Regimen regimen;

    @JoinColumn(name = "id_anterior")
    @ManyToOne
    private GestionAcademica anterior;

    @JoinColumn(name = "id_evaluacion")
    @ManyToOne
    private Evaluacion evaluacion;

    @ManyToMany(mappedBy = "gestionesAcademicas")
    private List<Rol> roles;

    public GestionAcademica() {
    }

    public GestionAcademica(Integer id_gestionacademica) {
        this.id_gestionacademica = id_gestionacademica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_gestionacademica != null ? id_gestionacademica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GestionAcademica)) {
            return false;
        }
        GestionAcademica other = (GestionAcademica) object;
        if ((this.id_gestionacademica == null && other.id_gestionacademica != null) || (this.id_gestionacademica != null && !this.id_gestionacademica.equals(other.id_gestionacademica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getCodigo() + " [" + this.regimen.getNombre() + "]";
    }

    public String toString_FileName() {
        return getCodigo().replace("/", "") + getRegimen().getNombre().substring(0, 1);
    }
    
    public String toString_Centralizador(){
        String s = null;
        
        if(regimen.getId_regimen() == 1){
            s = codigo;
        } else if(regimen.getId_regimen() == 2){
            s = codigo.split("/")[1];
        }
                
        return s;
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

    public String getInicio_ddMMyyyyHHmm() {
        return Reloj.formatearFecha_ddMMyyyyHHmm(inicio);
    }

    public String getFin_ddMMyyyyHHmm() {
        return Reloj.formatearFecha_ddMMyyyyHHmm(fin);
    }

    /**
     * @return the id_gestionacademica
     */
    public Integer getId_gestionacademica() {
        return id_gestionacademica;
    }

    /**
     * @param id_gestionacademica the id_gestionacademica to set
     */
    public void setId_gestionacademica(Integer id_gestionacademica) {
        this.id_gestionacademica = id_gestionacademica;
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
     * @return the id_anterior
     */
    public GestionAcademica getAnterior() {
        return anterior;
    }

    /**
     * @param anterior the id_anterior to set
     */
    public void setAnterior(GestionAcademica anterior) {
        this.anterior = anterior;
    }

    /**
     * @return the estado
     */
    public Boolean getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getEstado_VigentePasada() {
        return estado ? "VIGENTE" : "PASADA";
    }

    /**
     * @return the periodo
     */
    public Integer getPeriodo() {
        return periodo;
    }

    /**
     * @param periodo the periodo to set
     */
    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    /**
     * @return the evaluacion
     */
    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    /**
     * @param evaluacion the evaluacion to set
     */
    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getIngreso() {
        return String.valueOf(periodo).substring(4, 5);
    }

    public String getGestion() {
        return String.valueOf(periodo).substring(0, 4);
    }

    public String getParalelo() {
        String res = null;

        if (getIngreso().compareTo("1") == 0) {
            res = "PB";
        } else if (getIngreso().compareTo("2") == 0) {
            res = "PA";
        }

        return res;
    }

    /**
     * @return the roles
     */
    public List<Rol> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
