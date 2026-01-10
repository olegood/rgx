package olegood.rgx.outbox;

import lombok.extern.slf4j.Slf4j;
import olegood.rgx.event.document.DocumentSubmittedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
public class OutboxService {

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void onDocumentSubmitted(DocumentSubmittedEvent event) {
    log.info("Submitted document: {}", event.document().getId());
  }
}
