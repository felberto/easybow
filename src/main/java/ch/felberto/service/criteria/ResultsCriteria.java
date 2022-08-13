package ch.felberto.service.criteria;

import ch.felberto.domain.Results;
import ch.felberto.web.rest.ResultsResource;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;

/**
 * Criteria class for the {@link Results} entity. This class is used
 * in {@link ResultsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /results?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResultsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter round;

    private IntegerFilter result;

    private LongFilter serie1Id;

    private LongFilter serie2Id;

    private LongFilter serie3Id;

    private LongFilter serie4Id;

    private LongFilter groupId;

    private LongFilter athleteId;

    private LongFilter competitionId;

    public ResultsCriteria() {}

    public ResultsCriteria(ResultsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.round = other.round == null ? null : other.round.copy();
        this.result = other.result == null ? null : other.result.copy();
        this.serie1Id = other.serie1Id == null ? null : other.serie1Id.copy();
        this.serie2Id = other.serie2Id == null ? null : other.serie2Id.copy();
        this.serie3Id = other.serie3Id == null ? null : other.serie3Id.copy();
        this.serie4Id = other.serie4Id == null ? null : other.serie4Id.copy();
        this.groupId = other.groupId == null ? null : other.groupId.copy();
        this.athleteId = other.athleteId == null ? null : other.athleteId.copy();
        this.competitionId = other.competitionId == null ? null : other.competitionId.copy();
    }

    @Override
    public ResultsCriteria copy() {
        return new ResultsCriteria(this);
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

    public IntegerFilter getResult() {
        return result;
    }

    public IntegerFilter result() {
        if (result == null) {
            result = new IntegerFilter();
        }
        return result;
    }

    public void setResult(IntegerFilter result) {
        this.result = result;
    }

    public LongFilter getSerie1Id() {
        return serie1Id;
    }

    public LongFilter serie1Id() {
        if (serie1Id == null) {
            serie1Id = new LongFilter();
        }
        return serie1Id;
    }

    public void setSerie1Id(LongFilter serie1Id) {
        this.serie1Id = serie1Id;
    }

    public LongFilter getSerie2Id() {
        return serie2Id;
    }

    public LongFilter serie2Id() {
        if (serie2Id == null) {
            serie2Id = new LongFilter();
        }
        return serie2Id;
    }

    public void setSerie2Id(LongFilter serie2Id) {
        this.serie2Id = serie2Id;
    }

    public LongFilter getSerie3Id() {
        return serie3Id;
    }

    public LongFilter serie3Id() {
        if (serie3Id == null) {
            serie3Id = new LongFilter();
        }
        return serie3Id;
    }

    public void setSerie3Id(LongFilter serie3Id) {
        this.serie3Id = serie3Id;
    }

    public LongFilter getSerie4Id() {
        return serie4Id;
    }

    public LongFilter serie4Id() {
        if (serie4Id == null) {
            serie4Id = new LongFilter();
        }
        return serie4Id;
    }

    public void setSerie4Id(LongFilter serie4Id) {
        this.serie4Id = serie4Id;
    }

    public LongFilter getGroupId() {
        return groupId;
    }

    public LongFilter groupId() {
        if (groupId == null) {
            groupId = new LongFilter();
        }
        return groupId;
    }

    public void setGroupId(LongFilter groupId) {
        this.groupId = groupId;
    }

    public LongFilter getAthleteId() {
        return athleteId;
    }

    public LongFilter athleteId() {
        if (athleteId == null) {
            athleteId = new LongFilter();
        }
        return athleteId;
    }

    public void setAthleteId(LongFilter athleteId) {
        this.athleteId = athleteId;
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
        final ResultsCriteria that = (ResultsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(round, that.round) &&
            Objects.equals(result, that.result) &&
            Objects.equals(serie1Id, that.serie1Id) &&
            Objects.equals(serie2Id, that.serie2Id) &&
            Objects.equals(serie3Id, that.serie3Id) &&
            Objects.equals(serie4Id, that.serie4Id) &&
            Objects.equals(groupId, that.groupId) &&
            Objects.equals(athleteId, that.athleteId) &&
            Objects.equals(competitionId, that.competitionId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, round, result, serie1Id, serie2Id, serie3Id, serie4Id, groupId, athleteId, competitionId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (round != null ? "round=" + round + ", " : "") +
                (result != null ? "result=" + result + ", " : "") +
                (serie1Id != null ? "serie1Id=" + serie1Id + ", " : "") +
                (serie2Id != null ? "serie2Id=" + serie2Id + ", " : "") +
                (serie3Id != null ? "serie3Id=" + serie3Id + ", " : "") +
                (serie4Id != null ? "serie4Id=" + serie4Id + ", " : "") +
                (groupId != null ? "groupId=" + groupId + ", " : "") +
                (athleteId != null ? "athleteId=" + athleteId + ", " : "") +
                (competitionId != null ? "competitionId=" + competitionId + ", " : "") +
                "}";
    }
}
