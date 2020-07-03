package com.boa.web.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.boa.web.web.rest.TestUtil;

public class ParamIdentifierTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParamIdentifier.class);
        ParamIdentifier paramIdentifier1 = new ParamIdentifier();
        paramIdentifier1.setId(1L);
        ParamIdentifier paramIdentifier2 = new ParamIdentifier();
        paramIdentifier2.setId(paramIdentifier1.getId());
        assertThat(paramIdentifier1).isEqualTo(paramIdentifier2);
        paramIdentifier2.setId(2L);
        assertThat(paramIdentifier1).isNotEqualTo(paramIdentifier2);
        paramIdentifier1.setId(null);
        assertThat(paramIdentifier1).isNotEqualTo(paramIdentifier2);
    }
}
