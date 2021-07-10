package ch.felberto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WettkampfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wettkampf.class);
        Wettkampf wettkampf1 = new Wettkampf();
        wettkampf1.setId(1L);
        Wettkampf wettkampf2 = new Wettkampf();
        wettkampf2.setId(wettkampf1.getId());
        assertThat(wettkampf1).isEqualTo(wettkampf2);
        wettkampf2.setId(2L);
        assertThat(wettkampf1).isNotEqualTo(wettkampf2);
        wettkampf1.setId(null);
        assertThat(wettkampf1).isNotEqualTo(wettkampf2);
    }
}
