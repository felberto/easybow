package ch.felberto.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ch.felberto.domain.Resultate} entity.
 */
public class ResultateDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer runde;

    private PassenDTO passe1;

    private PassenDTO passe2;

    private PassenDTO passe3;

    private PassenDTO passe4;

    private GruppenDTO gruppe;

    private SchuetzeDTO schuetze;

    private WettkampfDTO wettkampf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRunde() {
        return runde;
    }

    public void setRunde(Integer runde) {
        this.runde = runde;
    }

    public PassenDTO getPasse1() {
        return passe1;
    }

    public void setPasse1(PassenDTO passe1) {
        this.passe1 = passe1;
    }

    public PassenDTO getPasse2() {
        return passe2;
    }

    public void setPasse2(PassenDTO passe2) {
        this.passe2 = passe2;
    }

    public PassenDTO getPasse3() {
        return passe3;
    }

    public void setPasse3(PassenDTO passe3) {
        this.passe3 = passe3;
    }

    public PassenDTO getPasse4() {
        return passe4;
    }

    public void setPasse4(PassenDTO passe4) {
        this.passe4 = passe4;
    }

    public GruppenDTO getGruppe() {
        return gruppe;
    }

    public void setGruppe(GruppenDTO gruppe) {
        this.gruppe = gruppe;
    }

    public SchuetzeDTO getSchuetze() {
        return schuetze;
    }

    public void setSchuetze(SchuetzeDTO schuetze) {
        this.schuetze = schuetze;
    }

    public WettkampfDTO getWettkampf() {
        return wettkampf;
    }

    public void setWettkampf(WettkampfDTO wettkampf) {
        this.wettkampf = wettkampf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultateDTO)) {
            return false;
        }

        ResultateDTO resultateDTO = (ResultateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, resultateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultateDTO{" +
            "id=" + getId() +
            ", runde=" + getRunde() +
            ", passe1=" + getPasse1() +
            ", passe2=" + getPasse2() +
            ", passe3=" + getPasse3() +
            ", passe4=" + getPasse4() +
            ", gruppe=" + getGruppe() +
            ", schuetze=" + getSchuetze() +
            ", wettkampf=" + getWettkampf() +
            "}";
    }
}
