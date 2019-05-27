package it.unibg.nextraining.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import it.unibg.nextraining.domain.enumeration.Natura;

/**
 * A AllenamentoAggiuntivo.
 */
@Entity
@Table(name = "allenamento_aggiuntivo")
public class AllenamentoAggiuntivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "sport", length = 50, nullable = false)
    private String sport;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "natura", nullable = false)
    private Natura natura;

    @Column(name = "lavoro")
    private String lavoro;

    @Column(name = "durata")
    private Float durata;

    @ManyToMany
    @JoinTable(name = "allenamento_aggiuntivo_calciatore",
               joinColumns = @JoinColumn(name = "allenamento_aggiuntivo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "calciatore_id", referencedColumnName = "id"))
    private Set<Calciatore> calciatores = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSport() {
        return sport;
    }

    public AllenamentoAggiuntivo sport(String sport) {
        this.sport = sport;
        return this;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Natura getNatura() {
        return natura;
    }

    public AllenamentoAggiuntivo natura(Natura natura) {
        this.natura = natura;
        return this;
    }

    public void setNatura(Natura natura) {
        this.natura = natura;
    }

    public String getLavoro() {
        return lavoro;
    }

    public AllenamentoAggiuntivo lavoro(String lavoro) {
        this.lavoro = lavoro;
        return this;
    }

    public void setLavoro(String lavoro) {
        this.lavoro = lavoro;
    }

    public Float getDurata() {
        return durata;
    }

    public AllenamentoAggiuntivo durata(Float durata) {
        this.durata = durata;
        return this;
    }

    public void setDurata(Float durata) {
        this.durata = durata;
    }

    public Set<Calciatore> getCalciatores() {
        return calciatores;
    }

    public AllenamentoAggiuntivo calciatores(Set<Calciatore> calciatores) {
        this.calciatores = calciatores;
        return this;
    }

    public AllenamentoAggiuntivo addCalciatore(Calciatore calciatore) {
        this.calciatores.add(calciatore);
        calciatore.getAllenamentoExtras().add(this);
        return this;
    }

    public AllenamentoAggiuntivo removeCalciatore(Calciatore calciatore) {
        this.calciatores.remove(calciatore);
        calciatore.getAllenamentoExtras().remove(this);
        return this;
    }

    public void setCalciatores(Set<Calciatore> calciatores) {
        this.calciatores = calciatores;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AllenamentoAggiuntivo)) {
            return false;
        }
        return id != null && id.equals(((AllenamentoAggiuntivo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AllenamentoAggiuntivo{" +
            "id=" + getId() +
            ", sport='" + getSport() + "'" +
            ", natura='" + getNatura() + "'" +
            ", lavoro='" + getLavoro() + "'" +
            ", durata=" + getDurata() +
            "}";
    }
}
