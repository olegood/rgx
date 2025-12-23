package olegood.rgx.predicate.document.status;

import static olegood.rgx.domain.document.DocumentStatus.DRAFT;

import java.util.function.Predicate;
import olegood.rgx.domain.document.Document;

public final class CanBeSubmitted implements Predicate<Document> {

  @Override
  public boolean test(Document document) {
    return new InStatuses(DRAFT).test(document);
  }
}
