package olegood.rgx.domain.document;

import static olegood.rgx.domain.document.DocumentStatus.DRAFT;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DocumentTest {

  @Test
  void initialStatusIsDraft() {
    // expect
    assertThat(new Document().getStatus()).isEqualTo(DRAFT);
  }
}
