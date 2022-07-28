package ch.felberto.service.criteria;

import ch.felberto.domain.Series;
import ch.felberto.web.rest.SeriesResource;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;

/**
 * Criteria class for the {@link Series} entity. This class is used
 * in {@link SeriesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /series?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SeriesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter p1;

    private IntegerFilter p2;

    private IntegerFilter p3;

    private IntegerFilter p4;

    private IntegerFilter p5;

    private IntegerFilter p6;

    private IntegerFilter p7;

    private IntegerFilter p8;

    private IntegerFilter p9;

    private IntegerFilter p10;

    private IntegerFilter result;

    public SeriesCriteria() {}

    public SeriesCriteria(SeriesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.p1 = other.p1 == null ? null : other.p1.copy();
        this.p2 = other.p2 == null ? null : other.p2.copy();
        this.p3 = other.p3 == null ? null : other.p3.copy();
        this.p4 = other.p4 == null ? null : other.p4.copy();
        this.p5 = other.p5 == null ? null : other.p5.copy();
        this.p6 = other.p6 == null ? null : other.p6.copy();
        this.p7 = other.p7 == null ? null : other.p7.copy();
        this.p8 = other.p8 == null ? null : other.p8.copy();
        this.p9 = other.p9 == null ? null : other.p9.copy();
        this.p10 = other.p10 == null ? null : other.p10.copy();
        this.result = other.result == null ? null : other.result.copy();
    }

    @Override
    public SeriesCriteria copy() {
        return new SeriesCriteria(this);
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

    public IntegerFilter getp1() {
        return p1;
    }

    public IntegerFilter p1() {
        if (p1 == null) {
            p1 = new IntegerFilter();
        }
        return p1;
    }

    public void setp1(IntegerFilter p1) {
        this.p1 = p1;
    }

    public IntegerFilter getp2() {
        return p2;
    }

    public IntegerFilter p2() {
        if (p2 == null) {
            p2 = new IntegerFilter();
        }
        return p2;
    }

    public void setp2(IntegerFilter p2) {
        this.p2 = p2;
    }

    public IntegerFilter getp3() {
        return p3;
    }

    public IntegerFilter p3() {
        if (p3 == null) {
            p3 = new IntegerFilter();
        }
        return p3;
    }

    public void setp3(IntegerFilter p3) {
        this.p3 = p3;
    }

    public IntegerFilter getp4() {
        return p4;
    }

    public IntegerFilter p4() {
        if (p4 == null) {
            p4 = new IntegerFilter();
        }
        return p4;
    }

    public void setp4(IntegerFilter p4) {
        this.p4 = p4;
    }

    public IntegerFilter getp5() {
        return p5;
    }

    public IntegerFilter p5() {
        if (p5 == null) {
            p5 = new IntegerFilter();
        }
        return p5;
    }

    public void setp5(IntegerFilter p5) {
        this.p5 = p5;
    }

    public IntegerFilter getp6() {
        return p6;
    }

    public IntegerFilter p6() {
        if (p6 == null) {
            p6 = new IntegerFilter();
        }
        return p6;
    }

    public void setp6(IntegerFilter p6) {
        this.p6 = p6;
    }

    public IntegerFilter getp7() {
        return p7;
    }

    public IntegerFilter p7() {
        if (p7 == null) {
            p7 = new IntegerFilter();
        }
        return p7;
    }

    public void setp7(IntegerFilter p7) {
        this.p7 = p7;
    }

    public IntegerFilter getp8() {
        return p8;
    }

    public IntegerFilter p8() {
        if (p8 == null) {
            p8 = new IntegerFilter();
        }
        return p8;
    }

    public void setp8(IntegerFilter p8) {
        this.p8 = p8;
    }

    public IntegerFilter getp9() {
        return p9;
    }

    public IntegerFilter p9() {
        if (p9 == null) {
            p9 = new IntegerFilter();
        }
        return p9;
    }

    public void setp9(IntegerFilter p9) {
        this.p9 = p9;
    }

    public IntegerFilter getp10() {
        return p10;
    }

    public IntegerFilter p10() {
        if (p10 == null) {
            p10 = new IntegerFilter();
        }
        return p10;
    }

    public void setp10(IntegerFilter p10) {
        this.p10 = p10;
    }

    public IntegerFilter getResult() {
        return result;
    }

    public IntegerFilter resultat() {
        if (result == null) {
            result = new IntegerFilter();
        }
        return result;
    }

    public void setResult(IntegerFilter result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SeriesCriteria that = (SeriesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(p1, that.p1) &&
            Objects.equals(p2, that.p2) &&
            Objects.equals(p3, that.p3) &&
            Objects.equals(p4, that.p4) &&
            Objects.equals(p5, that.p5) &&
            Objects.equals(p6, that.p6) &&
            Objects.equals(p7, that.p7) &&
            Objects.equals(p8, that.p8) &&
            Objects.equals(p9, that.p9) &&
            Objects.equals(p10, that.p10) &&
            Objects.equals(result, that.result)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, result);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SeriesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (p1 != null ? "p1=" + p1 + ", " : "") +
            (p2 != null ? "p2=" + p2 + ", " : "") +
            (p3 != null ? "p3=" + p3 + ", " : "") +
            (p4 != null ? "p4=" + p4 + ", " : "") +
            (p5 != null ? "p5=" + p5 + ", " : "") +
            (p6 != null ? "p6=" + p6 + ", " : "") +
            (p7 != null ? "p7=" + p7 + ", " : "") +
            (p8 != null ? "p8=" + p8 + ", " : "") +
            (p9 != null ? "p9=" + p9 + ", " : "") +
            (p10 != null ? "p10=" + p10 + ", " : "") +
            (result != null ? "result=" + result + ", " : "") +
            "}";
    }
}
