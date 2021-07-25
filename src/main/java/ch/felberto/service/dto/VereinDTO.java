package ch.felberto.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ch.felberto.domain.Verein} entity.
 */
public class VereinDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private VerbandDTO verband;

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

    public VerbandDTO getVerband() {
        return verband;
    }

    public void setVerband(VerbandDTO verband) {
        this.verband = verband;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VereinDTO)) {
            return false;
        }

        VereinDTO vereinDTO = (VereinDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vereinDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VereinDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", verband=" + getVerband() +
            "}";
    }
}
