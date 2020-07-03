package com.bim.seguridad.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Orden.
 */
@Entity
@Table(name = "orden")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "firma")
    private String firma;

    @Column(name = "llava_id")
    private Integer llavaId;

    @Column(name = "primary_key")
    private String primaryKey;

    @ManyToOne
    @JsonIgnoreProperties("ordens")
    private Llaves llaveId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Orden numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFirma() {
        return firma;
    }

    public Orden firma(String firma) {
        this.firma = firma;
        return this;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public Integer getLlavaId() {
        return llavaId;
    }

    public Orden llavaId(Integer llavaId) {
        this.llavaId = llavaId;
        return this;
    }

    public void setLlavaId(Integer llavaId) {
        this.llavaId = llavaId;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public Orden primaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
        return this;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Llaves getLlaveId() {
        return llaveId;
    }

    public Orden llaveId(Llaves llaves) {
        this.llaveId = llaves;
        return this;
    }

    public void setLlaveId(Llaves llaves) {
        this.llaveId = llaves;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Orden)) {
            return false;
        }
        return id != null && id.equals(((Orden) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Orden{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", firma='" + getFirma() + "'" +
            ", llavaId=" + getLlavaId() +
            ", primaryKey='" + getPrimaryKey() + "'" +
            "}";
    }
}
