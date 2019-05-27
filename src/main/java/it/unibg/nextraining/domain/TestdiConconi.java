package it.unibg.nextraining.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import it.unibg.nextraining.domain.enumeration.Feedback;

/**
 * A TestdiConconi.
 */
@Entity
@Table(name = "testdi_conconi")
public class TestdiConconi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Max(value = 220)
    @Column(name = "fc_max", nullable = false)
    private Integer fcMax;

    @NotNull
    @Max(value = 220)
    @Column(name = "soglia_anaerobica", nullable = false)
    private Integer sogliaAnaerobica;

    @DecimalMax(value = "100")
    @Column(name = "vel_soglia")
    private Float velSoglia;

    @NotNull
    @DecimalMax(value = "60")
    @Column(name = "durata", nullable = false)
    private Float durata;

    @Enumerated(EnumType.STRING)
    @Column(name = "commento")
    private Feedback commento;

    @Size(max = 50)
    @Column(name = "cond_climatiche", length = 50)
    private String condClimatiche;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private Calciatore calciatore;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFcMax() {
        return fcMax;
    }

    public TestdiConconi fcMax(Integer fcMax) {
        this.fcMax = fcMax;
        return this;
    }

    public void setFcMax(Integer fcMax) {
        this.fcMax = fcMax;
    }

    public Integer getSogliaAnaerobica() {
        return sogliaAnaerobica;
    }

    public TestdiConconi sogliaAnaerobica(Integer sogliaAnaerobica) {
        this.sogliaAnaerobica = sogliaAnaerobica;
        return this;
    }

    public void setSogliaAnaerobica(Integer sogliaAnaerobica) {
        this.sogliaAnaerobica = sogliaAnaerobica;
    }

    public Float getVelSoglia() {
        return velSoglia;
    }

    public TestdiConconi velSoglia(Float velSoglia) {
        this.velSoglia = velSoglia;
        return this;
    }

    public void setVelSoglia(Float velSoglia) {
        this.velSoglia = velSoglia;
    }

    public Float getDurata() {
        return durata;
    }

    public TestdiConconi durata(Float durata) {
        this.durata = durata;
        return this;
    }

    public void setDurata(Float durata) {
        this.durata = durata;
    }

    public Feedback getCommento() {
        return commento;
    }

    public TestdiConconi commento(Feedback commento) {
        this.commento = commento;
        return this;
    }

    public void setCommento(Feedback commento) {
        this.commento = commento;
    }

    public String getCondClimatiche() {
        return condClimatiche;
    }

    public TestdiConconi condClimatiche(String condClimatiche) {
        this.condClimatiche = condClimatiche;
        return this;
    }

    public void setCondClimatiche(String condClimatiche) {
        this.condClimatiche = condClimatiche;
    }

    public Calciatore getCalciatore() {
        return calciatore;
    }

    public TestdiConconi calciatore(Calciatore calciatore) {
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
        if (!(o instanceof TestdiConconi)) {
            return false;
        }
        return id != null && id.equals(((TestdiConconi) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TestdiConconi{" +
            "id=" + getId() +
            ", fcMax=" + getFcMax() +
            ", sogliaAnaerobica=" + getSogliaAnaerobica() +
            ", velSoglia=" + getVelSoglia() +
            ", durata=" + getDurata() +
            ", commento='" + getCommento() + "'" +
            ", condClimatiche='" + getCondClimatiche() + "'" +
            "}";
    }
}
