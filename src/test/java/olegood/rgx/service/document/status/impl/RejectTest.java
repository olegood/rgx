package olegood.rgx.service.document.status.impl;

import static olegood.rgx.domain.document.DocumentAction.REJECT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import olegood.rgx.domain.document.Document;
import olegood.rgx.service.document.DocumentStatusService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RejectTest {

  @Mock private DocumentStatusService documentStatusService;

  @InjectMocks private Reject reject;

  @Test
  void actionIsReject() {
    // when
    var action = reject.action();

    // then
    assertThat(action).isEqualTo(REJECT);
  }

  @Test
  void shouldCallReject() {
    // when
    var document = new Document();

    // then
    reject.accept(document);

    // then
    verify(documentStatusService).reject(document);
  }
}
