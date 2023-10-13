/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import edu.infocalcbba.zeus.entities.Detalle;
import java.io.Serializable;
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
import javax.persistence.Transient;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "nota", catalog = "infocaloruro", schema = "argos")
public class Nota implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_nota;

    private String modalidad;
    private Integer as1;
    private Integer dp1;
    private Integer df1;
    private Integer ex1;
    private Integer p1;
    private Integer as2;
    private Integer dp2;
    private Integer df2;
    private Integer ex2;
    private Integer p2;
    private Integer as3;
    private Integer dp3;
    private Integer df3;
    private Integer ex3;
    private Integer p3;
    private Integer as4;
    private Integer dp4;
    private Integer df4;
    private Integer ex4;
    private Integer p4;
    private Integer nf;
    private Integer ins;
    private String condicion;

    @JoinColumn(name = "id_grupo")
    @ManyToOne
    private Grupo grupo;

    @JoinColumn(name = "id_inscrito")
    @ManyToOne
    private Inscrito inscrito;

    @JoinColumn(name = "id_persona")
    @ManyToOne
    private Estudiante estudiante;

    @JoinColumn(name = "id_asc")
    @ManyToOne
    private ASC asc;

    @JoinColumn(name = "id_gestionacademica")
    @ManyToOne
    private GestionAcademica gestionacademica;
    
    @OneToMany(mappedBy = "nota")
    private List<Detalle> detalles;
    
    @Transient
    private Integer numeroComprobante;

    public Nota() {
    }

    public Nota(Integer idGrupo) {
        this.id_nota = idGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id_nota);
        hash = 41 * hash + Objects.hashCode(this.asc);
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
        final Nota other = (Nota) obj;
        if (!Objects.equals(this.id_nota, other.id_nota)) {
            return false;
        }
        if (!Objects.equals(this.asc, other.asc)) {
            return false;
        }
        return true;
    } 

    /**
     * @return the id_nota
     */
    public Integer getId_nota() {
        return id_nota;
    }

    /**
     * @param id_nota the id_nota to set
     */
    public void setId_nota(Integer id_nota) {
        this.id_nota = id_nota;
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
     * @return the dp1
     */
    public Integer getDp1() {
        return dp1;
    }

    /**
     * @param dp1 the dp1 to set
     */
    public void setDp1(Integer dp1) {
        this.dp1 = dp1;
    }

    /**
     * @return the df1
     */
    public Integer getDf1() {
        return df1;
    }

    /**
     * @param df1 the df1 to set
     */
    public void setDf1(Integer df1) {
        this.df1 = df1;
    }

    /**
     * @return the ex1
     */
    public Integer getEx1() {
        return ex1;
    }

    /**
     * @param ex1 the ex1 to set
     */
    public void setEx1(Integer ex1) {
        this.ex1 = ex1;
    }

    /**
     * @return the p1
     */
    public Integer getP1() {
        return p1;
    }

    /**
     * @param p1 the p1 to set
     */
    public void setP1(Integer p1) {
        this.p1 = p1;
    }

    /**
     * @return the dp2
     */
    public Integer getDp2() {
        return dp2;
    }

    /**
     * @param dp2 the dp2 to set
     */
    public void setDp2(Integer dp2) {
        this.dp2 = dp2;
    }

    /**
     * @return the df2
     */
    public Integer getDf2() {
        return df2;
    }

    /**
     * @param df2 the df2 to set
     */
    public void setDf2(Integer df2) {
        this.df2 = df2;
    }

    /**
     * @return the ex2
     */
    public Integer getEx2() {
        return ex2;
    }

    /**
     * @param ex2 the ex2 to set
     */
    public void setEx2(Integer ex2) {
        this.ex2 = ex2;
    }

    /**
     * @return the p2
     */
    public Integer getP2() {
        return p2;
    }

    /**
     * @param p2 the p2 to set
     */
    public void setP2(Integer p2) {
        this.p2 = p2;
    }

    /**
     * @return the dp3
     */
    public Integer getDp3() {
        return dp3;
    }

    /**
     * @param dp3 the dp3 to set
     */
    public void setDp3(Integer dp3) {
        this.dp3 = dp3;
    }

    /**
     * @return the df3
     */
    public Integer getDf3() {
        return df3;
    }

    /**
     * @param df3 the df3 to set
     */
    public void setDf3(Integer df3) {
        this.df3 = df3;
    }

    /**
     * @return the ex3
     */
    public Integer getEx3() {
        return ex3;
    }

    /**
     * @param ex3 the ex3 to set
     */
    public void setEx3(Integer ex3) {
        this.ex3 = ex3;
    }

    /**
     * @return the p3
     */
    public Integer getP3() {
        return p3;
    }

    /**
     * @param p3 the p3 to set
     */
    public void setP3(Integer p3) {
        this.p3 = p3;
    }

    /**
     * @return the nf
     */
    public Integer getNf() {
        return nf;
    }

    /**
     * @param nf the nf to set
     */
    public void setNf(Integer nf) {
        this.nf = nf;
    }

    /**
     * @return the ins
     */
    public Integer getIns() {
        return ins;
    }

    /**
     * @param ins the ins to set
     */
    public void setIns(Integer ins) {
        this.ins = ins;
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
     * @return the inscrito
     */
    public Inscrito getInscrito() {
        return inscrito;
    }

    /**
     * @param inscrito the inscrito to set
     */
    public void setInscrito(Inscrito inscrito) {
        this.inscrito = inscrito;
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
     * @return the asc
     */
    public ASC getAsc() {
        return asc;
    }

    /**
     * @param asc the asc to set
     */
    public void setAsc(ASC asc) {
        this.asc = asc;
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

    public String getModalidadAbreviada() {
        String res = "";

        String[] split = modalidad.split(" ");
        for (String s : split) {
            res += s.substring(0, 1);
        }

        return res;
    }

    public String getCondicionAbreviada() {
        return condicion.substring(0, 2);
    }

    /**
     * @return the as1
     */
    public Integer getAs1() {
        return as1;
    }

    /**
     * @param as1 the as1 to set
     */
    public void setAs1(Integer as1) {
        this.as1 = as1;
    }

    /**
     * @return the as2
     */
    public Integer getAs2() {
        return as2;
    }

    /**
     * @param as2 the as2 to set
     */
    public void setAs2(Integer as2) {
        this.as2 = as2;
    }

    /**
     * @return the as3
     */
    public Integer getAs3() {
        return as3;
    }

    /**
     * @param as3 the as3 to set
     */
    public void setAs3(Integer as3) {
        this.as3 = as3;
    }

    /**
     * @return the as4
     */
    public Integer getAs4() {
        return as4;
    }

    /**
     * @param as4 the as4 to set
     */
    public void setAs4(Integer as4) {
        this.as4 = as4;
    }

    /**
     * @return the dp4
     */
    public Integer getDp4() {
        return dp4;
    }

    /**
     * @param dp4 the dp4 to set
     */
    public void setDp4(Integer dp4) {
        this.dp4 = dp4;
    }

    /**
     * @return the df4
     */
    public Integer getDf4() {
        return df4;
    }

    /**
     * @param df4 the df4 to set
     */
    public void setDf4(Integer df4) {
        this.df4 = df4;
    }

    /**
     * @return the ex4
     */
    public Integer getEx4() {
        return ex4;
    }

    /**
     * @param ex4 the ex4 to set
     */
    public void setEx4(Integer ex4) {
        this.ex4 = ex4;
    }

    /**
     * @return the p4
     */
    public Integer getP4() {
        return p4;
    }

    /**
     * @param p4 the p4 to set
     */
    public void setP4(Integer p4) {
        this.p4 = p4;
    }

    /**
     * @return the detalles
     */
    public List<Detalle> getDetalles() {
        return detalles;
    }

    /**
     * @param detalles the detalles to set
     */
    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }

    /**
     * @return the numeroComprobante
     */
    public Integer getNumeroComprobante() {
        return numeroComprobante;
    }

    /**
     * @param numeroComprobante the numeroComprobante to set
     */
    public void setNumeroComprobante(Integer numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }
}
