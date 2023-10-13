/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.zeus.entities;

import java.io.Serializable;
import javax.persistence.Column;
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
@Table(name = "pagotramcert", catalog = "infocaloruro", schema = "zeus")
public class PagoTramCert implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pagotramcert;

    @Column(unique = true)
    private String codigo;
    private String concepto;
    private Integer monto;


    public PagoTramCert() {
    }

    public PagoTramCert(Integer id_pago) {
        this.id_pagotramcert = id_pago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_pagotramcert != null ? id_pagotramcert.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoTramCert)) {
            return false;
        }
        PagoTramCert other = (PagoTramCert) object;
        if ((this.id_pagotramcert == null && other.id_pagotramcert != null) || (this.id_pagotramcert != null && !this.id_pagotramcert.equals(other.id_pagotramcert))) {
            return false;
        }
        return true;
    }

    /**
     * @return the id_pagotramcert
     */
    public Integer getId_pagotramcert() {
        return id_pagotramcert;
    }

    /**
     * @param id_pagotramcert the id_pagotramcert to set
     */
    public void setId_pagotramcert(Integer id_pagotramcert) {
        this.id_pagotramcert = id_pagotramcert;
    }

    /**
     * @return the monto
     */
    public Integer getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(Integer monto) {
        this.monto = monto;
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
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
}
