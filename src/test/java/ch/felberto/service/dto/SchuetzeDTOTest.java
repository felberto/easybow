package ch.felberto.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SchuetzeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchuetzeDTO.class);
        SchuetzeDTO schuetzeDTO1 = new SchuetzeDTO();
        schuetzeDTO1.setId(1L);
        SchuetzeDTO schuetzeDTO2 = new SchuetzeDTO();
        assertThat(schuetzeDTO1).isNotEqualTo(schuetzeDTO2);
        schuetzeDTO2.setId(schuetzeDTO1.getId());
        assertThat(schuetzeDTO1).isEqualTo(schuetzeDTO2);
        schuetzeDTO2.setId(2L);
        assertThat(schuetzeDTO1).isNotEqualTo(schuetzeDTO2);
        schuetzeDTO1.setId(null);
        assertThat(schuetzeDTO1).isNotEqualTo(schuetzeDTO2);
    }
}
