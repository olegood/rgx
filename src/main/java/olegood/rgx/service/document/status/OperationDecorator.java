package olegood.rgx.service.document.status;

import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;

public record OperationDecorator(Operation delegate) implements Operation {

  @Override
  public DocumentAction associatedAction() {
    return delegate.associatedAction();
  }

  @Override
  public void execute(Document document) {
    validateAllowed(document);
    validateEligible(document);
    delegate.execute(document);
  }

  private void validateAllowed(Document document) {
    if (!document.isActionAllowed(delegate.associatedAction())) {
      throw new UnsupportedOperationException(
          "Operation not allowed: " + delegate.associatedAction());
    }
  }

  private void validateEligible(Document document) {
    if (!delegate.isEligible().test(document)) {
      throw new UnsupportedOperationException(
          "Operation not eligible: " + delegate.associatedAction());
    }
  }
}
