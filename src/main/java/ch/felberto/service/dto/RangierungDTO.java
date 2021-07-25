package ch.felberto.service.dto;

import ch.felberto.domain.enumeration.Rangierungskriterien;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ch.felberto.domain.Rangierung} entity.
 */
public class RangierungDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer position;

    @NotNull
    private Rangierungskriterien rangierungskriterien;

    private WettkampfDTO wettkampf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Rangierungskriterien getRangierungskriterien() {
        return rangierungskriterien;
    }

    public void setRangierungskriterien(Rangierungskriterien rangierungskriterien) {
        this.rangierungskriterien = rangierungskriterien;
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
        if (!(o instanceof RangierungDTO)) {
            return false;
        }

        RangierungDTO rangierungDTO = (RangierungDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rangierungDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RangierungDTO{" +
            "id=" + getId() +
            ", position=" + getPosition() +
            ", rangierungskriterien='" + getRangierungskriterien() + "'" +
            ", wettkampf=" + getWettkampf() +
            "}";
    }
}
