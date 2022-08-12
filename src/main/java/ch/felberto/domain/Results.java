package ch.felberto.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

/**
 * A results.
 */
@Entity
@Table(name = "results")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Results implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "round", nullable = false)
    private Integer round;

    @Column(name = "result")
    private Integer result;

    @Column(name = "athlete_number")
    private String athleteNumber;

    @OneToOne
    @JoinColumn(unique = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Series serie1;

    @OneToOne
    @JoinColumn(unique = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Series serie2;

    @OneToOne
    @JoinColumn(unique = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Series serie3;

    @OneToOne
    @JoinColumn(unique = true)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Series serie4;

    @ManyToOne
    private Group group;

    @ManyToOne
    @JsonIgnoreProperties(value = { "club" }, allowSetters = true)
    private Athlete athlete;

    @ManyToOne
    private Competition competition;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Results id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getRound() {
        return this.round;
    }

    public Results round(Integer round) {
        this.round = round;
        return this;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Integer getResult() {
        return this.result;
    }

    public Results result(Integer result) {
        this.result = result;
        return this;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getAthleteNumber() {
        return this.athleteNumber;
    }

    public Results athleteNumber(String athleteNumber) {
        this.athleteNumber = athleteNumber;
        return this;
    }

    public void setAthleteNumber(String athleteNumber) {
        this.athleteNumber = athleteNumber;
    }

    public Series getSerie1() {
        return this.serie1;
    }

    public Results serie1(Series serie1) {
        this.setSerie1(serie1);
        return this;
    }

    public void setSerie1(Series serie1) {
        this.serie1 = serie1;
    }

    public Series getSerie2() {
        return this.serie2;
    }

    public Results serie2(Series serie2) {
        this.setSerie2(serie2);
        return this;
    }

    public void setSerie2(Series serie2) {
        this.serie2 = serie2;
    }

    public Series getSerie3() {
        return this.serie3;
    }

    public Results serie3(Series serie3) {
        this.setSerie3(serie3);
        return this;
    }

    public void setSerie3(Series serie3) {
        this.serie3 = serie3;
    }

    public Series getSerie4() {
        return this.serie4;
    }

    public Results serie4(Series serie4) {
        this.setSerie4(serie4);
        return this;
    }

    public void setSerie4(Series serie4) {
        this.serie4 = serie4;
    }

    public Group getGroup() {
        return this.group;
    }

    public Results group(Group group) {
        this.setGroup(group);
        return this;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Athlete getAthlete() {
        return this.athlete;
    }

    public Results athlete(Athlete athlete) {
        this.setAthlete(athlete);
        return this;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public Competition getCompetition() {
        return this.competition;
    }

    public Results competition(Competition competition) {
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
        if (!(o instanceof Results)) {
            return false;
        }
        return id != null && id.equals(((Results) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Results{" +
                "id=" + getId() +
                ", round=" + getRound() +
                ", result=" + getResult() +
                "}";
    }
}
