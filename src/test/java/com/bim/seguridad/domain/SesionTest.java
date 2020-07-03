package com.bim.seguridad.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bim.seguridad.web.rest.TestUtil;

public class SesionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sesion.class);
        Sesion sesion1 = new Sesion();
        sesion1.setId(1L);
        Sesion sesion2 = new Sesion();
        sesion2.setId(sesion1.getId());
        assertThat(sesion1).isEqualTo(sesion2);
        sesion2.setId(2L);
        assertThat(sesion1).isNotEqualTo(sesion2);
        sesion1.setId(null);
        assertThat(sesion1).isNotEqualTo(sesion2);
    }
}
