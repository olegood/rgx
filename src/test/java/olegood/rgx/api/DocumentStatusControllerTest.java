package olegood.rgx.api;

import static org.mockito.Mockito.verify;

import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.service.document.DocumentStatusHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DocumentStatusControllerTest {

  @Mock private DocumentStatusHandler documentStatusHandler;

  @InjectMocks private DocumentStatusController documentStatusController;

  @Test
  void shouldCallDocumentStatusHandler() {
    // given
    var id = 42L;
    var action = DocumentAction.APPROVE;

    // when
    documentStatusController.handleDocumentAction(id, action);

    // then
    verify(documentStatusHandler).handleAction(id, action);
  }
}
