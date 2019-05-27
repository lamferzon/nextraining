package it.unibg.nextraining.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import it.unibg.nextraining.domain.enumeration.Feedback;

/**
 * A Prova1500m.
 */
@Entity
@Table(name = "prova_1500_m")
public class Prova1500m implements Serializable {

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

    @DecimalMax(value = "10")
    @Column(name = "tempo_km")
    private Float tempoKm;

    @Enumerated(EnumType.STRING)
    @Column(name = "commento")
    private Feedback commento;

    @Size(max = 50)
    @Column(name = "cond_climatiche", length = 50)
    private String condClimatiche;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("prova1500ms")
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

    public Prova1500m dataProva(LocalDate dataProva) {
        this.dataProva = dataProva;
        return this;
    }

    public void setDataProva(LocalDate dataProva) {
        this.dataProva = dataProva;
    }

    public Float getTempo() {
        return tempo;
    }

    public Prova1500m tempo(Float tempo) {
        this.tempo = tempo;
        return this;
    }

    public void setTempo(Float tempo) {
        this.tempo = tempo;
    }

    public Float getTempoKm() {
        return tempoKm;
    }

    public Prova1500m tempoKm(Float tempoKm) {
        this.tempoKm = tempoKm;
        return this;
    }

    public void setTempoKm(Float tempoKm) {
        this.tempoKm = tempoKm;
    }

    public Feedback getCommento() {
        return commento;
    }

    public Prova1500m commento(Feedback commento) {
        this.commento = commento;
        return this;
    }

    public void setCommento(Feedback commento) {
        this.commento = commento;
    }

    public String getCondClimatiche() {
        return condClimatiche;
    }

    public Prova1500m condClimatiche(String condClimatiche) {
        this.condClimatiche = condClimatiche;
        return this;
    }

    public void setCondClimatiche(String condClimatiche) {
        this.condClimatiche = condClimatiche;
    }

    public Calciatore getCalciatore() {
        return calciatore;
    }

    public Prova1500m calciatore(Calciatore calciatore) {
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
        if (!(o instanceof Prova1500m)) {
            return false;
        }
        return id != null && id.equals(((Prova1500m) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Prova1500m{" +
            "id=" + getId() +
            ", dataProva='" + getDataProva() + "'" +
            ", tempo=" + getTempo() +
            ", tempoKm=" + getTempoKm() +
            ", commento='" + getCommento() + "'" +
            ", condClimatiche='" + getCondClimatiche() + "'" +
            "}";
    }
}
