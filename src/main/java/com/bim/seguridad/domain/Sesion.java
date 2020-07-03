package com.bim.seguridad.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Sesion.
 */
@Entity
@Table(name = "sesion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sesion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cliente_id")
    private Integer clienteId;

    @Column(name = "activo")
    private Boolean activo;

    @OneToOne
    @JoinColumn(unique = true)
    private User clienteId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public Sesion clienteId(Integer clienteId) {
        this.clienteId = clienteId;
        return this;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Sesion activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public User getClienteId() {
        return clienteId;
    }

    public Sesion clienteId(User user) {
        this.clienteId = user;
        return this;
    }

    public void setClienteId(User user) {
        this.clienteId = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sesion)) {
            return false;
        }
        return id != null && id.equals(((Sesion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Sesion{" +
            "id=" + getId() +
            ", clienteId=" + getClienteId() +
            ", activo='" + isActivo() + "'" +
            "}";
    }
}
