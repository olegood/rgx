package olegood.rgx.service.document.status;

import java.util.function.Predicate;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;

public interface Operation {

  DocumentAction associatedAction();

  void execute(Document document);

  default Predicate<Document> isEligible() {
    return document -> true;
  }
}
