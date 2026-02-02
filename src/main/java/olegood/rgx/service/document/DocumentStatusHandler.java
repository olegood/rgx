package olegood.rgx.service.document;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.domain.document.DocumentRepository;
import olegood.rgx.service.document.status.Command;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DocumentStatusHandler {

  private final Set<Command> commands;
  private final DocumentRepository documentRepository;

  public Document handleAction(Long documentId, DocumentAction action) {
    var document = documentRepository.findById(documentId).orElseThrow();
    getCommandFor(action).execute(document);
    return document;
  }

  private Command getCommandFor(final DocumentAction action) {
    return commands.stream()
        .filter(command -> command.action().equals(action))
        .findAny()
        .orElseThrow(() -> new UnsupportedOperationException("Unknown action: " + action));
  }
}
