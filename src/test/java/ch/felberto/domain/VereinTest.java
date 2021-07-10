package ch.felberto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VereinTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verein.class);
        Verein verein1 = new Verein();
        verein1.setId(1L);
        Verein verein2 = new Verein();
        verein2.setId(verein1.getId());
        assertThat(verein1).isEqualTo(verein2);
        verein2.setId(2L);
        assertThat(verein1).isNotEqualTo(verein2);
        verein1.setId(null);
        assertThat(verein1).isNotEqualTo(verein2);
    }
}
