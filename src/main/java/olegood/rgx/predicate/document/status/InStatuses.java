package olegood.rgx.predicate.document.status;

import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentStatus;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public record InStatuses(Set<DocumentStatus> statuses) implements Predicate<Document> {

    public InStatuses(DocumentStatus status, DocumentStatus... statuses) {
        this(Stream.concat(Stream.of(status), Stream.of(statuses)).collect(toSet()));
    }

    @Override
    public boolean test(Document businessPlan) {
        return Optional.ofNullable(businessPlan)
                .map(Document::getStatus)
                .filter(statuses::contains)
                .isPresent();
    }
}
