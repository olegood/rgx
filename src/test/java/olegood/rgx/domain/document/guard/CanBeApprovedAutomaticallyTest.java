package olegood.rgx.domain.document.guard;

import static org.assertj.core.api.Assertions.assertThat;

import olegood.rgx.domain.document.Document;
import org.junit.jupiter.api.Test;

class CanBeApprovedAutomaticallyTest {

  @Test
  void shouldReturnTrueWhenOwnerIsVIP() {
    // when
    var document = new Document().setOwner("VIP");

    // then
    assertThat(new CanBeApprovedAutomatically().test(document)).isTrue();
  }

  @Test
  void shouldReturnFalseWhenOwnerIsNotVIP() {
    // when
    var document = new Document().setOwner("STANDARD");

    // then
    assertThat(new CanBeApprovedAutomatically().test(document)).isFalse();
  }
}
