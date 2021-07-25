package ch.felberto.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ch.felberto.domain.Wettkampf} entity.
 */
public class WettkampfDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private LocalDate jahr;

    @NotNull
    private Integer anzahlRunden;

    @NotNull
    @Min(value = 1)
    @Max(value = 4)
    private Integer anzahlPassen;

    private Boolean finalRunde;

    private Boolean finalVorbereitung;

    private Integer anzahlFinalteilnehmer;

    @Min(value = 1)
    @Max(value = 4)
    private Integer anzahlPassenFinal;

    private Integer anzahlTeam;

    private Boolean template;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getJahr() {
        return jahr;
    }

    public void setJahr(LocalDate jahr) {
        this.jahr = jahr;
    }

    public Integer getAnzahlRunden() {
        return anzahlRunden;
    }

    public void setAnzahlRunden(Integer anzahlRunden) {
        this.anzahlRunden = anzahlRunden;
    }

    public Integer getAnzahlPassen() {
        return anzahlPassen;
    }

    public void setAnzahlPassen(Integer anzahlPassen) {
        this.anzahlPassen = anzahlPassen;
    }

    public Boolean getFinalRunde() {
        return finalRunde;
    }

    public void setFinalRunde(Boolean finalRunde) {
        this.finalRunde = finalRunde;
    }

    public Boolean getFinalVorbereitung() {
        return finalVorbereitung;
    }

    public void setFinalVorbereitung(Boolean finalVorbereitung) {
        this.finalVorbereitung = finalVorbereitung;
    }

    public Integer getAnzahlFinalteilnehmer() {
        return anzahlFinalteilnehmer;
    }

    public void setAnzahlFinalteilnehmer(Integer anzahlFinalteilnehmer) {
        this.anzahlFinalteilnehmer = anzahlFinalteilnehmer;
    }

    public Integer getAnzahlPassenFinal() {
        return anzahlPassenFinal;
    }

    public void setAnzahlPassenFinal(Integer anzahlPassenFinal) {
        this.anzahlPassenFinal = anzahlPassenFinal;
    }

    public Integer getAnzahlTeam() {
        return anzahlTeam;
    }

    public void setAnzahlTeam(Integer anzahlTeam) {
        this.anzahlTeam = anzahlTeam;
    }

    public Boolean getTemplate() {
        return template;
    }

    public void setTemplate(Boolean template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WettkampfDTO)) {
            return false;
        }

        WettkampfDTO wettkampfDTO = (WettkampfDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, wettkampfDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WettkampfDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", jahr='" + getJahr() + "'" +
            ", anzahlRunden=" + getAnzahlRunden() +
            ", anzahlPassen=" + getAnzahlPassen() +
            ", finalRunde='" + getFinalRunde() + "'" +
            ", finalVorbereitung='" + getFinalVorbereitung() + "'" +
            ", anzahlFinalteilnehmer=" + getAnzahlFinalteilnehmer() +
            ", anzahlPassenFinal=" + getAnzahlPassenFinal() +
            ", anzahlTeam=" + getAnzahlTeam() +
            ", template='" + getTemplate() + "'" +
            "}";
    }
}
