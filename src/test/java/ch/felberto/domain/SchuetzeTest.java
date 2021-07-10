package ch.felberto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SchuetzeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Schuetze.class);
        Schuetze schuetze1 = new Schuetze();
        schuetze1.setId(1L);
        Schuetze schuetze2 = new Schuetze();
        schuetze2.setId(schuetze1.getId());
        assertThat(schuetze1).isEqualTo(schuetze2);
        schuetze2.setId(2L);
        assertThat(schuetze1).isNotEqualTo(schuetze2);
        schuetze1.setId(null);
        assertThat(schuetze1).isNotEqualTo(schuetze2);
    }
}
