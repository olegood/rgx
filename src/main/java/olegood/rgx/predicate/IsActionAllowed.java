package olegood.rgx.predicate;

import java.util.Optional;
import java.util.function.Predicate;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;

public record IsActionAllowed(DocumentAction action) implements Predicate<Document> {

  @Override
  public boolean test(Document document) {
    return Optional.ofNullable(document)
        .map(Document::getStatus)
        .map(status -> status.isActionAllowed(action))
        .orElse(false);
  }
}
