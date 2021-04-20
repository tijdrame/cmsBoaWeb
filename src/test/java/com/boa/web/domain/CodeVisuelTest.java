package com.boa.web.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.boa.web.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CodeVisuelTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(CodeVisuel.class);
    CodeVisuel codeVisuel1 = new CodeVisuel();
    codeVisuel1.setId(1L);
    CodeVisuel codeVisuel2 = new CodeVisuel();
    codeVisuel2.setId(codeVisuel1.getId());
    assertThat(codeVisuel1).isEqualTo(codeVisuel2);
    codeVisuel2.setId(2L);
    assertThat(codeVisuel1).isNotEqualTo(codeVisuel2);
    codeVisuel1.setId(null);
    assertThat(codeVisuel1).isNotEqualTo(codeVisuel2);
  }
}
