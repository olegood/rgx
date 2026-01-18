package olegood.rgx.service.outbox;

import lombok.extern.slf4j.Slf4j;
import olegood.rgx.domain.document.event.DocumentSubmitted;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
public class OutboxService {

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void onDocumentSubmitted(DocumentSubmitted event) {
    log.info("Submitted document: {}", event.document().getId());
  }
}
