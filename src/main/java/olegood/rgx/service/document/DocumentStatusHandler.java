package olegood.rgx.service.document;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.domain.document.DocumentRepository;
import olegood.rgx.service.document.status.Operation;
import olegood.rgx.service.document.status.OperationDecorator;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DocumentStatusHandler {

  private final Set<Operation> operations;
  private final DocumentRepository documentRepository;

  public void handleAction(Long documentId, DocumentAction action) {
    var document = documentRepository.findById(documentId).orElseThrow();
    findOperationByAction(action).execute(document);
  }

  private Operation findOperationByAction(final DocumentAction action) {
    return operations.stream()
        .filter(operation -> operation.associatedAction().equals(action))
        .findAny()
        .map(OperationDecorator::new)
        .orElseThrow(() -> new UnsupportedOperationException("Unknown action: " + action));
  }
}
