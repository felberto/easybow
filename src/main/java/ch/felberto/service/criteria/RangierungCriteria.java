package ch.felberto.service.criteria;

import ch.felberto.domain.enumeration.Rangierungskriterien;
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
 * Criteria class for the {@link ch.felberto.domain.Rangierung} entity. This class is used
 * in {@link ch.felberto.web.rest.RangierungResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rangierungs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RangierungCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Rangierungskriterien
     */
    public static class RangierungskriterienFilter extends Filter<Rangierungskriterien> {

        public RangierungskriterienFilter() {}

        public RangierungskriterienFilter(RangierungskriterienFilter filter) {
            super(filter);
        }

        @Override
        public RangierungskriterienFilter copy() {
            return new RangierungskriterienFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter position;

    private RangierungskriterienFilter rangierungskriterien;

    private LongFilter wettkampfId;

    public RangierungCriteria() {}

    public RangierungCriteria(RangierungCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.rangierungskriterien = other.rangierungskriterien == null ? null : other.rangierungskriterien.copy();
        this.wettkampfId = other.wettkampfId == null ? null : other.wettkampfId.copy();
    }

    @Override
    public RangierungCriteria copy() {
        return new RangierungCriteria(this);
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

    public RangierungskriterienFilter getRangierungskriterien() {
        return rangierungskriterien;
    }

    public RangierungskriterienFilter rangierungskriterien() {
        if (rangierungskriterien == null) {
            rangierungskriterien = new RangierungskriterienFilter();
        }
        return rangierungskriterien;
    }

    public void setRangierungskriterien(RangierungskriterienFilter rangierungskriterien) {
        this.rangierungskriterien = rangierungskriterien;
    }

    public LongFilter getWettkampfId() {
        return wettkampfId;
    }

    public LongFilter wettkampfId() {
        if (wettkampfId == null) {
            wettkampfId = new LongFilter();
        }
        return wettkampfId;
    }

    public void setWettkampfId(LongFilter wettkampfId) {
        this.wettkampfId = wettkampfId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RangierungCriteria that = (RangierungCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(position, that.position) &&
            Objects.equals(rangierungskriterien, that.rangierungskriterien) &&
            Objects.equals(wettkampfId, that.wettkampfId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, position, rangierungskriterien, wettkampfId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RangierungCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (position != null ? "position=" + position + ", " : "") +
            (rangierungskriterien != null ? "rangierungskriterien=" + rangierungskriterien + ", " : "") +
            (wettkampfId != null ? "wettkampfId=" + wettkampfId + ", " : "") +
            "}";
    }
}
