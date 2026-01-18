package olegood.rgx.service.document.status.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.domain.document.guard.CanBeApprovedAutomatically;
import olegood.rgx.domain.document.guard.CanBeSubmitted;
import olegood.rgx.service.document.DocumentStatusService;
import olegood.rgx.service.document.status.Operation;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Submit implements Operation {

  private final DocumentStatusService documentStatusService;

  @Override
  public DocumentAction associatedAction() {
    return DocumentAction.SUBMIT;
  }

  @Override
  public boolean isEligible(Document document) {
    return new CanBeSubmitted().test(document);
  }

  @Override
  public void execute(Document document) {
    // candidate for submission
    var documentForSubmission = Optional.of(document);

    // submit if it can be submitted
    documentForSubmission.filter(new CanBeSubmitted()).ifPresent(documentStatusService::submit);

    // approve if it can be approved automatically
    documentForSubmission
        .filter(new CanBeApprovedAutomatically())
        .ifPresent(documentStatusService::approveAutomatically);
  }
}
