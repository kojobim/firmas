package com.bim.seguridad.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Llaves.
 */
@Entity
@Table(name = "llaves")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Llaves implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "publica")
    private String publica;

    @Lob
    @Column(name = "privada")
    private String privada;

    @Column(name = "cliente_id")
    private Integer clienteId;

    @OneToOne
    @JoinColumn(unique = true)
    private User clienteId;

    @OneToMany(mappedBy = "llaveId")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Orden> ordens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublica() {
        return publica;
    }

    public Llaves publica(String publica) {
        this.publica = publica;
        return this;
    }

    public void setPublica(String publica) {
        this.publica = publica;
    }

    public String getPrivada() {
        return privada;
    }

    public Llaves privada(String privada) {
        this.privada = privada;
        return this;
    }

    public void setPrivada(String privada) {
        this.privada = privada;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public Llaves clienteId(Integer clienteId) {
        this.clienteId = clienteId;
        return this;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public User getClienteId() {
        return clienteId;
    }

    public Llaves clienteId(User user) {
        this.clienteId = user;
        return this;
    }

    public void setClienteId(User user) {
        this.clienteId = user;
    }

    public Set<Orden> getOrdens() {
        return ordens;
    }

    public Llaves ordens(Set<Orden> ordens) {
        this.ordens = ordens;
        return this;
    }

    public Llaves addOrden(Orden orden) {
        this.ordens.add(orden);
        orden.setLlaveId(this);
        return this;
    }

    public Llaves removeOrden(Orden orden) {
        this.ordens.remove(orden);
        orden.setLlaveId(null);
        return this;
    }

    public void setOrdens(Set<Orden> ordens) {
        this.ordens = ordens;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Llaves)) {
            return false;
        }
        return id != null && id.equals(((Llaves) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Llaves{" +
            "id=" + getId() +
            ", publica='" + getPublica() + "'" +
            ", privada='" + getPrivada() + "'" +
            ", clienteId=" + getClienteId() +
            "}";
    }
}
