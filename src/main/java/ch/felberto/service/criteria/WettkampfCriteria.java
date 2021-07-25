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
 * Criteria class for the {@link ch.felberto.domain.Wettkampf} entity. This class is used
 * in {@link ch.felberto.web.rest.WettkampfResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /wettkampfs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WettkampfCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter jahr;

    private IntegerFilter anzahlRunden;

    private IntegerFilter anzahlPassen;

    private BooleanFilter finalRunde;

    private BooleanFilter finalVorbereitung;

    private IntegerFilter anzahlFinalteilnehmer;

    private IntegerFilter anzahlPassenFinal;

    private IntegerFilter anzahlTeam;

    private BooleanFilter template;

    public WettkampfCriteria() {}

    public WettkampfCriteria(WettkampfCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.jahr = other.jahr == null ? null : other.jahr.copy();
        this.anzahlRunden = other.anzahlRunden == null ? null : other.anzahlRunden.copy();
        this.anzahlPassen = other.anzahlPassen == null ? null : other.anzahlPassen.copy();
        this.finalRunde = other.finalRunde == null ? null : other.finalRunde.copy();
        this.finalVorbereitung = other.finalVorbereitung == null ? null : other.finalVorbereitung.copy();
        this.anzahlFinalteilnehmer = other.anzahlFinalteilnehmer == null ? null : other.anzahlFinalteilnehmer.copy();
        this.anzahlPassenFinal = other.anzahlPassenFinal == null ? null : other.anzahlPassenFinal.copy();
        this.anzahlTeam = other.anzahlTeam == null ? null : other.anzahlTeam.copy();
        this.template = other.template == null ? null : other.template.copy();
    }

    @Override
    public WettkampfCriteria copy() {
        return new WettkampfCriteria(this);
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

    public IntegerFilter getJahr() {
        return jahr;
    }

    public IntegerFilter jahr() {
        if (jahr == null) {
            jahr = new IntegerFilter();
        }
        return jahr;
    }

    public void setJahr(IntegerFilter jahr) {
        this.jahr = jahr;
    }

    public IntegerFilter getAnzahlRunden() {
        return anzahlRunden;
    }

    public IntegerFilter anzahlRunden() {
        if (anzahlRunden == null) {
            anzahlRunden = new IntegerFilter();
        }
        return anzahlRunden;
    }

    public void setAnzahlRunden(IntegerFilter anzahlRunden) {
        this.anzahlRunden = anzahlRunden;
    }

    public IntegerFilter getAnzahlPassen() {
        return anzahlPassen;
    }

    public IntegerFilter anzahlPassen() {
        if (anzahlPassen == null) {
            anzahlPassen = new IntegerFilter();
        }
        return anzahlPassen;
    }

    public void setAnzahlPassen(IntegerFilter anzahlPassen) {
        this.anzahlPassen = anzahlPassen;
    }

    public BooleanFilter getFinalRunde() {
        return finalRunde;
    }

    public BooleanFilter finalRunde() {
        if (finalRunde == null) {
            finalRunde = new BooleanFilter();
        }
        return finalRunde;
    }

    public void setFinalRunde(BooleanFilter finalRunde) {
        this.finalRunde = finalRunde;
    }

    public BooleanFilter getFinalVorbereitung() {
        return finalVorbereitung;
    }

    public BooleanFilter finalVorbereitung() {
        if (finalVorbereitung == null) {
            finalVorbereitung = new BooleanFilter();
        }
        return finalVorbereitung;
    }

    public void setFinalVorbereitung(BooleanFilter finalVorbereitung) {
        this.finalVorbereitung = finalVorbereitung;
    }

    public IntegerFilter getAnzahlFinalteilnehmer() {
        return anzahlFinalteilnehmer;
    }

    public IntegerFilter anzahlFinalteilnehmer() {
        if (anzahlFinalteilnehmer == null) {
            anzahlFinalteilnehmer = new IntegerFilter();
        }
        return anzahlFinalteilnehmer;
    }

    public void setAnzahlFinalteilnehmer(IntegerFilter anzahlFinalteilnehmer) {
        this.anzahlFinalteilnehmer = anzahlFinalteilnehmer;
    }

    public IntegerFilter getAnzahlPassenFinal() {
        return anzahlPassenFinal;
    }

    public IntegerFilter anzahlPassenFinal() {
        if (anzahlPassenFinal == null) {
            anzahlPassenFinal = new IntegerFilter();
        }
        return anzahlPassenFinal;
    }

    public void setAnzahlPassenFinal(IntegerFilter anzahlPassenFinal) {
        this.anzahlPassenFinal = anzahlPassenFinal;
    }

    public IntegerFilter getAnzahlTeam() {
        return anzahlTeam;
    }

    public IntegerFilter anzahlTeam() {
        if (anzahlTeam == null) {
            anzahlTeam = new IntegerFilter();
        }
        return anzahlTeam;
    }

    public void setAnzahlTeam(IntegerFilter anzahlTeam) {
        this.anzahlTeam = anzahlTeam;
    }

    public BooleanFilter getTemplate() {
        return template;
    }

    public BooleanFilter template() {
        if (template == null) {
            template = new BooleanFilter();
        }
        return template;
    }

    public void setTemplate(BooleanFilter template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WettkampfCriteria that = (WettkampfCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(jahr, that.jahr) &&
            Objects.equals(anzahlRunden, that.anzahlRunden) &&
            Objects.equals(anzahlPassen, that.anzahlPassen) &&
            Objects.equals(finalRunde, that.finalRunde) &&
            Objects.equals(finalVorbereitung, that.finalVorbereitung) &&
            Objects.equals(anzahlFinalteilnehmer, that.anzahlFinalteilnehmer) &&
            Objects.equals(anzahlPassenFinal, that.anzahlPassenFinal) &&
            Objects.equals(anzahlTeam, that.anzahlTeam) &&
            Objects.equals(template, that.template)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            jahr,
            anzahlRunden,
            anzahlPassen,
            finalRunde,
            finalVorbereitung,
            anzahlFinalteilnehmer,
            anzahlPassenFinal,
            anzahlTeam,
            template
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WettkampfCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (jahr != null ? "jahr=" + jahr + ", " : "") +
            (anzahlRunden != null ? "anzahlRunden=" + anzahlRunden + ", " : "") +
            (anzahlPassen != null ? "anzahlPassen=" + anzahlPassen + ", " : "") +
            (finalRunde != null ? "finalRunde=" + finalRunde + ", " : "") +
            (finalVorbereitung != null ? "finalVorbereitung=" + finalVorbereitung + ", " : "") +
            (anzahlFinalteilnehmer != null ? "anzahlFinalteilnehmer=" + anzahlFinalteilnehmer + ", " : "") +
            (anzahlPassenFinal != null ? "anzahlPassenFinal=" + anzahlPassenFinal + ", " : "") +
            (anzahlTeam != null ? "anzahlTeam=" + anzahlTeam + ", " : "") +
            (template != null ? "template=" + template + ", " : "") +
            "}";
    }
}
