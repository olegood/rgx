package olegood.rgx.predicate.document.status;

import olegood.rgx.domain.document.Document;

import java.util.function.Predicate;

import static olegood.rgx.domain.document.DocumentStatus.DRAFT;

public final class CanBeSubmitted implements Predicate<Document> {

    @Override
    public boolean test(Document document) {
        return new InStatuses(DRAFT)
                .test(document);
    }
}
