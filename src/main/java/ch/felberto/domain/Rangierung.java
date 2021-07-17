package ch.felberto.domain;

import ch.felberto.domain.enumeration.Rangierungskriterien;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rangierung.
 */
@Entity
@Table(name = "rangierung")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rangierung implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "position", nullable = false)
    private Integer position;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rangierungskriterien", nullable = false)
    private Rangierungskriterien rangierungskriterien;

    @ManyToOne
    private Wettkampf wettkampf;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rangierung id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getPosition() {
        return this.position;
    }

    public Rangierung position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Rangierungskriterien getRangierungskriterien() {
        return this.rangierungskriterien;
    }

    public Rangierung rangierungskriterien(Rangierungskriterien rangierungskriterien) {
        this.rangierungskriterien = rangierungskriterien;
        return this;
    }

    public void setRangierungskriterien(Rangierungskriterien rangierungskriterien) {
        this.rangierungskriterien = rangierungskriterien;
    }

    public Wettkampf getWettkampf() {
        return this.wettkampf;
    }

    public Rangierung wettkampf(Wettkampf wettkampf) {
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
        if (!(o instanceof Rangierung)) {
            return false;
        }
        return id != null && id.equals(((Rangierung) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rangierung{" +
            "id=" + getId() +
            ", position=" + getPosition() +
            ", rangierungskriterien='" + getRangierungskriterien() + "'" +
            "}";
    }
}
