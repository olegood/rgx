package olegood.rgx.domain.document;

import static olegood.rgx.domain.document.DocumentStatus.DRAFT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DocumentTest {

  @Test
  void initialStatusIsDraft() {
    // expect
    assertEquals(DRAFT, new Document().getStatus());
  }
}
