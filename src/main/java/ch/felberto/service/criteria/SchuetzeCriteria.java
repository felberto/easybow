package ch.felberto.service.criteria;

import ch.felberto.domain.enumeration.Stellung;
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
 * Criteria class for the {@link ch.felberto.domain.Schuetze} entity. This class is used
 * in {@link ch.felberto.web.rest.SchuetzeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /schuetzes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SchuetzeCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Stellung
     */
    public static class StellungFilter extends Filter<Stellung> {

        public StellungFilter() {}

        public StellungFilter(StellungFilter filter) {
            super(filter);
        }

        @Override
        public StellungFilter copy() {
            return new StellungFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter jahrgang;

    private StellungFilter stellung;

    private LongFilter vereinId;

    public SchuetzeCriteria() {}

    public SchuetzeCriteria(SchuetzeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.jahrgang = other.jahrgang == null ? null : other.jahrgang.copy();
        this.stellung = other.stellung == null ? null : other.stellung.copy();
        this.vereinId = other.vereinId == null ? null : other.vereinId.copy();
    }

    @Override
    public SchuetzeCriteria copy() {
        return new SchuetzeCriteria(this);
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

    public IntegerFilter getJahrgang() {
        return jahrgang;
    }

    public IntegerFilter jahrgang() {
        if (jahrgang == null) {
            jahrgang = new IntegerFilter();
        }
        return jahrgang;
    }

    public void setJahrgang(IntegerFilter jahrgang) {
        this.jahrgang = jahrgang;
    }

    public StellungFilter getStellung() {
        return stellung;
    }

    public StellungFilter stellung() {
        if (stellung == null) {
            stellung = new StellungFilter();
        }
        return stellung;
    }

    public void setStellung(StellungFilter stellung) {
        this.stellung = stellung;
    }

    public LongFilter getVereinId() {
        return vereinId;
    }

    public LongFilter vereinId() {
        if (vereinId == null) {
            vereinId = new LongFilter();
        }
        return vereinId;
    }

    public void setVereinId(LongFilter vereinId) {
        this.vereinId = vereinId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SchuetzeCriteria that = (SchuetzeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(jahrgang, that.jahrgang) &&
            Objects.equals(stellung, that.stellung) &&
            Objects.equals(vereinId, that.vereinId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, jahrgang, stellung, vereinId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchuetzeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (jahrgang != null ? "jahrgang=" + jahrgang + ", " : "") +
            (stellung != null ? "stellung=" + stellung + ", " : "") +
            (vereinId != null ? "vereinId=" + vereinId + ", " : "") +
            "}";
    }
}
