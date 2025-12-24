package olegood.rgx.service.document.status;

import static org.assertj.core.api.Assertions.assertThat;

import olegood.rgx.domain.document.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OperationTest {

  @Spy private Operation operation;

  @Test
  void anyOperationIsEligibleByDefault() {
    // expect
    assertThat(operation.isEligible().test(new Document())).isTrue();
  }
}
