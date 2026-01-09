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
    ensureOperationAllowed(document);
    ensureOperationEligible(document);
    operation.execute(document);
  }

  private void ensureOperationAllowed(Document document) {
    if (!document.isActionAllowed(operation.associatedAction())) {
      throw new UnsupportedOperationException(
          "Operation not allowed: " + operation.associatedAction());
    }
  }

  private void ensureOperationEligible(Document document) {
    if (!operation.isEligible().test(document)) {
      throw new UnsupportedOperationException(
          "Operation not eligible: " + operation.associatedAction());
    }
  }
}
