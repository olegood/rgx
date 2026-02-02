package olegood.rgx.probe;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.service.document.status.Command;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DocumentActionsStartCheck {

  private final Set<Command> commands;

  @EventListener(ApplicationStartedEvent.class)
  void ensureCommandsMatchActions() {
    if (commands.size() != DocumentAction.values().length) {
      throw new IllegalStateException("Number of commands does not match number of actions");
    }
  }
}
