package it.unibg.nextraining.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import it.unibg.nextraining.domain.enumeration.Feedback;

/**
 * A Prova70m.
 */
@Entity
@Table(name = "prova_70_m")
public class Prova70m implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_prova", nullable = false)
    private LocalDate dataProva;

    @NotNull
    @Column(name = "tempo", nullable = false)
    private Float tempo;

    @Column(name = "partenza_blocchi")
    private Boolean partenzaBlocchi;

    @DecimalMax(value = "100")
    @Column(name = "vel_max")
    private Float velMax;

    @Enumerated(EnumType.STRING)
    @Column(name = "commento")
    private Feedback commento;

    @Size(max = 50)
    @Column(name = "cond_climatiche", length = 50)
    private String condClimatiche;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("prova70ms")
    private Calciatore calciatore;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataProva() {
        return dataProva;
    }

    public Prova70m dataProva(LocalDate dataProva) {
        this.dataProva = dataProva;
        return this;
    }

    public void setDataProva(LocalDate dataProva) {
        this.dataProva = dataProva;
    }

    public Float getTempo() {
        return tempo;
    }

    public Prova70m tempo(Float tempo) {
        this.tempo = tempo;
        return this;
    }

    public void setTempo(Float tempo) {
        this.tempo = tempo;
    }

    public Boolean isPartenzaBlocchi() {
        return partenzaBlocchi;
    }

    public Prova70m partenzaBlocchi(Boolean partenzaBlocchi) {
        this.partenzaBlocchi = partenzaBlocchi;
        return this;
    }

    public void setPartenzaBlocchi(Boolean partenzaBlocchi) {
        this.partenzaBlocchi = partenzaBlocchi;
    }

    public Float getVelMax() {
        return velMax;
    }

    public Prova70m velMax(Float velMax) {
        this.velMax = velMax;
        return this;
    }

    public void setVelMax(Float velMax) {
        this.velMax = velMax;
    }

    public Feedback getCommento() {
        return commento;
    }

    public Prova70m commento(Feedback commento) {
        this.commento = commento;
        return this;
    }

    public void setCommento(Feedback commento) {
        this.commento = commento;
    }

    public String getCondClimatiche() {
        return condClimatiche;
    }

    public Prova70m condClimatiche(String condClimatiche) {
        this.condClimatiche = condClimatiche;
        return this;
    }

    public void setCondClimatiche(String condClimatiche) {
        this.condClimatiche = condClimatiche;
    }

    public Calciatore getCalciatore() {
        return calciatore;
    }

    public Prova70m calciatore(Calciatore calciatore) {
        this.calciatore = calciatore;
        return this;
    }

    public void setCalciatore(Calciatore calciatore) {
        this.calciatore = calciatore;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prova70m)) {
            return false;
        }
        return id != null && id.equals(((Prova70m) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Prova70m{" +
            "id=" + getId() +
            ", dataProva='" + getDataProva() + "'" +
            ", tempo=" + getTempo() +
            ", partenzaBlocchi='" + isPartenzaBlocchi() + "'" +
            ", velMax=" + getVelMax() +
            ", commento='" + getCommento() + "'" +
            ", condClimatiche='" + getCondClimatiche() + "'" +
            "}";
    }
}
