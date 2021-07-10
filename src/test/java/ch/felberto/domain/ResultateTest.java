package ch.felberto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResultateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resultate.class);
        Resultate resultate1 = new Resultate();
        resultate1.setId(1L);
        Resultate resultate2 = new Resultate();
        resultate2.setId(resultate1.getId());
        assertThat(resultate1).isEqualTo(resultate2);
        resultate2.setId(2L);
        assertThat(resultate1).isNotEqualTo(resultate2);
        resultate1.setId(null);
        assertThat(resultate1).isNotEqualTo(resultate2);
    }
}
