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
 * Criteria class for the {@link ch.felberto.domain.Resultate} entity. This class is used
 * in {@link ch.felberto.web.rest.ResultateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /resultates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResultateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter runde;

    private IntegerFilter resultat;

    private LongFilter passe1Id;

    private LongFilter passe2Id;

    private LongFilter passe3Id;

    private LongFilter passe4Id;

    private LongFilter gruppeId;

    private LongFilter schuetzeId;

    private LongFilter wettkampfId;

    public ResultateCriteria() {}

    public ResultateCriteria(ResultateCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.runde = other.runde == null ? null : other.runde.copy();
        this.resultat = other.resultat == null ? null : other.resultat.copy();
        this.passe1Id = other.passe1Id == null ? null : other.passe1Id.copy();
        this.passe2Id = other.passe2Id == null ? null : other.passe2Id.copy();
        this.passe3Id = other.passe3Id == null ? null : other.passe3Id.copy();
        this.passe4Id = other.passe4Id == null ? null : other.passe4Id.copy();
        this.gruppeId = other.gruppeId == null ? null : other.gruppeId.copy();
        this.schuetzeId = other.schuetzeId == null ? null : other.schuetzeId.copy();
        this.wettkampfId = other.wettkampfId == null ? null : other.wettkampfId.copy();
    }

    @Override
    public ResultateCriteria copy() {
        return new ResultateCriteria(this);
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

    public IntegerFilter getResultat() {
        return resultat;
    }

    public IntegerFilter resultat() {
        if (resultat == null) {
            resultat = new IntegerFilter();
        }
        return resultat;
    }

    public void setResultat(IntegerFilter resultat) {
        this.resultat = resultat;
    }

    public LongFilter getPasse1Id() {
        return passe1Id;
    }

    public LongFilter passe1Id() {
        if (passe1Id == null) {
            passe1Id = new LongFilter();
        }
        return passe1Id;
    }

    public void setPasse1Id(LongFilter passe1Id) {
        this.passe1Id = passe1Id;
    }

    public LongFilter getPasse2Id() {
        return passe2Id;
    }

    public LongFilter passe2Id() {
        if (passe2Id == null) {
            passe2Id = new LongFilter();
        }
        return passe2Id;
    }

    public void setPasse2Id(LongFilter passe2Id) {
        this.passe2Id = passe2Id;
    }

    public LongFilter getPasse3Id() {
        return passe3Id;
    }

    public LongFilter passe3Id() {
        if (passe3Id == null) {
            passe3Id = new LongFilter();
        }
        return passe3Id;
    }

    public void setPasse3Id(LongFilter passe3Id) {
        this.passe3Id = passe3Id;
    }

    public LongFilter getPasse4Id() {
        return passe4Id;
    }

    public LongFilter passe4Id() {
        if (passe4Id == null) {
            passe4Id = new LongFilter();
        }
        return passe4Id;
    }

    public void setPasse4Id(LongFilter passe4Id) {
        this.passe4Id = passe4Id;
    }

    public LongFilter getGruppeId() {
        return gruppeId;
    }

    public LongFilter gruppeId() {
        if (gruppeId == null) {
            gruppeId = new LongFilter();
        }
        return gruppeId;
    }

    public void setGruppeId(LongFilter gruppeId) {
        this.gruppeId = gruppeId;
    }

    public LongFilter getSchuetzeId() {
        return schuetzeId;
    }

    public LongFilter schuetzeId() {
        if (schuetzeId == null) {
            schuetzeId = new LongFilter();
        }
        return schuetzeId;
    }

    public void setSchuetzeId(LongFilter schuetzeId) {
        this.schuetzeId = schuetzeId;
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
        final ResultateCriteria that = (ResultateCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(runde, that.runde) &&
            Objects.equals(resultat, that.resultat) &&
            Objects.equals(passe1Id, that.passe1Id) &&
            Objects.equals(passe2Id, that.passe2Id) &&
            Objects.equals(passe3Id, that.passe3Id) &&
            Objects.equals(passe4Id, that.passe4Id) &&
            Objects.equals(gruppeId, that.gruppeId) &&
            Objects.equals(schuetzeId, that.schuetzeId) &&
            Objects.equals(wettkampfId, that.wettkampfId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, runde, resultat, passe1Id, passe2Id, passe3Id, passe4Id, gruppeId, schuetzeId, wettkampfId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultateCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (runde != null ? "runde=" + runde + ", " : "") +
            (resultat != null ? "resultat=" + resultat + ", " : "") +
            (passe1Id != null ? "passe1Id=" + passe1Id + ", " : "") +
            (passe2Id != null ? "passe2Id=" + passe2Id + ", " : "") +
            (passe3Id != null ? "passe3Id=" + passe3Id + ", " : "") +
            (passe4Id != null ? "passe4Id=" + passe4Id + ", " : "") +
            (gruppeId != null ? "gruppeId=" + gruppeId + ", " : "") +
            (schuetzeId != null ? "schuetzeId=" + schuetzeId + ", " : "") +
            (wettkampfId != null ? "wettkampfId=" + wettkampfId + ", " : "") +
            "}";
    }
}
