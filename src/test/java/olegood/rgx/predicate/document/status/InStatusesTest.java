package olegood.rgx.predicate.document.status;

import static olegood.rgx.domain.document.DocumentStatus.APPROVED;
import static olegood.rgx.domain.document.DocumentStatus.DRAFT;
import static olegood.rgx.domain.document.DocumentStatus.IN_REVIEW;
import static org.assertj.core.api.Assertions.assertThat;

import olegood.rgx.domain.document.Document;
import org.junit.jupiter.api.Test;

class InStatusesTest {

  private final InStatuses predicate = new InStatuses(DRAFT, IN_REVIEW);

  @Test
  void shouldReturnTrueWhenDocumentInStatuses() {
    // when
    var document = new Document().setStatus(IN_REVIEW);

    // then
    assertThat(predicate.test(document)).isTrue();
  }

  @Test
  void shouldReturnFalseWhenDocumentNotInStatuses() {
    // when
    var document = new Document().setStatus(APPROVED);

    // then
    assertThat(predicate.test(document)).isFalse();
  }

  @Test
  void shouldReturnFalseWhenDocumentHasNullStatus() {
    // when
    var document = new Document().setStatus(null);

    // then
    assertThat(predicate.test(document)).isFalse();
  }

  @Test
  void shouldReturnFalseWhenDocumentIsNull() {
    // expect
    assertThat(predicate.test(null)).isFalse();
  }
}
