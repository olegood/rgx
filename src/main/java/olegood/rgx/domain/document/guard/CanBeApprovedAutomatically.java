package olegood.rgx.domain.document.guard;

import java.util.function.Predicate;
import olegood.rgx.domain.document.Document;

public final class CanBeApprovedAutomatically implements Predicate<Document> {

  @Override
  public boolean test(Document document) {
    return "VIP".equals(document.getOwner());
  }
}
