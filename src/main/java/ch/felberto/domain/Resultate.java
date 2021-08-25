package ch.felberto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

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

    @NotNull
    @Column(name = "runde", nullable = false)
    private Integer runde;

    @OneToOne
    @JoinColumn(unique = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Passen passe1;

    @OneToOne
    @JoinColumn(unique = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Passen passe2;

    @OneToOne
    @JoinColumn(unique = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Passen passe3;

    @OneToOne
    @JoinColumn(unique = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Passen passe4;

    @ManyToOne
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

    public Integer getRunde() {
        return this.runde;
    }

    public Resultate runde(Integer runde) {
        this.runde = runde;
        return this;
    }

    public void setRunde(Integer runde) {
        this.runde = runde;
    }

    public Passen getPasse1() {
        return this.passe1;
    }

    public Resultate passe1(Passen passen) {
        this.setPasse1(passen);
        return this;
    }

    public void setPasse1(Passen passen) {
        this.passe1 = passen;
    }

    public Passen getPasse2() {
        return this.passe2;
    }

    public Resultate passe2(Passen passen) {
        this.setPasse2(passen);
        return this;
    }

    public void setPasse2(Passen passen) {
        this.passe2 = passen;
    }

    public Passen getPasse3() {
        return this.passe3;
    }

    public Resultate passe3(Passen passen) {
        this.setPasse3(passen);
        return this;
    }

    public void setPasse3(Passen passen) {
        this.passe3 = passen;
    }

    public Passen getPasse4() {
        return this.passe4;
    }

    public Resultate passe4(Passen passen) {
        this.setPasse4(passen);
        return this;
    }

    public void setPasse4(Passen passen) {
        this.passe4 = passen;
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
