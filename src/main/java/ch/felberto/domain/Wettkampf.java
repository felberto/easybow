package ch.felberto.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "jahr")
    private Integer jahr;

    @NotNull
    @Column(name = "anzahl_runden", nullable = false)
    private Integer anzahlRunden;

    @NotNull
    @Min(value = 1)
    @Max(value = 4)
    @Column(name = "anzahl_passen", nullable = false)
    private Integer anzahlPassen;

    @Column(name = "final_runde")
    private Boolean finalRunde;

    @Column(name = "final_vorbereitung")
    private Boolean finalVorbereitung;

    @Column(name = "anzahl_finalteilnehmer")
    private Integer anzahlFinalteilnehmer;

    @Min(value = 1)
    @Max(value = 4)
    @Column(name = "anzahl_passen_final")
    private Integer anzahlPassenFinal;

    @Column(name = "anzahl_team")
    private Integer anzahlTeam;

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

    public Integer getJahr() {
        return this.jahr;
    }

    public Wettkampf jahr(Integer jahr) {
        this.jahr = jahr;
        return this;
    }

    public void setJahr(Integer jahr) {
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

    public Boolean getFinalVorbereitung() {
        return this.finalVorbereitung;
    }

    public Wettkampf finalVorbereitung(Boolean finalVorbereitung) {
        this.finalVorbereitung = finalVorbereitung;
        return this;
    }

    public void setFinalVorbereitung(Boolean finalVorbereitung) {
        this.finalVorbereitung = finalVorbereitung;
    }

    public Integer getAnzahlFinalteilnehmer() {
        return this.anzahlFinalteilnehmer;
    }

    public Wettkampf anzahlFinalteilnehmer(Integer anzahlFinalteilnehmer) {
        this.anzahlFinalteilnehmer = anzahlFinalteilnehmer;
        return this;
    }

    public void setAnzahlFinalteilnehmer(Integer anzahlFinalteilnehmer) {
        this.anzahlFinalteilnehmer = anzahlFinalteilnehmer;
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

    public Integer getAnzahlTeam() {
        return this.anzahlTeam;
    }

    public Wettkampf anzahlTeam(Integer anzahlTeam) {
        this.anzahlTeam = anzahlTeam;
        return this;
    }

    public void setAnzahlTeam(Integer anzahlTeam) {
        this.anzahlTeam = anzahlTeam;
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
            ", jahr=" + getJahr() +
            ", anzahlRunden=" + getAnzahlRunden() +
            ", anzahlPassen=" + getAnzahlPassen() +
            ", finalRunde='" + getFinalRunde() + "'" +
            ", finalVorbereitung='" + getFinalVorbereitung() + "'" +
            ", anzahlFinalteilnehmer=" + getAnzahlFinalteilnehmer() +
            ", anzahlPassenFinal=" + getAnzahlPassenFinal() +
            ", anzahlTeam=" + getAnzahlTeam() +
            ", template='" + getTemplate() + "'" +
            "}";
    }
}
