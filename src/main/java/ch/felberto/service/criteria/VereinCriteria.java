package ch.felberto.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ch.felberto.domain.Verein} entity. This class is used
 * in {@link ch.felberto.web.rest.VereinResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vereins?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VereinCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter verbandId;

    public VereinCriteria() {}

    public VereinCriteria(VereinCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.verbandId = other.verbandId == null ? null : other.verbandId.copy();
    }

    @Override
    public VereinCriteria copy() {
        return new VereinCriteria(this);
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

    public LongFilter getVerbandId() {
        return verbandId;
    }

    public LongFilter verbandId() {
        if (verbandId == null) {
            verbandId = new LongFilter();
        }
        return verbandId;
    }

    public void setVerbandId(LongFilter verbandId) {
        this.verbandId = verbandId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VereinCriteria that = (VereinCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(verbandId, that.verbandId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, verbandId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VereinCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (verbandId != null ? "verbandId=" + verbandId + ", " : "") +
            "}";
    }
}