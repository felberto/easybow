package ch.felberto.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PassenDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PassenDTO.class);
        PassenDTO passenDTO1 = new PassenDTO();
        passenDTO1.setId(1L);
        PassenDTO passenDTO2 = new PassenDTO();
        assertThat(passenDTO1).isNotEqualTo(passenDTO2);
        passenDTO2.setId(passenDTO1.getId());
        assertThat(passenDTO1).isEqualTo(passenDTO2);
        passenDTO2.setId(2L);
        assertThat(passenDTO1).isNotEqualTo(passenDTO2);
        passenDTO1.setId(null);
        assertThat(passenDTO1).isNotEqualTo(passenDTO2);
    }
}
