package ch.felberto.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Runde.
 */
@Entity
@Table(name = "runde")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Runde implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "runde", nullable = false)
    private Integer runde;

    @Column(name = "datum")
    private LocalDate datum;

    @ManyToOne
    private Wettkampf wettkampf;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Runde id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getRunde() {
        return this.runde;
    }

    public Runde runde(Integer runde) {
        this.runde = runde;
        return this;
    }

    public void setRunde(Integer runde) {
        this.runde = runde;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Runde datum(LocalDate datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Wettkampf getWettkampf() {
        return this.wettkampf;
    }

    public Runde wettkampf(Wettkampf wettkampf) {
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
        if (!(o instanceof Runde)) {
            return false;
        }
        return id != null && id.equals(((Runde) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Runde{" +
            "id=" + getId() +
            ", runde=" + getRunde() +
            ", datum='" + getDatum() + "'" +
            "}";
    }
}
