package ch.felberto.service.criteria;

import ch.felberto.domain.Competition;
import ch.felberto.domain.enumeration.CompetitionType;
import ch.felberto.web.rest.CompetitionResource;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link Competition} entity. This class is used
 * in {@link CompetitionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /competitions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompetitionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    /**
     * Class for filtering CompetitionType
     */
    public static class CompetitionTypeFilter extends Filter<CompetitionType> {

        public CompetitionTypeFilter() {}

        public CompetitionTypeFilter(CompetitionCriteria.CompetitionTypeFilter filter) {
            super(filter);
        }

        @Override
        public CompetitionCriteria.CompetitionTypeFilter copy() {
            return new CompetitionCriteria.CompetitionTypeFilter(this);
        }
    }

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter year;

    private CompetitionTypeFilter competitionType;

    private IntegerFilter numberOfRounds;

    private IntegerFilter numberOfSeries;

    private BooleanFilter finalRound;

    private BooleanFilter finalPreparation;

    private IntegerFilter numberOfFinalAthletes;

    private IntegerFilter numberOfFinalSeries;

    private IntegerFilter teamSize;

    private BooleanFilter template;

    public CompetitionCriteria() {}

    public CompetitionCriteria(CompetitionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.competitionType = other.competitionType == null ? null : other.competitionType.copy();
        this.numberOfRounds = other.numberOfRounds == null ? null : other.numberOfRounds.copy();
        this.numberOfSeries = other.numberOfSeries == null ? null : other.numberOfSeries.copy();
        this.finalRound = other.finalRound == null ? null : other.finalRound.copy();
        this.finalPreparation = other.finalPreparation == null ? null : other.finalPreparation.copy();
        this.numberOfFinalAthletes = other.numberOfFinalAthletes == null ? null : other.numberOfFinalAthletes.copy();
        this.numberOfFinalSeries = other.numberOfFinalSeries == null ? null : other.numberOfFinalSeries.copy();
        this.teamSize = other.teamSize == null ? null : other.teamSize.copy();
        this.template = other.template == null ? null : other.template.copy();
    }

    @Override
    public CompetitionCriteria copy() {
        return new CompetitionCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getYear() {
        return year;
    }

    public IntegerFilter year() {
        if (year == null) {
            year = new IntegerFilter();
        }
        return year;
    }

    public void setYear(IntegerFilter year) {
        this.year = year;
    }

    public CompetitionTypeFilter getCompetitionType() {
        return competitionType;
    }

    public CompetitionTypeFilter competitionType() {
        if (competitionType == null) {
            competitionType = new CompetitionTypeFilter();
        }
        return competitionType;
    }

    public void setCompetitionType(CompetitionTypeFilter competitionType) {
        this.competitionType = competitionType;
    }

    public IntegerFilter getNumberOfRounds() {
        return numberOfRounds;
    }

    public IntegerFilter numberOfRounds() {
        if (numberOfRounds == null) {
            numberOfRounds = new IntegerFilter();
        }
        return numberOfRounds;
    }

    public void setNumberOfRounds(IntegerFilter numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public IntegerFilter getNumberOfSeries() {
        return numberOfSeries;
    }

    public IntegerFilter numberOfSeries() {
        if (numberOfSeries == null) {
            numberOfSeries = new IntegerFilter();
        }
        return numberOfSeries;
    }

    public void setNumberOfSeries(IntegerFilter numberOfSeries) {
        this.numberOfSeries = numberOfSeries;
    }

    public BooleanFilter getFinalRound() {
        return finalRound;
    }

    public BooleanFilter finalRound() {
        if (finalRound == null) {
            finalRound = new BooleanFilter();
        }
        return finalRound;
    }

    public void setFinalRound(BooleanFilter finalRound) {
        this.finalRound = finalRound;
    }

    public BooleanFilter getFinalPreparation() {
        return finalPreparation;
    }

    public BooleanFilter finalPreparation() {
        if (finalPreparation == null) {
            finalPreparation = new BooleanFilter();
        }
        return finalPreparation;
    }

    public void setFinalPreparation(BooleanFilter finalPreparation) {
        this.finalPreparation = finalPreparation;
    }

    public IntegerFilter getNumberOfFinalAthletes() {
        return numberOfFinalAthletes;
    }

    public IntegerFilter numberOfFinalAthletes() {
        if (numberOfFinalAthletes == null) {
            numberOfFinalAthletes = new IntegerFilter();
        }
        return numberOfFinalAthletes;
    }

    public void setNumberOfFinalAthletes(IntegerFilter numberOfFinalAthletes) {
        this.numberOfFinalAthletes = numberOfFinalAthletes;
    }

    public IntegerFilter getNumberOfFinalSeries() {
        return numberOfFinalSeries;
    }

    public IntegerFilter numberOfFinalSeries() {
        if (numberOfFinalSeries == null) {
            numberOfFinalSeries = new IntegerFilter();
        }
        return numberOfFinalSeries;
    }

    public void setNumberOfFinalSeries(IntegerFilter numberOfFinalSeries) {
        this.numberOfFinalSeries = numberOfFinalSeries;
    }

    public IntegerFilter getTeamSize() {
        return teamSize;
    }

    public IntegerFilter teamSize() {
        if (teamSize == null) {
            teamSize = new IntegerFilter();
        }
        return teamSize;
    }

    public void setTeamSize(IntegerFilter teamSize) {
        this.teamSize = teamSize;
    }

    public BooleanFilter getTemplate() {
        return template;
    }

    public BooleanFilter template() {
        if (template == null) {
            template = new BooleanFilter();
        }
        return template;
    }

    public void setTemplate(BooleanFilter template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompetitionCriteria that = (CompetitionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(year, that.year) &&
            Objects.equals(competitionType, that.competitionType) &&
            Objects.equals(numberOfRounds, that.numberOfRounds) &&
            Objects.equals(numberOfSeries, that.numberOfSeries) &&
            Objects.equals(finalRound, that.finalRound) &&
            Objects.equals(finalPreparation, that.finalPreparation) &&
            Objects.equals(numberOfFinalAthletes, that.numberOfFinalAthletes) &&
            Objects.equals(numberOfFinalSeries, that.numberOfFinalSeries) &&
            Objects.equals(teamSize, that.teamSize) &&
            Objects.equals(template, that.template)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            year,
            competitionType,
            numberOfRounds,
            numberOfSeries,
            finalRound,
            finalPreparation,
            numberOfFinalAthletes,
            numberOfFinalSeries,
            teamSize,
            template
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompetitionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (competitionType != null ? "competitionType=" + competitionType + ", " : "") +
                (numberOfRounds != null ? "numberOfRounds=" + numberOfRounds + ", " : "") +
                (numberOfSeries != null ? "numberOfSeries=" + numberOfSeries + ", " : "") +
                (finalRound != null ? "finalRound=" + finalRound + ", " : "") +
                (finalPreparation != null ? "finalPreparation=" + finalPreparation + ", " : "") +
                (numberOfFinalAthletes != null ? "numberOfFinalAthletes=" + numberOfFinalAthletes + ", " : "") +
                (numberOfFinalSeries != null ? "numberOfFinalSeries=" + numberOfFinalSeries + ", " : "") +
                (teamSize != null ? "teamSize=" + teamSize + ", " : "") +
                (template != null ? "template=" + template + ", " : "") +
                "}";
    }
}
