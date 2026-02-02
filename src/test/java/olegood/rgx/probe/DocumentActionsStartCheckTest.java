package olegood.rgx.probe;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.when;

import java.util.Set;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.service.document.status.Command;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DocumentActionsStartCheckTest {

  @Mock private Set<Command> commands;

  @Test
  void shouldNotThrowExceptionWhenNumberOfCommandsMatchesNumberOfActions() {
    // when
    when(commands.size()).thenReturn(DocumentAction.values().length);
    var check = new DocumentActionsStartCheck(commands);

    // then
    assertThatNoException().isThrownBy(check::ensureCommandsMatchActions);
  }

  @Test
  void shouldThrowExceptionWhenNumberOfCommandsDoesNotMatchNumberOfActions() {
    // when
    when(commands.size()).thenReturn(-1);
    var check = new DocumentActionsStartCheck(commands);

    // then
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(check::ensureCommandsMatchActions)
        .withMessage("Number of commands does not match number of actions");
  }
}
