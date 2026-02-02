package olegood.rgx.validation.rule.impl.document;

import static olegood.rgx.domain.document.DocumentStatus.DRAFT;

import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.guard.InStatuses;
import olegood.rgx.validation.rule.Rule;

public class IsSubmitReady extends Rule<Document> {

  public IsSubmitReady() {
    super("Document must be in working state.");
  }

  @Override
  public boolean test(Document document) {
    return new InStatuses(DRAFT).test(document);
  }
}
