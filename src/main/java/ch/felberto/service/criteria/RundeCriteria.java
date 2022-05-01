package ch.felberto.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Runde} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.RundeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rundes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RundeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter runde;

    private LocalDateFilter datum;

    private LongFilter wettkampfId;

    public RundeCriteria() {}

    public RundeCriteria(RundeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.runde = other.runde == null ? null : other.runde.copy();
        this.datum = other.datum == null ? null : other.datum.copy();
        this.wettkampfId = other.wettkampfId == null ? null : other.wettkampfId.copy();
    }

    @Override
    public RundeCriteria copy() {
        return new RundeCriteria(this);
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

    public IntegerFilter getRunde() {
        return runde;
    }

    public IntegerFilter runde() {
        if (runde == null) {
            runde = new IntegerFilter();
        }
        return runde;
    }

    public void setRunde(IntegerFilter runde) {
        this.runde = runde;
    }

    public LocalDateFilter getDatum() {
        return datum;
    }

    public LocalDateFilter datum() {
        if (datum == null) {
            datum = new LocalDateFilter();
        }
        return datum;
    }

    public void setDatum(LocalDateFilter datum) {
        this.datum = datum;
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
        final RundeCriteria that = (RundeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(runde, that.runde) &&
            Objects.equals(datum, that.datum) &&
            Objects.equals(wettkampfId, that.wettkampfId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, runde, datum, wettkampfId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RundeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (runde != null ? "runde=" + runde + ", " : "") +
            (datum != null ? "datum=" + datum + ", " : "") +
            (wettkampfId != null ? "wettkampfId=" + wettkampfId + ", " : "") +
            "}";
    }
}
