package it.unibg.nextraining.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import it.unibg.nextraining.domain.enumeration.Reparto;

import it.unibg.nextraining.domain.enumeration.Ruolo;

import it.unibg.nextraining.domain.enumeration.Selettore;

/**
 * A Calciatore.
 */
@Entity
@Table(name = "calciatore")
public class Calciatore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 16)
    @Column(name = "cod_fiscale", length = 16, unique = true)
    private String codFiscale;

    @NotNull
    @Size(max = 50)
    @Column(name = "cognome", length = 50, nullable = false)
    private String cognome;

    @NotNull
    @Size(max = 50)
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Column(name = "data_nascita")
    private LocalDate dataNascita;

    @NotNull
    @Size(max = 20)
    @Column(name = "num_telefono", length = 20, nullable = false)
    private String numTelefono;

    @Size(max = 50)
    @Column(name = "email", length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "reparto")
    private Reparto reparto;

    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo")
    private Ruolo ruolo;

    @Enumerated(EnumType.STRING)
    @Column(name = "selettore")
    private Selettore selettore;

    @ManyToMany(mappedBy = "calciatores")
    @JsonIgnore
    private Set<Allenamento> allenamentos = new HashSet<>();

    @ManyToMany(mappedBy = "calciatores")
    @JsonIgnore
    private Set<AllenamentoAggiuntivo> allenamentoExtras = new HashSet<>();
    
    @OneToMany(mappedBy = "calciatore", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Prova70m> prova70list = new HashSet<>();
    
    @OneToMany(mappedBy = "calciatore", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Prova1500m> prova1500list = new HashSet<>();
    
	@OneToMany(mappedBy = "calciatore", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<TestdiCooper> testdiCooperlist = new HashSet<>();
    
    @OneToMany(mappedBy = "calciatore", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<ParametriFisici> parametriFisicilist = new HashSet<>();
    
    @OneToMany(mappedBy = "calciatore", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Infortunio> infortuniolist = new HashSet<>();
    
    @OneToOne(mappedBy = "calciatore", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private TestdiConconi testdiConconilist;
    
    public TestdiConconi getTestdiConconilist() {
		return testdiConconilist;
	}

	public void setTestdiConconilist(TestdiConconi testdiConconilist) {
		this.testdiConconilist = testdiConconilist;
	}

	public Set<TestdiCooper> getTestdiCooperlist() {
  		return testdiCooperlist;
  	}

  	public void setTestdiCooperlist(Set<TestdiCooper> testdiCooperlist) {
  		this.testdiCooperlist = testdiCooperlist;
  	}

  	public Set<ParametriFisici> getParametriFisicilist() {
  		return parametriFisicilist;
  	}

  	public void setParametriFisicilist(Set<ParametriFisici> parametriFisicilist) {
  		this.parametriFisicilist = parametriFisicilist;
  	}

  	public Set<Infortunio> getInfortuniolist() {
  		return infortuniolist;
  	}

  	public void setInfortuniolist(Set<Infortunio> infortuniolist) {
  		this.infortuniolist = infortuniolist;
  	}

    public Set<Prova1500m> getProva1500list() {
		return prova1500list;
	}

	public void setProva1500list(Set<Prova1500m> prova1500list) {
		this.prova1500list = prova1500list;
	}

	public Set<Prova70m> getProva70list() {
		return prova70list;
	}

	public void setProva70list(Set<Prova70m> prova70list) {
		this.prova70list = prova70list;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public Calciatore codFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
        return this;
    }

    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    public String getCognome() {
        return cognome;
    }

    public Calciatore cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public Calciatore nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public Calciatore dataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
        return this;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public Calciatore numTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
        return this;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public String getEmail() {
        return email;
    }

    public Calciatore email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Reparto getReparto() {
        return reparto;
    }

    public Calciatore reparto(Reparto reparto) {
        this.reparto = reparto;
        return this;
    }

    public void setReparto(Reparto reparto) {
        this.reparto = reparto;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public Calciatore ruolo(Ruolo ruolo) {
        this.ruolo = ruolo;
        return this;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public Selettore getSelettore() {
        return selettore;
    }

    public Calciatore selettore(Selettore selettore) {
        this.selettore = selettore;
        return this;
    }

    public void setSelettore(Selettore selettore) {
        this.selettore = selettore;
    }

    public Set<Allenamento> getAllenamentos() {
        return allenamentos;
    }

    public Calciatore allenamentos(Set<Allenamento> allenamentos) {
        this.allenamentos = allenamentos;
        return this;
    }

    public Calciatore addAllenamento(Allenamento allenamento) {
        this.allenamentos.add(allenamento);
        allenamento.getCalciatores().add(this);
        return this;
    }

    public Calciatore removeAllenamento(Allenamento allenamento) {
        this.allenamentos.remove(allenamento);
        allenamento.getCalciatores().remove(this);
        return this;
    }

    public void setAllenamentos(Set<Allenamento> allenamentos) {
        this.allenamentos = allenamentos;
    }

    public Set<AllenamentoAggiuntivo> getAllenamentoExtras() {
        return allenamentoExtras;
    }

    public Calciatore allenamentoExtras(Set<AllenamentoAggiuntivo> allenamentoAggiuntivos) {
        this.allenamentoExtras = allenamentoAggiuntivos;
        return this;
    }
    
    public Calciatore addAllenamentoExtra(AllenamentoAggiuntivo allenamentoAggiuntivo) {
        this.allenamentoExtras.add(allenamentoAggiuntivo);
        allenamentoAggiuntivo.getCalciatores().add(this);
        return this;
    }

    public Calciatore removeAllenamentoExtra(AllenamentoAggiuntivo allenamentoAggiuntivo) {
        this.allenamentoExtras.remove(allenamentoAggiuntivo);
        allenamentoAggiuntivo.getCalciatores().remove(this);
        return this;
    }

    public void setAllenamentoExtras(Set<AllenamentoAggiuntivo> allenamentoAggiuntivos) {
        this.allenamentoExtras = allenamentoAggiuntivos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Calciatore)) {
            return false;
        }
        return id != null && id.equals(((Calciatore) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Calciatore{" +
            "id=" + getId() +
            ", codFiscale='" + getCodFiscale() + "'" +
            ", cognome='" + getCognome() + "'" +
            ", nome='" + getNome() + "'" +
            ", dataNascita='" + getDataNascita() + "'" +
            ", numTelefono='" + getNumTelefono() + "'" +
            ", email='" + getEmail() + "'" +
            ", reparto='" + getReparto() + "'" +
            ", ruolo='" + getRuolo() + "'" +
            ", selettore='" + getSelettore() + "'" +
            "}";
    }
}
