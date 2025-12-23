package olegood.rgx.service.document.status.impl;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.service.document.DocumentStatusService;
import olegood.rgx.service.document.status.DocumentOperation;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Submit implements DocumentOperation {

  private final DocumentStatusService documentStatusService;

  @Override
  public DocumentAction associatedAction() {
    return DocumentAction.SUBMIT;
  }

  @Override
  public Predicate<Document> isEligible() {
    return Document::canBeSubmitted;
  }

  @Override
  public void execute(Document document) {
    // submit if it can be submitted
    Optional.of(document).filter(Document::canBeSubmitted).ifPresent(documentStatusService::submit);

    // approve if it can be approved automatically
    Optional.of(document)
        .filter(Document::canBeApprovedAutomatically)
        .ifPresent(documentStatusService::approveAutomatically);
  }
}
