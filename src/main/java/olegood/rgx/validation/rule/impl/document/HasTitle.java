package olegood.rgx.validation.rule.impl.document;

import static java.util.function.Predicate.not;

import java.util.Optional;
import olegood.rgx.domain.document.Document;
import olegood.rgx.validation.rule.Rule;

public class HasTitle extends Rule<Document> {

  public HasTitle() {
    super("Document must have a title.");
  }

  @Override
  public boolean test(Document document) {
    return Optional.of(document).map(Document::getTitle).filter(not(String::isBlank)).isPresent();
  }
}
