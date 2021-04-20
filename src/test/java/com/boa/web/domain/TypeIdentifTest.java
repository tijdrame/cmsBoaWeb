package com.boa.web.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.boa.web.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeIdentifTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(TypeIdentif.class);
    TypeIdentif typeIdentif1 = new TypeIdentif();
    typeIdentif1.setId(1L);
    TypeIdentif typeIdentif2 = new TypeIdentif();
    typeIdentif2.setId(typeIdentif1.getId());
    assertThat(typeIdentif1).isEqualTo(typeIdentif2);
    typeIdentif2.setId(2L);
    assertThat(typeIdentif1).isNotEqualTo(typeIdentif2);
    typeIdentif1.setId(null);
    assertThat(typeIdentif1).isNotEqualTo(typeIdentif2);
  }
}
