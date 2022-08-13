package ch.felberto.service.criteria;

import ch.felberto.domain.Ranking;
import ch.felberto.web.rest.RankingResource;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;

/**
 * Criteria class for the {@link Ranking} entity. This class is used
 * in {@link RankingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rangierungs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RankingCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Rangierungskriterien
     */
    public static class RankingCriteriaFilter extends Filter<ch.felberto.domain.enumeration.RankingCriteria> {

        public RankingCriteriaFilter() {}

        public RankingCriteriaFilter(RankingCriteriaFilter filter) {
            super(filter);
        }

        @Override
        public RankingCriteriaFilter copy() {
            return new RankingCriteriaFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter position;

    private RankingCriteriaFilter rankingCriteriaFilter;

    private LongFilter competitionId;

    public RankingCriteria() {}

    public RankingCriteria(RankingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.rankingCriteriaFilter = other.rankingCriteriaFilter == null ? null : other.rankingCriteriaFilter.copy();
        this.competitionId = other.competitionId == null ? null : other.competitionId.copy();
    }

    @Override
    public RankingCriteria copy() {
        return new RankingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getPosition() {
        return position;
    }

    public IntegerFilter position() {
        if (position == null) {
            position = new IntegerFilter();
        }
        return position;
    }

    public void setPosition(IntegerFilter position) {
        this.position = position;
    }

    public RankingCriteriaFilter getRankingCriteriaFilter() {
        return rankingCriteriaFilter;
    }

    public RankingCriteriaFilter rankingCriteriaFilter() {
        if (rankingCriteriaFilter == null) {
            rankingCriteriaFilter = new RankingCriteriaFilter();
        }
        return rankingCriteriaFilter;
    }

    public void setRankingCriteriaFilter(RankingCriteriaFilter rankingCriteriaFilter) {
        this.rankingCriteriaFilter = rankingCriteriaFilter;
    }

    public LongFilter getWettkampfId() {
        return competitionId;
    }

    public LongFilter competitionId() {
        if (competitionId == null) {
            competitionId = new LongFilter();
        }
        return competitionId;
    }

    public void setWettkampfId(LongFilter competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RankingCriteria that = (RankingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(position, that.position) &&
            Objects.equals(rankingCriteriaFilter, that.rankingCriteriaFilter) &&
            Objects.equals(competitionId, that.competitionId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, position, rankingCriteriaFilter, competitionId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RankingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (position != null ? "position=" + position + ", " : "") +
                (rankingCriteriaFilter != null ? "rankingCriteriaFilter=" + rankingCriteriaFilter + ", " : "") +
                (competitionId != null ? "competitionId=" + competitionId + ", " : "") +
                "}";
    }
}
