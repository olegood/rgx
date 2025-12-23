package olegood.rgx.service.document.status.impl;

import static olegood.rgx.domain.document.DocumentAction.TERMINATE;
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
class TerminateTest {

  @Mock private DocumentStatusService documentStatusService;

  @InjectMocks private Terminate terminate;

  @Test
  void associatedActionIsTerminate() {
    // when
    var action = terminate.associatedAction();

    // then
    assertThat(action).isEqualTo(TERMINATE);
  }

  @Test
  void shouldCallTerminate() {
    // when
    var document = new Document();

    // then
    terminate.execute(document);

    // then
    verify(documentStatusService).terminate(document);
  }
}
