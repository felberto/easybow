package ch.felberto.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RangierungDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RangierungDTO.class);
        RangierungDTO rangierungDTO1 = new RangierungDTO();
        rangierungDTO1.setId(1L);
        RangierungDTO rangierungDTO2 = new RangierungDTO();
        assertThat(rangierungDTO1).isNotEqualTo(rangierungDTO2);
        rangierungDTO2.setId(rangierungDTO1.getId());
        assertThat(rangierungDTO1).isEqualTo(rangierungDTO2);
        rangierungDTO2.setId(2L);
        assertThat(rangierungDTO1).isNotEqualTo(rangierungDTO2);
        rangierungDTO1.setId(null);
        assertThat(rangierungDTO1).isNotEqualTo(rangierungDTO2);
    }
}
