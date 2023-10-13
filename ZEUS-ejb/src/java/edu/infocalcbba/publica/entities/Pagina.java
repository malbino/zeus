/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infocalcbba.publica.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author tincho
 */
@Entity
@Table(name = "pagina", catalog = "infocaloruro", schema = "public")
public class Pagina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pagina;

    private String uri;
    
    @JoinColumn(name = "id_funcionalidad")
    @ManyToOne
    private Funcionalidad funcionalidad;

    public Pagina() {
    }

    /**
     * @return the id_pagina
     */
    public Integer getId_pagina() {
        return id_pagina;
    }

    /**
     * @param id_pagina the id_pagina to set
     */
    public void setId_pagina(Integer id_pagina) {
        this.id_pagina = id_pagina;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the funcionalidad
     */
    public Funcionalidad getFuncionalidad() {
        return funcionalidad;
    }

    /**
     * @param funcionalidad the funcionalidad to set
     */
    public void setFuncionalidad(Funcionalidad funcionalidad) {
        this.funcionalidad = funcionalidad;
    }
    
    
}
