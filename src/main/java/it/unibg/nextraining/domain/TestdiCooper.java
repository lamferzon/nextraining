package it.unibg.nextraining.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import it.unibg.nextraining.domain.enumeration.Feedback;

/**
 * A TestdiCooper.
 */
@Entity
@Table(name = "testdi_cooper")
public class TestdiCooper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_test", nullable = false)
    private LocalDate dataTest;

    @NotNull
    @DecimalMax(value = "10")
    @Column(name = "distanza", nullable = false)
    private Float distanza;

    @Max(value = 120)
    @Column(name = "v_02_max")
    private Integer v02Max;

    @Enumerated(EnumType.STRING)
    @Column(name = "commento")
    private Feedback commento;

    @Size(max = 50)
    @Column(name = "cond_climatiche", length = 50)
    private String condClimatiche;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("testdiCoopers")
    private Calciatore calciatore;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataTest() {
        return dataTest;
    }

    public TestdiCooper dataTest(LocalDate dataTest) {
        this.dataTest = dataTest;
        return this;
    }

    public void setDataTest(LocalDate dataTest) {
        this.dataTest = dataTest;
    }

    public Float getDistanza() {
        return distanza;
    }

    public TestdiCooper distanza(Float distanza) {
        this.distanza = distanza;
        return this;
    }

    public void setDistanza(Float distanza) {
        this.distanza = distanza;
    }

    public Integer getv02Max() {
        return v02Max;
    }

    public TestdiCooper v02Max(Integer v02Max) {
        this.v02Max = v02Max;
        return this;
    }

    public void setv02Max(Integer v02Max) {
        this.v02Max = v02Max;
    }

    public Feedback getCommento() {
        return commento;
    }

    public TestdiCooper commento(Feedback commento) {
        this.commento = commento;
        return this;
    }

    public void setCommento(Feedback commento) {
        this.commento = commento;
    }

    public String getCondClimatiche() {
        return condClimatiche;
    }

    public TestdiCooper condClimatiche(String condClimatiche) {
        this.condClimatiche = condClimatiche;
        return this;
    }

    public void setCondClimatiche(String condClimatiche) {
        this.condClimatiche = condClimatiche;
    }

    public Calciatore getCalciatore() {
        return calciatore;
    }

    public TestdiCooper calciatore(Calciatore calciatore) {
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
        if (!(o instanceof TestdiCooper)) {
            return false;
        }
        return id != null && id.equals(((TestdiCooper) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TestdiCooper{" +
            "id=" + getId() +
            ", dataTest='" + getDataTest() + "'" +
            ", distanza=" + getDistanza() +
            ", v02Max=" + getv02Max() +
            ", commento='" + getCommento() + "'" +
            ", condClimatiche='" + getCondClimatiche() + "'" +
            "}";
    }
}
