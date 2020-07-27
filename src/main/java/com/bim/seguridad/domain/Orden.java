package com.bim.seguridad.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Orden.
 */
@Entity
@Table(name = "orden")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "firma")
    private String firma;

    @Column(name = "primary_key")
    private String primaryKey;

    @Column(name = "transaccion")
    private String transaccion;

    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "operada")
    private Integer operada;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "orden_llaves",
               joinColumns = @JoinColumn(name = "orden_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "llaves_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Llaves> llaves = new HashSet<>();

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

    public String getTransaccion() {
        return transaccion;
    }

    public Orden transaccion(String transaccion) {
        this.transaccion = transaccion;
        return this;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public Integer getOperada() {
        return operada;
    }

    public Orden operada(Integer operada) {
        this.operada = operada;
        return this;
    }

    public void setOperada(Integer operada) {
        this.operada = operada;
    }

    public Set<Llaves> getLlaves() {
        return llaves;
    }

    public Orden llaves(Set<Llaves> llaves) {
        this.llaves = llaves;
        return this;
    }

    public Orden addLlaves(Llaves llaves) {
        this.llaves.add(llaves);
        llaves.getOrdens().add(this);
        return this;
    }

    public Orden removeLlaves(Llaves llaves) {
        this.llaves.remove(llaves);
        llaves.getOrdens().remove(this);
        return this;
    }

    public void setLlaves(Set<Llaves> llaves) {
        this.llaves = llaves;
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
            ", primaryKey='" + getPrimaryKey() + "'" +
            ", transaccion='" + getTransaccion() + "'" +
            ", operada=" + getOperada() +
            "}";
    }
}
