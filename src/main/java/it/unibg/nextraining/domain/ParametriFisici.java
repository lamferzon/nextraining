package it.unibg.nextraining.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import it.unibg.nextraining.domain.enumeration.Stato;

/**
 * A ParametriFisici.
 */
@Entity
@Table(name = "parametri_fisici")
public class ParametriFisici implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_rivelazione", nullable = false)
    private LocalDate dataRivelazione;

    @NotNull
    @Max(value = 150)
    @Column(name = "massa_corporea", nullable = false)
    private Integer massaCorporea;

    @NotNull
    @Max(value = 250)
    @Column(name = "altezza", nullable = false)
    private Integer altezza;

    @DecimalMax(value = "50")
    @Column(name = "bmi")
    private Float bmi;

    @Enumerated(EnumType.STRING)
    @Column(name = "condizione")
    private Stato condizione;

    @Max(value = 150)
    @Column(name = "fc_riposo")
    private Integer fcRiposo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("parametriFisicis")
    private Calciatore calciatore;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataRivelazione() {
        return dataRivelazione;
    }

    public ParametriFisici dataRivelazione(LocalDate dataRivelazione) {
        this.dataRivelazione = dataRivelazione;
        return this;
    }

    public void setDataRivelazione(LocalDate dataRivelazione) {
        this.dataRivelazione = dataRivelazione;
    }

    public Integer getMassaCorporea() {
        return massaCorporea;
    }

    public ParametriFisici massaCorporea(Integer massaCorporea) {
        this.massaCorporea = massaCorporea;
        return this;
    }

    public void setMassaCorporea(Integer massaCorporea) {
        this.massaCorporea = massaCorporea;
    }

    public Integer getAltezza() {
        return altezza;
    }

    public ParametriFisici altezza(Integer altezza) {
        this.altezza = altezza;
        return this;
    }

    public void setAltezza(Integer altezza) {
        this.altezza = altezza;
    }

    public Float getBmi() {
        return bmi;
    }

    public ParametriFisici bmi(Float bmi) {
        this.bmi = bmi;
        return this;
    }

    public void setBmi(Float bmi) {
        this.bmi = bmi;
    }

    public Stato getCondizione() {
        return condizione;
    }

    public ParametriFisici condizione(Stato condizione) {
        this.condizione = condizione;
        return this;
    }

    public void setCondizione(Stato condizione) {
        this.condizione = condizione;
    }

    public Integer getFcRiposo() {
        return fcRiposo;
    }

    public ParametriFisici fcRiposo(Integer fcRiposo) {
        this.fcRiposo = fcRiposo;
        return this;
    }

    public void setFcRiposo(Integer fcRiposo) {
        this.fcRiposo = fcRiposo;
    }

    public Calciatore getCalciatore() {
        return calciatore;
    }

    public ParametriFisici calciatore(Calciatore calciatore) {
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
        if (!(o instanceof ParametriFisici)) {
            return false;
        }
        return id != null && id.equals(((ParametriFisici) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ParametriFisici{" +
            "id=" + getId() +
            ", dataRivelazione='" + getDataRivelazione() + "'" +
            ", massaCorporea=" + getMassaCorporea() +
            ", altezza=" + getAltezza() +
            ", bmi=" + getBmi() +
            ", condizione='" + getCondizione() + "'" +
            ", fcRiposo=" + getFcRiposo() +
            "}";
    }
}
