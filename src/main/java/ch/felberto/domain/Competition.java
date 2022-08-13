package ch.felberto.domain;

import ch.felberto.domain.enumeration.CompetitionType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A competition.
 */
@Entity
@Table(name = "competition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "year")
    private Integer year;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "competition_type", nullable = false)
    private CompetitionType competitionType;

    @NotNull
    @Column(name = "number_of_rounds", nullable = false)
    private Integer numberOfRounds;

    @NotNull
    @Min(value = 1)
    @Max(value = 4)
    @Column(name = "number_of_series", nullable = false)
    private Integer numberOfSeries;

    @Column(name = "final_round")
    private Boolean finalRound;

    @Column(name = "final_preparation")
    private Boolean finalPreparation;

    @Column(name = "number_of_final_athletes")
    private Integer numberOfFinalAthletes;

    @Min(value = 1)
    @Max(value = 4)
    @Column(name = "number_of_final_series")
    private Integer numberOfFinalSeries;

    @Column(name = "team_size")
    private Integer teamSize;

    @Column(name = "template")
    private Boolean template;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Competition id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Competition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return this.year;
    }

    public Competition year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public CompetitionType getCompetitionType() {
        return this.competitionType;
    }

    public Competition competitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
        return this;
    }

    public void setCompetitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
    }

    public Integer getNumberOfRounds() {
        return this.numberOfRounds;
    }

    public Competition numberOfRounds(Integer numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
        return this;
    }

    public void setNumberOfRounds(Integer numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public Integer getNumberOfSeries() {
        return this.numberOfSeries;
    }

    public Competition numberOfSeries(Integer numberOfSeries) {
        this.numberOfSeries = numberOfSeries;
        return this;
    }

    public void setNumberOfSeries(Integer numberOfSeries) {
        this.numberOfSeries = numberOfSeries;
    }

    public Boolean getFinalRound() {
        return this.finalRound;
    }

    public Competition finalRound(Boolean finalRound) {
        this.finalRound = finalRound;
        return this;
    }

    public void setFinalRound(Boolean finalRound) {
        this.finalRound = finalRound;
    }

    public Boolean getFinalPreparation() {
        return this.finalPreparation;
    }

    public Competition finalPreparation(Boolean finalPreparation) {
        this.finalPreparation = finalPreparation;
        return this;
    }

    public void setFinalPreparation(Boolean finalPreparation) {
        this.finalPreparation = finalPreparation;
    }

    public Integer getNumberOfFinalAthletes() {
        return this.numberOfFinalAthletes;
    }

    public Competition numberOfFinalAthletes(Integer numberOfFinalAthletes) {
        this.numberOfFinalAthletes = numberOfFinalAthletes;
        return this;
    }

    public void setNumberOfFinalAthletes(Integer numberOfFinalAthletes) {
        this.numberOfFinalAthletes = numberOfFinalAthletes;
    }

    public Integer getNumberOfFinalSeries() {
        return this.numberOfFinalSeries;
    }

    public Competition numberOfFinalSeries(Integer numberOfFinalSeries) {
        this.numberOfFinalSeries = numberOfFinalSeries;
        return this;
    }

    public void setNumberOfFinalSeries(Integer numberOfFinalSeries) {
        this.numberOfFinalSeries = numberOfFinalSeries;
    }

    public Integer getTeamSize() {
        return this.teamSize;
    }

    public Competition teamSize(Integer teamSize) {
        this.teamSize = teamSize;
        return this;
    }

    public void setTeamSize(Integer teamSize) {
        this.teamSize = teamSize;
    }

    public Boolean getTemplate() {
        return this.template;
    }

    public Competition template(Boolean template) {
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
        if (!(o instanceof Competition)) {
            return false;
        }
        return id != null && id.equals(((Competition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Competition{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", year=" + getYear() +
                ", competitionType=" + getCompetitionType() +
                ", numberOfRounds=" + getNumberOfRounds() +
                ", numberOfSeries=" + getNumberOfSeries() +
                ", finalRound='" + getFinalRound() + "'" +
                ", finalPreparation='" + getFinalPreparation() + "'" +
                ", numberOfFinalAthletes=" + getNumberOfFinalAthletes() +
                ", numberOfFinalSeries=" + getNumberOfFinalSeries() +
                ", teamSize=" + getTeamSize() +
                ", template='" + getTemplate() + "'" +
                "}";
    }
}
