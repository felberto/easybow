package ch.felberto.service.dto;

import ch.felberto.domain.enumeration.Stellung;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ch.felberto.domain.Schuetze} entity.
 */
public class SchuetzeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate jahrgang;

    @NotNull
    private Stellung stellung;

    private VereinDTO verein;

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

    public LocalDate getJahrgang() {
        return jahrgang;
    }

    public void setJahrgang(LocalDate jahrgang) {
        this.jahrgang = jahrgang;
    }

    public Stellung getStellung() {
        return stellung;
    }

    public void setStellung(Stellung stellung) {
        this.stellung = stellung;
    }

    public VereinDTO getVerein() {
        return verein;
    }

    public void setVerein(VereinDTO verein) {
        this.verein = verein;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchuetzeDTO)) {
            return false;
        }

        SchuetzeDTO schuetzeDTO = (SchuetzeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, schuetzeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchuetzeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", jahrgang='" + getJahrgang() + "'" +
            ", stellung='" + getStellung() + "'" +
            ", verein=" + getVerein() +
            "}";
    }
}
