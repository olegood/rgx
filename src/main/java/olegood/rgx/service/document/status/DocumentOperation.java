package olegood.rgx.service.document.status;

import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;

import java.util.function.Predicate;

public interface DocumentOperation {

    DocumentAction associatedAction();

    void execute(Document document);

    default Predicate<Document> isAllowed() {
        return doc -> doc.isActionAllowed(associatedAction());
    }

    default Predicate<Document> isEligible() {
        return doc -> true;
    }
}
