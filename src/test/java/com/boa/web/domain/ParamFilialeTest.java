package com.boa.web.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.boa.web.web.rest.TestUtil;

public class ParamFilialeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParamFiliale.class);
        ParamFiliale paramFiliale1 = new ParamFiliale();
        paramFiliale1.setId(1L);
        ParamFiliale paramFiliale2 = new ParamFiliale();
        paramFiliale2.setId(paramFiliale1.getId());
        assertThat(paramFiliale1).isEqualTo(paramFiliale2);
        paramFiliale2.setId(2L);
        assertThat(paramFiliale1).isNotEqualTo(paramFiliale2);
        paramFiliale1.setId(null);
        assertThat(paramFiliale1).isNotEqualTo(paramFiliale2);
    }
}
