package olegood.rgx.service.document;

import static java.util.Collections.emptySet;
import static olegood.rgx.domain.document.DocumentAction.REOPEN;
import static olegood.rgx.domain.document.DocumentAction.SUBMIT;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentRepository;
import olegood.rgx.service.document.status.impl.Submit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DocumentStatusHandlerTest {

  private static final Long ID = 42L;

  @Mock private Submit submit;
  @Mock private DocumentRepository documentRepository;

  @Test
  void shouldThrowExceptionForUnknownAction() {
    // when
    var document = new Document().setId(ID);
    when(documentRepository.findById(ID)).thenReturn(Optional.of(document));

    // then
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(
            () ->
                new DocumentStatusHandler(emptySet(), documentRepository).handleAction(ID, REOPEN))
        .withMessage("Unknown action: REOPEN");
  }

  @Test
  void shouldExecuteOperationIfFound() {
    // given
    var document = new Document().setId(ID);
    when(documentRepository.findById(ID)).thenReturn(Optional.of(document));

    when(submit.associatedAction()).thenCallRealMethod();
    when(submit.isEligible()).thenReturn(doc -> true);

    // when
    new DocumentStatusHandler(Set.of(submit), documentRepository).handleAction(ID, SUBMIT);

    // then
    verify(submit).execute(document);
  }
}
