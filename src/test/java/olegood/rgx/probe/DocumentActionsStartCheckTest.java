package olegood.rgx.probe;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.when;

import java.util.Set;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.service.document.status.Operation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DocumentActionsStartCheckTest {

  @Mock private Set<Operation> operations;

  @Test
  void shouldNotThrowExceptionWhenNumberOfOperationsMatchesNumberOfActions() {
    // when
    when(operations.size()).thenReturn(DocumentAction.values().length);

    // then
    assertThatNoException()
        .isThrownBy(() -> new DocumentActionsStartCheck(operations).ensureActionsMatchOperations());
  }

  @Test
  void shouldThrowExceptionWhenNumberOfOperationsDoesNotMatchNumberOfActions() {
    // when
    when(operations.size()).thenReturn(-1);

    // then
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(() -> new DocumentActionsStartCheck(operations).ensureActionsMatchOperations())
        .withMessage("Number of operations does not match number of actions");
  }
}
