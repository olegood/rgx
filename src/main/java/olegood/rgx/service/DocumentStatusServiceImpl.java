package olegood.rgx.service;

import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentRepository;
import olegood.rgx.domain.document.DocumentStatus;
import org.springframework.stereotype.Component;

import static olegood.rgx.domain.document.DocumentStatus.*;

@RequiredArgsConstructor
@Component
public class DocumentStatusServiceImpl implements DocumentStatusService {

    private final DocumentRepository documentRepository;

    @Override
    public void submit(Document document) {
        changeStatus(document, IN_REVIEW);
    }

    @Override
    public void approve(Document document) {
        changeStatus(document, APPROVED);
    }

    @Override
    public void reject(Document document) {
        changeStatus(document, DRAFT);
    }

    @Override
    public void terminate(Document document) {
        changeStatus(document, TERMINATED);
    }

    @Override
    public void archive(Document document) {
        changeStatus(document, ARCHIVED);
    }

    @Override
    public void reopen(Document document) {
        switch (document.getStatus()) {
            case APPROVED -> changeStatus(document, IN_REVIEW);
            case TERMINATED -> changeStatus(document, DRAFT);
        }
    }

    private void changeStatus(Document document, DocumentStatus status) {
        document.setStatus(status);
        documentRepository.save(document);
    }
}
