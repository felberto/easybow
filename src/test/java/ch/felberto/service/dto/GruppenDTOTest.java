package ch.felberto.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GruppenDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GruppenDTO.class);
        GruppenDTO gruppenDTO1 = new GruppenDTO();
        gruppenDTO1.setId(1L);
        GruppenDTO gruppenDTO2 = new GruppenDTO();
        assertThat(gruppenDTO1).isNotEqualTo(gruppenDTO2);
        gruppenDTO2.setId(gruppenDTO1.getId());
        assertThat(gruppenDTO1).isEqualTo(gruppenDTO2);
        gruppenDTO2.setId(2L);
        assertThat(gruppenDTO1).isNotEqualTo(gruppenDTO2);
        gruppenDTO1.setId(null);
        assertThat(gruppenDTO1).isNotEqualTo(gruppenDTO2);
    }
}
