/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "evaluacion", catalog = "infocaloruro", schema = "argos")
public class Evaluacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_evaluacion;

    private Integer max_dp1;
    private Integer max_df1;
    private Integer max_ex1;
    private Integer max_p1;

    private Integer max_dp2;
    private Integer max_df2;
    private Integer max_ex2;
    private Integer max_p2;

    private Integer max_dp3;
    private Integer max_df3;
    private Integer max_ex3;
    private Integer max_p3;

    private Integer max_dp4;
    private Integer max_df4;
    private Integer max_ex4;
    private Integer max_p4;

    private Integer nota_min;
    private Integer nota_max;

    private Integer nota_min_apr;
    private Integer nota_min_ins;

    private Integer parciales;
    private Integer max_re;

    public Evaluacion() {
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id_evaluacion);
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
        final Evaluacion other = (Evaluacion) obj;
        if (!Objects.equals(this.id_evaluacion, other.id_evaluacion)) {
            return false;
        }
        return true;
    }

    /**
     * @return the id_evaluacion
     */
    public Integer getId_evaluacion() {
        return id_evaluacion;
    }

    /**
     * @param id_evaluacion the id_evaluacion to set
     */
    public void setId_evaluacion(Integer id_evaluacion) {
        this.id_evaluacion = id_evaluacion;
    }

    /**
     * @return the max_dp1
     */
    public Integer getMax_dp1() {
        return max_dp1;
    }

    /**
     * @param max_dp1 the max_dp1 to set
     */
    public void setMax_dp1(Integer max_dp1) {
        this.max_dp1 = max_dp1;
    }

    /**
     * @return the max_df1
     */
    public Integer getMax_df1() {
        return max_df1;
    }

    /**
     * @param max_df1 the max_df1 to set
     */
    public void setMax_df1(Integer max_df1) {
        this.max_df1 = max_df1;
    }

    /**
     * @return the max_ex1
     */
    public Integer getMax_ex1() {
        return max_ex1;
    }

    /**
     * @param max_ex1 the max_ex1 to set
     */
    public void setMax_ex1(Integer max_ex1) {
        this.max_ex1 = max_ex1;
    }

    /**
     * @return the max_p1
     */
    public Integer getMax_p1() {
        return max_p1;
    }

    /**
     * @param max_p1 the max_p1 to set
     */
    public void setMax_p1(Integer max_p1) {
        this.max_p1 = max_p1;
    }

    /**
     * @return the max_dp2
     */
    public Integer getMax_dp2() {
        return max_dp2;
    }

    /**
     * @param max_dp2 the max_dp2 to set
     */
    public void setMax_dp2(Integer max_dp2) {
        this.max_dp2 = max_dp2;
    }

    /**
     * @return the max_df2
     */
    public Integer getMax_df2() {
        return max_df2;
    }

    /**
     * @param max_df2 the max_df2 to set
     */
    public void setMax_df2(Integer max_df2) {
        this.max_df2 = max_df2;
    }

    /**
     * @return the max_ex2
     */
    public Integer getMax_ex2() {
        return max_ex2;
    }

    /**
     * @param max_ex2 the max_ex2 to set
     */
    public void setMax_ex2(Integer max_ex2) {
        this.max_ex2 = max_ex2;
    }

    /**
     * @return the max_p2
     */
    public Integer getMax_p2() {
        return max_p2;
    }

    /**
     * @param max_p2 the max_p2 to set
     */
    public void setMax_p2(Integer max_p2) {
        this.max_p2 = max_p2;
    }

    /**
     * @return the max_dp3
     */
    public Integer getMax_dp3() {
        return max_dp3;
    }

    /**
     * @param max_dp3 the max_dp3 to set
     */
    public void setMax_dp3(Integer max_dp3) {
        this.max_dp3 = max_dp3;
    }

    /**
     * @return the max_df3
     */
    public Integer getMax_df3() {
        return max_df3;
    }

    /**
     * @param max_df3 the max_df3 to set
     */
    public void setMax_df3(Integer max_df3) {
        this.max_df3 = max_df3;
    }

    /**
     * @return the max_ex3
     */
    public Integer getMax_ex3() {
        return max_ex3;
    }

    /**
     * @param max_ex3 the max_ex3 to set
     */
    public void setMax_ex3(Integer max_ex3) {
        this.max_ex3 = max_ex3;
    }

    /**
     * @return the max_p3
     */
    public Integer getMax_p3() {
        return max_p3;
    }

    /**
     * @param max_p3 the max_p3 to set
     */
    public void setMax_p3(Integer max_p3) {
        this.max_p3 = max_p3;
    }

    /**
     * @return the nota_min
     */
    public Integer getNota_min() {
        return nota_min;
    }

    /**
     * @param nota_min the nota_min to set
     */
    public void setNota_min(Integer nota_min) {
        this.nota_min = nota_min;
    }

    /**
     * @return the nota_max
     */
    public Integer getNota_max() {
        return nota_max;
    }

    /**
     * @param nota_max the nota_max to set
     */
    public void setNota_max(Integer nota_max) {
        this.nota_max = nota_max;
    }

    /**
     * @return the nota_min_apr
     */
    public Integer getNota_min_apr() {
        return nota_min_apr;
    }

    /**
     * @param nota_min_apr the nota_min_apr to set
     */
    public void setNota_min_apr(Integer nota_min_apr) {
        this.nota_min_apr = nota_min_apr;
    }

    /**
     * @return the nota_min_ins
     */
    public Integer getNota_min_ins() {
        return nota_min_ins;
    }

    /**
     * @param nota_min_ins the nota_min_ins to set
     */
    public void setNota_min_ins(Integer nota_min_ins) {
        this.nota_min_ins = nota_min_ins;
    }

    /**
     * @return the max_dp4
     */
    public Integer getMax_dp4() {
        return max_dp4;
    }

    /**
     * @param max_dp4 the max_dp4 to set
     */
    public void setMax_dp4(Integer max_dp4) {
        this.max_dp4 = max_dp4;
    }

    /**
     * @return the max_df4
     */
    public Integer getMax_df4() {
        return max_df4;
    }

    /**
     * @param max_df4 the max_df4 to set
     */
    public void setMax_df4(Integer max_df4) {
        this.max_df4 = max_df4;
    }

    /**
     * @return the max_ex4
     */
    public Integer getMax_ex4() {
        return max_ex4;
    }

    /**
     * @param max_ex4 the max_ex4 to set
     */
    public void setMax_ex4(Integer max_ex4) {
        this.max_ex4 = max_ex4;
    }

    /**
     * @return the max_p4
     */
    public Integer getMax_p4() {
        return max_p4;
    }

    /**
     * @param max_p4 the max_p4 to set
     */
    public void setMax_p4(Integer max_p4) {
        this.max_p4 = max_p4;
    }

    /**
     * @return the parciales
     */
    public Integer getParciales() {
        return parciales;
    }

    /**
     * @param parciales the parciales to set
     */
    public void setParciales(Integer parciales) {
        this.parciales = parciales;
    }

    /**
     * @return the max_re
     */
    public Integer getMax_re() {
        return max_re;
    }

    /**
     * @param max_re the max_re to set
     */
    public void setMax_re(Integer max_re) {
        this.max_re = max_re;
    }

}
