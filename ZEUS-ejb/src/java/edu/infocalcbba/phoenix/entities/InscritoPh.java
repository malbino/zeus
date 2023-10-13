/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.phoenix.entities;

import edu.infocalcbba.argos.entities.Estudiante;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Tincho
 */
@Entity
@Table(name = "inscrito", catalog = "infocaloruro", schema = "phoenix")
public class InscritoPh implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_inscrito;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @JoinColumn(name = "id_gestion")
    @ManyToOne
    private GestionPh gestion;

    @JoinColumn(name = "id_grupo")
    @ManyToOne
    private GrupoPh grupo;

    @JoinColumn(name = "id_persona")
    @ManyToOne
    private Estudiante estudiante;

    @OneToOne(mappedBy = "inscrito")
    private PlanPagoPh planpago;

    /**
     * @return the id_inscrito
     */
    public Integer getId_inscrito() {
        return id_inscrito;
    }

    /**
     * @param id_inscrito the id_inscrito to set
     */
    public void setId_inscrito(Integer id_inscrito) {
        this.id_inscrito = id_inscrito;
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
     * @return the grupo
     */
    public GrupoPh getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(GrupoPh grupo) {
        this.grupo = grupo;
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
     * @return the planpago
     */
    public PlanPagoPh getPlanpago() {
        return planpago;
    }

    /**
     * @param planpago the planpago to set
     */
    public void setPlanpago(PlanPagoPh planpago) {
        this.planpago = planpago;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id_inscrito);
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
        final InscritoPh other = (InscritoPh) obj;
        if (!Objects.equals(this.id_inscrito, other.id_inscrito)) {
            return false;
        }
        return true;
    }

    
}
