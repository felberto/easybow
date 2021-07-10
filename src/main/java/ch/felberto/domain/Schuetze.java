package ch.felberto.domain;

import ch.felberto.domain.enumeration.Stellung;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Schuetze.
 */
@Entity
@Table(name = "schuetze")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Schuetze implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "vorname")
    private String vorname;

    @Column(name = "jahrgang")
    private LocalDate jahrgang;

    @Enumerated(EnumType.STRING)
    @Column(name = "stellung")
    private Stellung stellung;

    @JsonIgnoreProperties(value = { "verband" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Verein verein;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Schuetze id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Schuetze name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return this.vorname;
    }

    public Schuetze vorname(String vorname) {
        this.vorname = vorname;
        return this;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public LocalDate getJahrgang() {
        return this.jahrgang;
    }

    public Schuetze jahrgang(LocalDate jahrgang) {
        this.jahrgang = jahrgang;
        return this;
    }

    public void setJahrgang(LocalDate jahrgang) {
        this.jahrgang = jahrgang;
    }

    public Stellung getStellung() {
        return this.stellung;
    }

    public Schuetze stellung(Stellung stellung) {
        this.stellung = stellung;
        return this;
    }

    public void setStellung(Stellung stellung) {
        this.stellung = stellung;
    }

    public Verein getVerein() {
        return this.verein;
    }

    public Schuetze verein(Verein verein) {
        this.setVerein(verein);
        return this;
    }

    public void setVerein(Verein verein) {
        this.verein = verein;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schuetze)) {
            return false;
        }
        return id != null && id.equals(((Schuetze) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Schuetze{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", vorname='" + getVorname() + "'" +
            ", jahrgang='" + getJahrgang() + "'" +
            ", stellung='" + getStellung() + "'" +
            "}";
    }
}
