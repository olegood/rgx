package olegood.rgx.service.document.status;

import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;

public interface Operation {

  DocumentAction associatedAction();

  void execute(Document document);

  default boolean isAllowed(Document document) {
    return document.isActionAllowed(associatedAction());
  }

  default boolean isEligible(Document document) {
    return true;
  }
}
