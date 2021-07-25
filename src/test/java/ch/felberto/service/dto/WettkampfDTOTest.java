package ch.felberto.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WettkampfDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WettkampfDTO.class);
        WettkampfDTO wettkampfDTO1 = new WettkampfDTO();
        wettkampfDTO1.setId(1L);
        WettkampfDTO wettkampfDTO2 = new WettkampfDTO();
        assertThat(wettkampfDTO1).isNotEqualTo(wettkampfDTO2);
        wettkampfDTO2.setId(wettkampfDTO1.getId());
        assertThat(wettkampfDTO1).isEqualTo(wettkampfDTO2);
        wettkampfDTO2.setId(2L);
        assertThat(wettkampfDTO1).isNotEqualTo(wettkampfDTO2);
        wettkampfDTO1.setId(null);
        assertThat(wettkampfDTO1).isNotEqualTo(wettkampfDTO2);
    }
}
