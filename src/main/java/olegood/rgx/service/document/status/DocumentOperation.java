package olegood.rgx.service.document.status;

import java.util.function.Predicate;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;

public interface DocumentOperation {

  DocumentAction associatedAction();

  void execute(Document document);

  default void executeValidated(Document document) {
    validateAllowed(document);
    validateEligible(document);
    execute(document);
  }

  default Predicate<Document> isEligible() {
    return document -> true;
  }

  private Predicate<Document> isAllowed() {
    return document -> document.isActionAllowed(associatedAction());
  }

  private void validateAllowed(Document document) {
    var isNotAllowed = isAllowed().negate();
    if (isNotAllowed.test(document)) {
      throw new UnsupportedOperationException("Operation not allowed: " + associatedAction());
    }
  }

  private void validateEligible(Document document) {
    var isNotEligible = isEligible().negate();
    if (isNotEligible.test(document)) {
      throw new UnsupportedOperationException("Operation not eligible: " + associatedAction());
    }
  }
}
