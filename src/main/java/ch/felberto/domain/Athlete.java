package ch.felberto.domain;

import ch.felberto.domain.enumeration.Gender;
import ch.felberto.domain.enumeration.Nationality;
import ch.felberto.domain.enumeration.Position;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A athlete.
 */
@Entity
@Table(name = "athlete")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Athlete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "yearOfBirth", nullable = false)
    private Integer yearOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "nationality", nullable = false)
    private Nationality nationality;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private Position position;

    @Column(name = "role", nullable = false)
    private String role;

    @ManyToOne
    @JsonIgnoreProperties(value = { "association" }, allowSetters = true)
    private Club club;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Athlete id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Athlete name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Athlete firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getYearOfBirth() {
        return this.yearOfBirth;
    }

    public Athlete yearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
        return this;
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public Nationality getNationality() {
        return this.nationality;
    }

    public Athlete nationality(Nationality nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = nationality;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Athlete gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Position getPosition() {
        return this.position;
    }

    public Athlete position(Position position) {
        this.position = position;
        return this;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getRole() {
        return this.role;
    }

    public Athlete role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Club getClub() {
        return this.club;
    }

    public Athlete club(Club club) {
        this.setClub(club);
        return this;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Athlete)) {
            return false;
        }
        return id != null && id.equals(((Athlete) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Athlete{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", firstName='" + getFirstName() + "'" +
                ", yearOfBirth=" + getYearOfBirth() +
                ", nationality=" + getNationality() +
                ", gender=" + getGender() +
                ", position='" + getPosition() + "'" +
                ", role='" + getRole() + "'" +
                "}";
    }
}
