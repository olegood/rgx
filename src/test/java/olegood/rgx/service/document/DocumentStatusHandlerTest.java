package olegood.rgx.service.document;

import static olegood.rgx.domain.document.DocumentAction.REOPEN;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentRepository;
import olegood.rgx.service.document.status.Operation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DocumentStatusHandlerTest {

  private static final Long ID = 42L;

  @Mock private Set<Operation> operations;

  @Mock private DocumentRepository documentRepository;

  @InjectMocks private DocumentStatusHandler documentStatusHandler;

  @Test
  void shouldThrowExceptionForUnknownAction() {
    // when
    var document = new Document();
    when(documentRepository.findById(ID)).thenReturn(Optional.of(document));

    // then
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> documentStatusHandler.handleAction(ID, REOPEN))
        .withMessage("Unknown action: REOPEN");
  }
}
