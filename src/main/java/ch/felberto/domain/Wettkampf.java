package ch.felberto.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Wettkampf.
 */
@Entity
@Table(name = "wettkampf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Wettkampf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jahr")
    private LocalDate jahr;

    @Column(name = "anzahl_runden")
    private Integer anzahlRunden;

    @Column(name = "final_runde")
    private Boolean finalRunde;

    @Min(value = 1)
    @Max(value = 4)
    @Column(name = "anzahl_passen")
    private Integer anzahlPassen;

    @Min(value = 1)
    @Max(value = 4)
    @Column(name = "anzahl_passen_final")
    private Integer anzahlPassenFinal;

    @Column(name = "team")
    private Integer team;

    @Column(name = "template")
    private Boolean template;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wettkampf id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Wettkampf name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getJahr() {
        return this.jahr;
    }

    public Wettkampf jahr(LocalDate jahr) {
        this.jahr = jahr;
        return this;
    }

    public void setJahr(LocalDate jahr) {
        this.jahr = jahr;
    }

    public Integer getAnzahlRunden() {
        return this.anzahlRunden;
    }

    public Wettkampf anzahlRunden(Integer anzahlRunden) {
        this.anzahlRunden = anzahlRunden;
        return this;
    }

    public void setAnzahlRunden(Integer anzahlRunden) {
        this.anzahlRunden = anzahlRunden;
    }

    public Boolean getFinalRunde() {
        return this.finalRunde;
    }

    public Wettkampf finalRunde(Boolean finalRunde) {
        this.finalRunde = finalRunde;
        return this;
    }

    public void setFinalRunde(Boolean finalRunde) {
        this.finalRunde = finalRunde;
    }

    public Integer getAnzahlPassen() {
        return this.anzahlPassen;
    }

    public Wettkampf anzahlPassen(Integer anzahlPassen) {
        this.anzahlPassen = anzahlPassen;
        return this;
    }

    public void setAnzahlPassen(Integer anzahlPassen) {
        this.anzahlPassen = anzahlPassen;
    }

    public Integer getAnzahlPassenFinal() {
        return this.anzahlPassenFinal;
    }

    public Wettkampf anzahlPassenFinal(Integer anzahlPassenFinal) {
        this.anzahlPassenFinal = anzahlPassenFinal;
        return this;
    }

    public void setAnzahlPassenFinal(Integer anzahlPassenFinal) {
        this.anzahlPassenFinal = anzahlPassenFinal;
    }

    public Integer getTeam() {
        return this.team;
    }

    public Wettkampf team(Integer team) {
        this.team = team;
        return this;
    }

    public void setTeam(Integer team) {
        this.team = team;
    }

    public Boolean getTemplate() {
        return this.template;
    }

    public Wettkampf template(Boolean template) {
        this.template = template;
        return this;
    }

    public void setTemplate(Boolean template) {
        this.template = template;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wettkampf)) {
            return false;
        }
        return id != null && id.equals(((Wettkampf) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wettkampf{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", jahr='" + getJahr() + "'" +
            ", anzahlRunden=" + getAnzahlRunden() +
            ", finalRunde='" + getFinalRunde() + "'" +
            ", anzahlPassen=" + getAnzahlPassen() +
            ", anzahlPassenFinal=" + getAnzahlPassenFinal() +
            ", team=" + getTeam() +
            ", template='" + getTemplate() + "'" +
            "}";
    }
}
