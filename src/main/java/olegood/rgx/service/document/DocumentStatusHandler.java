package olegood.rgx.service.document;

import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.domain.document.DocumentRepository;
import olegood.rgx.service.document.status.DocumentOperation;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Component
public class DocumentStatusHandler {

    private final Set<DocumentOperation> operations;
    private final DocumentRepository documentRepository;

    public void handleAction(Long id, DocumentAction action) {
        var document = documentRepository.findById(id).orElseThrow();

        var operation = findOperationByAction(action);

        validateOperationAllowed(document, operation);
        validateOperationEligible(document, operation);

        operation.execute(document);
    }

    private DocumentOperation findOperationByAction(final DocumentAction action) {
        return operations.stream()
                .filter(operation -> operation.associatedAction().equals(action))
                .findAny()
                .orElseThrow(() -> new UnsupportedOperationException("Unknown action: " + action));
    }

    public void validateOperationAllowed(Document document, DocumentOperation operation) {
        Predicate<Document> isNotAllowed = operation.isAllowed().negate();
        if (isNotAllowed.test(document)) {
            throw new UnsupportedOperationException("Operation not allowed: " + operation.associatedAction());
        }
    }

    public void validateOperationEligible(Document document, DocumentOperation operation) {
        Predicate<Document> isNotEligible = operation.isEligible().negate();
        if (isNotEligible.test(document)) {
            throw new UnsupportedOperationException("Operation not eligible: " + operation.associatedAction());
        }
    }

}
