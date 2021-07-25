package ch.felberto.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ch.felberto.domain.Passen} entity.
 */
public class PassenDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    @Max(value = 11)
    private Integer p1;

    @NotNull
    @Min(value = 0)
    @Max(value = 11)
    private Integer p2;

    @NotNull
    @Min(value = 0)
    @Max(value = 11)
    private Integer p3;

    @NotNull
    @Min(value = 0)
    @Max(value = 11)
    private Integer p4;

    @NotNull
    @Min(value = 0)
    @Max(value = 11)
    private Integer p5;

    @NotNull
    @Min(value = 0)
    @Max(value = 11)
    private Integer p6;

    @NotNull
    @Min(value = 0)
    @Max(value = 11)
    private Integer p7;

    @NotNull
    @Min(value = 0)
    @Max(value = 11)
    private Integer p8;

    @NotNull
    @Min(value = 0)
    @Max(value = 11)
    private Integer p9;

    @NotNull
    @Min(value = 0)
    @Max(value = 11)
    private Integer p10;

    private Integer resultat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getp1() {
        return p1;
    }

    public void setp1(Integer p1) {
        this.p1 = p1;
    }

    public Integer getp2() {
        return p2;
    }

    public void setp2(Integer p2) {
        this.p2 = p2;
    }

    public Integer getp3() {
        return p3;
    }

    public void setp3(Integer p3) {
        this.p3 = p3;
    }

    public Integer getp4() {
        return p4;
    }

    public void setp4(Integer p4) {
        this.p4 = p4;
    }

    public Integer getp5() {
        return p5;
    }

    public void setp5(Integer p5) {
        this.p5 = p5;
    }

    public Integer getp6() {
        return p6;
    }

    public void setp6(Integer p6) {
        this.p6 = p6;
    }

    public Integer getp7() {
        return p7;
    }

    public void setp7(Integer p7) {
        this.p7 = p7;
    }

    public Integer getp8() {
        return p8;
    }

    public void setp8(Integer p8) {
        this.p8 = p8;
    }

    public Integer getp9() {
        return p9;
    }

    public void setp9(Integer p9) {
        this.p9 = p9;
    }

    public Integer getp10() {
        return p10;
    }

    public void setp10(Integer p10) {
        this.p10 = p10;
    }

    public Integer getResultat() {
        return resultat;
    }

    public void setResultat(Integer resultat) {
        this.resultat = resultat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PassenDTO)) {
            return false;
        }

        PassenDTO passenDTO = (PassenDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, passenDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PassenDTO{" +
            "id=" + getId() +
            ", p1=" + getp1() +
            ", p2=" + getp2() +
            ", p3=" + getp3() +
            ", p4=" + getp4() +
            ", p5=" + getp5() +
            ", p6=" + getp6() +
            ", p7=" + getp7() +
            ", p8=" + getp8() +
            ", p9=" + getp9() +
            ", p10=" + getp10() +
            ", resultat=" + getResultat() +
            "}";
    }
}
