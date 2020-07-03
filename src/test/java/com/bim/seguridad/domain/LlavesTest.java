package com.bim.seguridad.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bim.seguridad.web.rest.TestUtil;

public class LlavesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Llaves.class);
        Llaves llaves1 = new Llaves();
        llaves1.setId(1L);
        Llaves llaves2 = new Llaves();
        llaves2.setId(llaves1.getId());
        assertThat(llaves1).isEqualTo(llaves2);
        llaves2.setId(2L);
        assertThat(llaves1).isNotEqualTo(llaves2);
        llaves1.setId(null);
        assertThat(llaves1).isNotEqualTo(llaves2);
    }
}
