package ch.felberto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Resultate.
 */
@Entity
@Table(name = "resultate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resultate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "runde")
    private Long runde;

    @OneToOne
    @JoinColumn(unique = true)
    private Passen passe;

    @OneToOne
    @JoinColumn(unique = true)
    private Gruppen gruppe;

    @ManyToOne
    @JsonIgnoreProperties(value = { "verein" }, allowSetters = true)
    private Schuetze schuetze;

    @ManyToOne
    private Wettkampf wettkampf;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Resultate id(Long id) {
        this.id = id;
        return this;
    }

    public Long getRunde() {
        return this.runde;
    }

    public Resultate runde(Long runde) {
        this.runde = runde;
        return this;
    }

    public void setRunde(Long runde) {
        this.runde = runde;
    }

    public Passen getPasse() {
        return this.passe;
    }

    public Resultate passe(Passen passen) {
        this.setPasse(passen);
        return this;
    }

    public void setPasse(Passen passen) {
        this.passe = passen;
    }

    public Gruppen getGruppe() {
        return this.gruppe;
    }

    public Resultate gruppe(Gruppen gruppen) {
        this.setGruppe(gruppen);
        return this;
    }

    public void setGruppe(Gruppen gruppen) {
        this.gruppe = gruppen;
    }

    public Schuetze getSchuetze() {
        return this.schuetze;
    }

    public Resultate schuetze(Schuetze schuetze) {
        this.setSchuetze(schuetze);
        return this;
    }

    public void setSchuetze(Schuetze schuetze) {
        this.schuetze = schuetze;
    }

    public Wettkampf getWettkampf() {
        return this.wettkampf;
    }

    public Resultate wettkampf(Wettkampf wettkampf) {
        this.setWettkampf(wettkampf);
        return this;
    }

    public void setWettkampf(Wettkampf wettkampf) {
        this.wettkampf = wettkampf;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resultate)) {
            return false;
        }
        return id != null && id.equals(((Resultate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resultate{" +
            "id=" + getId() +
            ", runde=" + getRunde() +
            "}";
    }
}
