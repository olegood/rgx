package olegood.rgx.service.document.status;

import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;

public record OperationDecorator(Operation operation) implements Operation {

  @Override
  public DocumentAction associatedAction() {
    return operation.associatedAction();
  }

  @Override
  public void execute(Document document) {
    ensureOperationIsAllowed(document);
    ensureOperationIsEligible(document);
    operation.execute(document);
  }

  private void ensureOperationIsAllowed(Document document) {
    if (!document.isActionAllowed(operation.associatedAction())) {
      throw new UnsupportedOperationException(
          "Cannot execute operation '%s' on document [ID: %s]: current status '%s' does not allow this action."
              .formatted(operation.associatedAction(), document.getId(), document.getStatus()));
    }
  }

  private void ensureOperationIsEligible(Document document) {
    if (!operation.isEligible().test(document)) {
      throw new UnsupportedOperationException(
          "Document [ID: %s] does not meet the minimal criteria to be processed via action: '%s'"
              .formatted(document.getId(), operation.associatedAction()));
    }
  }
}
