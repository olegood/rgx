package olegood.rgx.predicate.document.status;

import static olegood.rgx.domain.document.DocumentStatus.APPROVED;
import static olegood.rgx.domain.document.DocumentStatus.DRAFT;
import static org.assertj.core.api.Assertions.assertThat;

import olegood.rgx.domain.document.Document;
import org.junit.jupiter.api.Test;

class CanBeSubmittedTest {

  @Test
  void shouldReturnTrueWhenDocumentInDraft() {
    // when
    var document = new Document().setStatus(DRAFT);

    // then
    assertThat(new CanBeSubmitted().test(document)).isTrue();
  }

  @Test
  void shouldReturnTrueWhenDocumentNotInDraft() {
    // when
    var document = new Document().setStatus(APPROVED);

    // then
    assertThat(new CanBeSubmitted().test(document)).isFalse();
  }
}
