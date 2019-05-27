package it.unibg.nextraining.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import it.unibg.nextraining.domain.enumeration.Natura;

/**
 * A Allenamento.
 */
@Entity
@Table(name = "allenamento")
public class Allenamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_svolgimento", nullable = false)
    private LocalDate dataSvolgimento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "natura", nullable = false)
    private Natura natura;

    @Column(name = "lavoro")
    private String lavoro;

    @ManyToMany
    @NotNull
    @JoinTable(name = "allenamento_calciatore",
               joinColumns = @JoinColumn(name = "allenamento_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "calciatore_id", referencedColumnName = "id"))
    private Set<Calciatore> calciatores = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataSvolgimento() {
        return dataSvolgimento;
    }

    public Allenamento dataSvolgimento(LocalDate dataSvolgimento) {
        this.dataSvolgimento = dataSvolgimento;
        return this;
    }

    public void setDataSvolgimento(LocalDate dataSvolgimento) {
        this.dataSvolgimento = dataSvolgimento;
    }

    public Natura getNatura() {
        return natura;
    }

    public Allenamento natura(Natura natura) {
        this.natura = natura;
        return this;
    }

    public void setNatura(Natura natura) {
        this.natura = natura;
    }

    public String getLavoro() {
        return lavoro;
    }

    public Allenamento lavoro(String lavoro) {
        this.lavoro = lavoro;
        return this;
    }

    public void setLavoro(String lavoro) {
        this.lavoro = lavoro;
    }

    public Set<Calciatore> getCalciatores() {
        return calciatores;
    }

    public Allenamento calciatores(Set<Calciatore> calciatores) {
        this.calciatores = calciatores;
        return this;
    }

    public Allenamento addCalciatore(Calciatore calciatore) {
        this.calciatores.add(calciatore);
        calciatore.getAllenamentos().add(this);
        return this;
    }

    public Allenamento removeCalciatore(Calciatore calciatore) {
        this.calciatores.remove(calciatore);
        calciatore.getAllenamentos().remove(this);
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
        if (!(o instanceof Allenamento)) {
            return false;
        }
        return id != null && id.equals(((Allenamento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Allenamento{" +
            "id=" + getId() +
            ", dataSvolgimento='" + getDataSvolgimento() + "'" +
            ", natura='" + getNatura() + "'" +
            ", lavoro='" + getLavoro() + "'" +
            "}";
    }
}
