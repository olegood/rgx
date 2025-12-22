package olegood.rgx.predicate;

import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;

import java.util.Optional;
import java.util.function.Predicate;

public record IsActionAllowed(DocumentAction action) implements Predicate<Document> {

    @Override
    public boolean test(Document document) {
        return Optional.ofNullable(document)
                .map(Document::getStatus)
                .map(status -> status.isActionAllowed(action))
                .orElse(false);
    }
}
