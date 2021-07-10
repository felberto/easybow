package ch.felberto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PassenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Passen.class);
        Passen passen1 = new Passen();
        passen1.setId(1L);
        Passen passen2 = new Passen();
        passen2.setId(passen1.getId());
        assertThat(passen1).isEqualTo(passen2);
        passen2.setId(2L);
        assertThat(passen1).isNotEqualTo(passen2);
        passen1.setId(null);
        assertThat(passen1).isNotEqualTo(passen2);
    }
}
