package ch.felberto.domain;

import ch.felberto.domain.enumeration.RankingCriteria;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ranking.
 */
@Entity
@Table(name = "ranking")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ranking implements Serializable {

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
    @Column(name = "ranking_criteria", nullable = false)
    private RankingCriteria rankingCriteria;

    @ManyToOne
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ranking id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getPosition() {
        return this.position;
    }

    public Ranking position(Integer position) {
        this.position = position;
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public RankingCriteria getRankingCriteria() {
        return this.rankingCriteria;
    }

    public Ranking rankingCriteria(RankingCriteria rankingCriteria) {
        this.rankingCriteria = rankingCriteria;
        return this;
    }

    public void setRankingCriteria(RankingCriteria rankingCriteria) {
        this.rankingCriteria = rankingCriteria;
    }

    public Competition getCompetition() {
        return this.competition;
    }

    public Ranking competition(Competition competition) {
        this.setCompetition(competition);
        return this;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ranking)) {
            return false;
        }
        return id != null && id.equals(((Ranking) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ranking{" +
                "id=" + getId() +
                ", position=" + getPosition() +
                ", rankingCriteria='" + getRankingCriteria() + "'" +
                "}";
    }
}
