package olegood.rgx.service.document;

import static olegood.rgx.domain.document.DocumentStatus.APPROVED;
import static olegood.rgx.domain.document.DocumentStatus.ARCHIVED;
import static olegood.rgx.domain.document.DocumentStatus.DRAFT;
import static olegood.rgx.domain.document.DocumentStatus.IN_REVIEW;
import static olegood.rgx.domain.document.DocumentStatus.TERMINATED;

import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentRepository;
import olegood.rgx.domain.document.DocumentStatus;
import olegood.rgx.event.document.DocumentSubmittedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class DocumentStatusServiceImpl implements DocumentStatusService {

  private final DocumentRepository documentRepository;
  private final ApplicationEventPublisher events;

  @Override
  public void submit(Document document) {
    changeStatus(document, IN_REVIEW);
    events.publishEvent(new DocumentSubmittedEvent(document));
  }

  @Override
  public void approve(Document document) {
    changeStatus(document, APPROVED);
  }

  @Override
  public void approveAutomatically(Document document) {
    // @PreAuthorize self-invocation.
    // The annotation will be ignored at runtime.
    approve(document);
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
    var status = document.getStatus();
    switch (status) {
      case APPROVED -> changeStatus(document, IN_REVIEW);
      case TERMINATED -> changeStatus(document, DRAFT);
      default -> throw new IllegalArgumentException("Cannot reopen document in status " + status);
    }
  }

  private void changeStatus(Document document, DocumentStatus status) {
    document.setStatus(status);
    documentRepository.save(document);
  }
}
