package olegood.rgx.validation.rule.impl.document;

import static java.util.function.Predicate.not;

import java.util.Optional;
import olegood.rgx.domain.document.Document;
import olegood.rgx.validation.rule.Rule;

public class HasOwner extends Rule<Document> {

  public HasOwner() {
    super("Document must have an owner.");
  }

  @Override
  public boolean test(Document document) {
    return Optional.of(document).map(Document::getOwner).filter(not(String::isBlank)).isPresent();
  }
}
