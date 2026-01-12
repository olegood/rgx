package olegood.rgx.probe;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.service.document.status.Operation;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DocumentActionsStartCheck {

  private final Set<Operation> operations;

  @EventListener(ApplicationStartedEvent.class)
  void ensureActionsMatchOperations() {
    if (operations.size() != DocumentAction.values().length) {
      throw new IllegalStateException("Number of operations does not match number of actions");
    }
  }
}
