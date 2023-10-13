 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import edu.infocalcbba.util.Reloj;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "convocatoria", catalog = "infocaloruro", schema = "argos")
public class Convocatoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_convocatoria;

    private Integer numero;
    private Integer gestion;
    private Integer grupo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fin;
    private Boolean estado;

    public Convocatoria() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id_convocatoria);
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
        final Convocatoria other = (Convocatoria) obj;
        if (!Objects.equals(this.id_convocatoria, other.id_convocatoria)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return numero + "ª CONVOCATORIA G" + grupo + " " + gestion;
    }
    
     public String getInicio_ddMMyyyyHHmm() {
        return Reloj.formatearFecha_ddMMyyyyHHmm(inicio);
    }

    public String getFin_ddMMyyyyHHmm() {
        return Reloj.formatearFecha_ddMMyyyyHHmm(fin);
    }
    
    public String getEstado_VigentePasada() {
        return estado ? "VIGENTE" : "PASADA";
    }

    /**
     * @return the id_convocatoria
     */
    public Integer getId_convocatoria() {
        return id_convocatoria;
    }

    /**
     * @param id_convocatoria the id_convocatoria to set
     */
    public void setId_convocatoria(Integer id_convocatoria) {
        this.id_convocatoria = id_convocatoria;
    }

    /**
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * @return the gestion
     */
    public Integer getGestion() {
        return gestion;
    }

    /**
     * @param gestion the gestion to set
     */
    public void setGestion(Integer gestion) {
        this.gestion = gestion;
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

    /**
     * @return the grupo
     */
    public Integer getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }

}
