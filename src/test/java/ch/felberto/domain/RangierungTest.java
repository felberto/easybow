package ch.felberto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RangierungTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rangierung.class);
        Rangierung rangierung1 = new Rangierung();
        rangierung1.setId(1L);
        Rangierung rangierung2 = new Rangierung();
        rangierung2.setId(rangierung1.getId());
        assertThat(rangierung1).isEqualTo(rangierung2);
        rangierung2.setId(2L);
        assertThat(rangierung1).isNotEqualTo(rangierung2);
        rangierung1.setId(null);
        assertThat(rangierung1).isNotEqualTo(rangierung2);
    }
}
