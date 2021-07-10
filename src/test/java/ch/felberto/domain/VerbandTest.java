package ch.felberto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ch.felberto.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerbandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verband.class);
        Verband verband1 = new Verband();
        verband1.setId(1L);
        Verband verband2 = new Verband();
        verband2.setId(verband1.getId());
        assertThat(verband1).isEqualTo(verband2);
        verband2.setId(2L);
        assertThat(verband1).isNotEqualTo(verband2);
        verband1.setId(null);
        assertThat(verband1).isNotEqualTo(verband2);
    }
}
