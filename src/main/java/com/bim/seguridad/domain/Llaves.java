package com.bim.seguridad.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "privada")
    private byte[] privada;



    @Lob
    @Column(name = "publica")
    private byte[] publica;


    @OneToOne
    @JoinColumn(unique = true)
    private Usuario usuario;

    @ManyToMany(mappedBy = "llaves")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Orden> ordens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPrivada() {
        return privada;
    }

    public Llaves privada(byte[] privada) {
        this.privada = privada;
        return this;
    }

    public void setPrivada(byte[] privada) {
        this.privada = privada;
    }

    
    public byte[] getPublica() {
        return publica;
    }

    public Llaves publica(byte[] publica) {
        this.publica = publica;
        return this;
    }

    public void setPublica(byte[] publica) {
        this.publica = publica;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public Llaves usuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        orden.getLlaves().add(this);
        return this;
    }

    public Llaves removeOrden(Orden orden) {
        this.ordens.remove(orden);
        orden.getLlaves().remove(this);
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
            ", privada='" + getPrivada() + "'" +            
            ", publica='" + getPublica() + "'" +
            "}";
    }
}
