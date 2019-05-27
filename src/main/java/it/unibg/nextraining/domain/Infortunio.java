package it.unibg.nextraining.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import it.unibg.nextraining.domain.enumeration.Gravita;

/**
 * A Infortunio.
 */
@Entity
@Table(name = "infortunio")
public class Infortunio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_inizio", nullable = false)
    private LocalDate dataInizio;

    @Column(name = "data_fine")
    private LocalDate dataFine;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gravita", nullable = false)
    private Gravita gravita;

    @Size(max = 50)
    @Column(name = "descrizione", length = 50)
    private String descrizione;

    @ManyToOne
    @JsonIgnoreProperties("infortunios")
    private Specialista specialista;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("infortunios")
    private Calciatore calciatore;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public Infortunio dataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
        return this;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public Infortunio dataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
        return this;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public Gravita getGravita() {
        return gravita;
    }

    public Infortunio gravita(Gravita gravita) {
        this.gravita = gravita;
        return this;
    }

    public void setGravita(Gravita gravita) {
        this.gravita = gravita;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Infortunio descrizione(String descrizione) {
        this.descrizione = descrizione;
        return this;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Specialista getSpecialista() {
        return specialista;
    }

    public Infortunio specialista(Specialista specialista) {
        this.specialista = specialista;
        return this;
    }

    public void setSpecialista(Specialista specialista) {
        this.specialista = specialista;
    }

    public Calciatore getCalciatore() {
        return calciatore;
    }

    public Infortunio calciatore(Calciatore calciatore) {
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
        if (!(o instanceof Infortunio)) {
            return false;
        }
        return id != null && id.equals(((Infortunio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Infortunio{" +
            "id=" + getId() +
            ", dataInizio='" + getDataInizio() + "'" +
            ", dataFine='" + getDataFine() + "'" +
            ", gravita='" + getGravita() + "'" +
            ", descrizione='" + getDescrizione() + "'" +
            "}";
    }
}
