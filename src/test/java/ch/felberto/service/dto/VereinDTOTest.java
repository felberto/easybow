package ch.felberto.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VereinDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VereinDTO.class);
        VereinDTO vereinDTO1 = new VereinDTO();
        vereinDTO1.setId(1L);
        VereinDTO vereinDTO2 = new VereinDTO();
        assertThat(vereinDTO1).isNotEqualTo(vereinDTO2);
        vereinDTO2.setId(vereinDTO1.getId());
        assertThat(vereinDTO1).isEqualTo(vereinDTO2);
        vereinDTO2.setId(2L);
        assertThat(vereinDTO1).isNotEqualTo(vereinDTO2);
        vereinDTO1.setId(null);
        assertThat(vereinDTO1).isNotEqualTo(vereinDTO2);
    }
}
