package olegood.rgx.outbox;

import lombok.extern.slf4j.Slf4j;
import olegood.rgx.event.DocumentSubmittedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DocumentOutboxService {

  @EventListener
  public void handleDocumentSubmitted(DocumentSubmittedEvent event) {
    log.info("Document submitted: {}", event.documentId());
  }
}
