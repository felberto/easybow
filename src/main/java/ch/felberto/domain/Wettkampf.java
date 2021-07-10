package ch.felberto.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
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

    @Column(name = "anzahl_passen")
    private Long anzahlPassen;

    @Column(name = "team")
    private Long team;

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

    public Long getAnzahlPassen() {
        return this.anzahlPassen;
    }

    public Wettkampf anzahlPassen(Long anzahlPassen) {
        this.anzahlPassen = anzahlPassen;
        return this;
    }

    public void setAnzahlPassen(Long anzahlPassen) {
        this.anzahlPassen = anzahlPassen;
    }

    public Long getTeam() {
        return this.team;
    }

    public Wettkampf team(Long team) {
        this.team = team;
        return this;
    }

    public void setTeam(Long team) {
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
            ", anzahlPassen=" + getAnzahlPassen() +
            ", team=" + getTeam() +
            ", template='" + getTemplate() + "'" +
            "}";
    }
}
