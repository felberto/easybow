package ch.felberto.service.criteria;

import ch.felberto.domain.enumeration.Gender;
import ch.felberto.domain.enumeration.Nationality;
import ch.felberto.domain.enumeration.Position;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ch.felberto.domain.Athlete} entity. This class is used
 * in {@link ch.felberto.web.rest.AthleteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /athletes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AthleteCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Position
     */
    public static class PositionFilter extends Filter<Position> {

        public PositionFilter() {}

        public PositionFilter(PositionFilter filter) {
            super(filter);
        }

        @Override
        public PositionFilter copy() {
            return new PositionFilter(this);
        }
    }

    /**
     * Class for filtering Nationality
     */
    public static class NationalityFilter extends Filter<Nationality> {

        public NationalityFilter() {}

        public NationalityFilter(NationalityFilter filter) {
            super(filter);
        }

        @Override
        public NationalityFilter copy() {
            return new NationalityFilter(this);
        }
    }

    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {}

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter firstName;

    private IntegerFilter yearOfBirth;

    private NationalityFilter nationality;

    private GenderFilter gender;

    private PositionFilter position;

    private StringFilter role;

    private LongFilter clubId;

    public AthleteCriteria() {}

    public AthleteCriteria(AthleteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.yearOfBirth = other.yearOfBirth == null ? null : other.yearOfBirth.copy();
        this.nationality = other.nationality == null ? null : other.nationality.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.role = other.role == null ? null : other.role.copy();
        this.clubId = other.clubId == null ? null : other.clubId.copy();
    }

    @Override
    public AthleteCriteria copy() {
        return new AthleteCriteria(this);
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

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public IntegerFilter getYearOfBirth() {
        return yearOfBirth;
    }

    public IntegerFilter yearOfBirth() {
        if (yearOfBirth == null) {
            yearOfBirth = new IntegerFilter();
        }
        return yearOfBirth;
    }

    public void setYearOfBirth(IntegerFilter yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public NationalityFilter getNationality() {
        return nationality;
    }

    public NationalityFilter nationality() {
        if (nationality == null) {
            nationality = new NationalityFilter();
        }
        return nationality;
    }

    public void setNationality(NationalityFilter nationality) {
        this.nationality = nationality;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public GenderFilter gender() {
        if (gender == null) {
            gender = new GenderFilter();
        }
        return gender;
    }

    public void setNationality(GenderFilter gender) {
        this.gender = gender;
    }

    public PositionFilter getPosition() {
        return position;
    }

    public PositionFilter position() {
        if (position == null) {
            position = new PositionFilter();
        }
        return position;
    }

    public void setPosition(PositionFilter position) {
        this.position = position;
    }

    public StringFilter getRole() {
        return role;
    }

    public StringFilter role() {
        if (role == null) {
            role = new StringFilter();
        }
        return role;
    }

    public void setRole(StringFilter role) {
        this.role = role;
    }

    public LongFilter getClubId() {
        return clubId;
    }

    public LongFilter clubId() {
        if (clubId == null) {
            clubId = new LongFilter();
        }
        return clubId;
    }

    public void setClubId(LongFilter clubId) {
        this.clubId = clubId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AthleteCriteria that = (AthleteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(yearOfBirth, that.yearOfBirth) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(position, that.position) &&
            Objects.equals(role, that.role) &&
            Objects.equals(clubId, that.clubId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, firstName, yearOfBirth, nationality, gender, position, role, clubId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AthleteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (yearOfBirth != null ? "yearOfBirth=" + yearOfBirth + ", " : "") +
                (nationality != null ? "nationality=" + nationality + ", " : "") +
                (gender != null ? "gender=" + gender + ", " : "") +
                (position != null ? "position=" + position + ", " : "") +
                (role != null ? "role=" + role + ", " : "") +
                (clubId != null ? "clubId=" + clubId + ", " : "") +
                "}";
    }
}
