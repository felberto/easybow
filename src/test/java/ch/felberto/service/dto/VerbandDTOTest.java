package ch.felberto.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerbandDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VerbandDTO.class);
        VerbandDTO verbandDTO1 = new VerbandDTO();
        verbandDTO1.setId(1L);
        VerbandDTO verbandDTO2 = new VerbandDTO();
        assertThat(verbandDTO1).isNotEqualTo(verbandDTO2);
        verbandDTO2.setId(verbandDTO1.getId());
        assertThat(verbandDTO1).isEqualTo(verbandDTO2);
        verbandDTO2.setId(2L);
        assertThat(verbandDTO1).isNotEqualTo(verbandDTO2);
        verbandDTO1.setId(null);
        assertThat(verbandDTO1).isNotEqualTo(verbandDTO2);
    }
}
