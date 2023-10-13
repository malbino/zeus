/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.argos.entities;

import edu.infocalcbba.util.Redondeo;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "periodo", catalog = "infocaloruro", schema = "argos", uniqueConstraints = @UniqueConstraint(columnNames = {"horas", "dia"}))
public class Periodo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_periodo;

    private String horas;
    private String dia;
    
    public Periodo() {
    }

    public Periodo(Integer id_bloque) {
        this.id_periodo = id_bloque;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId_periodo() != null ? getId_periodo().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Periodo)) {
            return false;
        }
        Periodo other = (Periodo) object;
        if ((this.getId_periodo() == null && other.getId_periodo() != null) || (this.getId_periodo() != null && !this.id_periodo.equals(other.id_periodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.dia + " [" + this.horas + "]";
    }

    /**
     * @return the horas
     */
    public String getHoras() {
        return horas;
    }

    /**
     * @param horas the horas to set
     */
    public void setHoras(String horas) {
        this.horas = horas;
    }

    /**
     * @return the dia
     */
    public String getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(String dia) {
        this.dia = dia;
    }

    /**
     * @return the id_periodo
     */
    public Integer getId_periodo() {
        return id_periodo;
    }

    /**
     * @param id_periodo the id_periodo to set
     */
    public void setId_periodo(Integer id_periodo) {
        this.id_periodo = id_periodo;
    }

    public Calendar getInicio_Calendar() {
        String[] split1 = horas.split(" - ");
        String[] split2 = split1[0].split(":");

        int hora_inicio = Integer.valueOf(split2[0]);
        int minuto_inicio = Integer.valueOf(split2[1]);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hora_inicio);
        c.set(Calendar.MINUTE, minuto_inicio);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c;
    }

    public Calendar getFin_Calendar() {
        String[] split1 = horas.split(" - ");
        String[] split2 = split1[1].split(":");

        int hora_fin = Integer.valueOf(split2[0]);
        int minuto_fin = Integer.valueOf(split2[1]);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hora_fin);
        c.set(Calendar.MINUTE, minuto_fin);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c;
    }

    public double getHoras_Double() {
        Calendar c1 = getInicio_Calendar();
        Calendar c2 = getFin_Calendar();

        double milisegundos = c2.getTimeInMillis() - c1.getTimeInMillis();

        return milisegundos / (60 * 60 * 1000);
    }

    public String getHorasDouble_0p00() {
        return Redondeo.formatear_0p00(getHoras_Double());
    }

}
