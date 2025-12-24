package olegood.rgx.service.document.status;

import static olegood.rgx.domain.document.DocumentAction.TERMINATE;
import static olegood.rgx.domain.document.DocumentStatus.DRAFT;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import olegood.rgx.domain.document.Document;
import olegood.rgx.service.document.DocumentStatusService;
import olegood.rgx.service.document.status.impl.Approve;
import olegood.rgx.service.document.status.impl.Submit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OperationDecoratorTest {

  @Mock private DocumentStatusService documentStatusService;

  @Mock private Operation operation;

  @Test
  void shouldThrowExceptionIfNotAllowed() {
    // given
    var document = new Document().setStatus(DRAFT);

    // when
    var approve = new OperationDecorator(new Approve(documentStatusService));

    // then
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> approve.execute(document))
        .withMessage("Operation not allowed: APPROVE");
  }

  @Test
  void shouldThrowExceptionIfNotEligible() {
    // given
    var document = new Document().setStatus(DRAFT);

    // when
    var submit = new OperationDecorator(new Submit(documentStatusService));

    // then
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> submit.execute(document))
        .withMessage("Operation not eligible: SUBMIT");
  }

  @Test
  void shouldDelegateExecutionIfValid() {
    // given
    var document = new Document().setStatus(DRAFT);

    when(operation.associatedAction()).thenReturn(TERMINATE);
    when(operation.isEligible()).thenReturn(doc -> true);
    var decorator = new OperationDecorator(operation);

    // when
    decorator.execute(document);

    // then
    verify(operation).execute(document);
  }
}
