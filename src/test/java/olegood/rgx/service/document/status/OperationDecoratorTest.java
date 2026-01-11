package olegood.rgx.service.document.status;

import static olegood.rgx.domain.document.DocumentAction.TERMINATE;
import static olegood.rgx.domain.document.DocumentStatus.DRAFT;
import static org.assertj.core.api.Assertions.assertThat;
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
  void shouldDelegateAssociatedActionAsIs() {
    // given
    var approve = new Approve(documentStatusService);

    // when
    var decorator = new OperationDecorator(new Approve(documentStatusService));

    // then
    assertThat(decorator.associatedAction()).isEqualTo(approve.associatedAction());
  }

  @Test
  void shouldThrowExceptionIfNotAllowed() {
    // given
    var document = new Document().setId(42L).setStatus(DRAFT);

    // when
    var approve = new OperationDecorator(new Approve(documentStatusService));

    // then
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> approve.execute(document))
        .withMessage(
            "Cannot execute operation 'APPROVE' on document [ID: 42]: current status 'DRAFT' does not allow this action.");
  }

  @Test
  void shouldThrowExceptionIfNotEligible() {
    // given
    var document = new Document().setId(56L).setStatus(DRAFT);

    // when
    var submit = new OperationDecorator(new Submit(documentStatusService));

    // then
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> submit.execute(document))
        .withMessage(
            "Document [ID: 56] does not meet the minimal criteria to be processed via action: 'SUBMIT'");
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
