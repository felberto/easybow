package ch.felberto.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResultateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultateDTO.class);
        ResultateDTO resultateDTO1 = new ResultateDTO();
        resultateDTO1.setId(1L);
        ResultateDTO resultateDTO2 = new ResultateDTO();
        assertThat(resultateDTO1).isNotEqualTo(resultateDTO2);
        resultateDTO2.setId(resultateDTO1.getId());
        assertThat(resultateDTO1).isEqualTo(resultateDTO2);
        resultateDTO2.setId(2L);
        assertThat(resultateDTO1).isNotEqualTo(resultateDTO2);
        resultateDTO1.setId(null);
        assertThat(resultateDTO1).isNotEqualTo(resultateDTO2);
    }
}
