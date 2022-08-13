package ch.felberto.service.criteria;

import ch.felberto.web.rest.RoundResource;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;

/**
 * Criteria class for the {@link ch.felberto.domain.Round} entity. This class is used
 * in {@link RoundResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rounds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RoundCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter round;

    private LocalDateFilter date;

    private LongFilter competitionId;

    public RoundCriteria() {}

    public RoundCriteria(RoundCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.round = other.round == null ? null : other.round.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.competitionId = other.competitionId == null ? null : other.competitionId.copy();
    }

    @Override
    public RoundCriteria copy() {
        return new RoundCriteria(this);
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

    public IntegerFilter getRound() {
        return round;
    }

    public IntegerFilter round() {
        if (round == null) {
            round = new IntegerFilter();
        }
        return round;
    }

    public void setRound(IntegerFilter round) {
        this.round = round;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public LocalDateFilter date() {
        if (date == null) {
            date = new LocalDateFilter();
        }
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public LongFilter getCompetitionId() {
        return competitionId;
    }

    public LongFilter competitionId() {
        if (competitionId == null) {
            competitionId = new LongFilter();
        }
        return competitionId;
    }

    public void setCompetitionId(LongFilter competitionId) {
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
        final RoundCriteria that = (RoundCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(round, that.round) &&
            Objects.equals(date, that.date) &&
            Objects.equals(competitionId, that.competitionId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, round, date, competitionId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoundCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (round != null ? "round=" + round + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (competitionId != null ? "competitionId=" + competitionId + ", " : "") +
            "}";
    }
}
