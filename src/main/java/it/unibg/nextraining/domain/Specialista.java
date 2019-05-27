package it.unibg.nextraining.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Specialista.
 */
@Entity
@Table(name = "specialista")
public class Specialista implements Serializable {

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

    @Size(max = 50)
    @Column(name = "specializzazione", length = 50)
    private String specializzazione;

    @NotNull
    @Size(max = 20)
    @Column(name = "num_telefono", length = 20, nullable = false)
    private String numTelefono;

    @Size(max = 40)
    @Column(name = "email", length = 40)
    private String email;

    @Size(max = 50)
    @Column(name = "indirizzo_studio", length = 50)
    private String indirizzoStudio;

    @Size(max = 50)
    @Column(name = "paese_studio", length = 50)
    private String paeseStudio;

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

    public Specialista codFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
        return this;
    }

    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    public String getCognome() {
        return cognome;
    }

    public Specialista cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public Specialista nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSpecializzazione() {
        return specializzazione;
    }

    public Specialista specializzazione(String specializzazione) {
        this.specializzazione = specializzazione;
        return this;
    }

    public void setSpecializzazione(String specializzazione) {
        this.specializzazione = specializzazione;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public Specialista numTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
        return this;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public String getEmail() {
        return email;
    }

    public Specialista email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIndirizzoStudio() {
        return indirizzoStudio;
    }

    public Specialista indirizzoStudio(String indirizzoStudio) {
        this.indirizzoStudio = indirizzoStudio;
        return this;
    }

    public void setIndirizzoStudio(String indirizzoStudio) {
        this.indirizzoStudio = indirizzoStudio;
    }

    public String getPaeseStudio() {
        return paeseStudio;
    }

    public Specialista paeseStudio(String paeseStudio) {
        this.paeseStudio = paeseStudio;
        return this;
    }

    public void setPaeseStudio(String paeseStudio) {
        this.paeseStudio = paeseStudio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Specialista)) {
            return false;
        }
        return id != null && id.equals(((Specialista) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Specialista{" +
            "id=" + getId() +
            ", codFiscale='" + getCodFiscale() + "'" +
            ", cognome='" + getCognome() + "'" +
            ", nome='" + getNome() + "'" +
            ", specializzazione='" + getSpecializzazione() + "'" +
            ", numTelefono='" + getNumTelefono() + "'" +
            ", email='" + getEmail() + "'" +
            ", indirizzoStudio='" + getIndirizzoStudio() + "'" +
            ", paeseStudio='" + getPaeseStudio() + "'" +
            "}";
    }
}
