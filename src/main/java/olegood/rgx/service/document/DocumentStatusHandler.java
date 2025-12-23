package olegood.rgx.service.document;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.domain.document.DocumentRepository;
import olegood.rgx.service.document.status.DocumentOperation;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DocumentStatusHandler {

  private final Set<DocumentOperation> operations;
  private final DocumentRepository documentRepository;

  public void handleAction(Long id, DocumentAction action) {
    var document = documentRepository.findById(id).orElseThrow();
    findOperationByAction(action).executeValidated(document);
  }

  private DocumentOperation findOperationByAction(final DocumentAction action) {
    return operations.stream()
        .filter(operation -> operation.associatedAction().equals(action))
        .findAny()
        .orElseThrow(() -> new UnsupportedOperationException("Unknown action: " + action));
  }
}
