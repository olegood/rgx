package olegood.rgx.service.document.status;

import static olegood.rgx.domain.document.DocumentAction.REJECT;
import static olegood.rgx.domain.document.DocumentAction.SUBMIT;
import static olegood.rgx.domain.document.DocumentStatus.DRAFT;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import olegood.rgx.domain.document.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DocumentOperationTest {

  @Spy private DocumentOperation operation;

  @Test
  void shouldValidateBeforeExecuteValidated() {
    // given
    var document = new Document().setStatus(DRAFT);
    when(operation.associatedAction()).thenReturn(SUBMIT);

    // when
    operation.executeValidated(document);

    // then
    InOrder inOrder = inOrder(operation);
    inOrder.verify(operation).validateAllowed(document);
    inOrder.verify(operation).validateEligible(document);
  }

  @Test
  void shouldNotValidateIfExecute() {
    // given
    var document = new Document().setStatus(DRAFT);

    // when
    operation.execute(document);

    // then
    InOrder inOrder = inOrder(operation);
    inOrder.verify(operation, never()).validateAllowed(document);
    inOrder.verify(operation, never()).validateEligible(document);
  }

  @Test
  void shouldThrowExceptionIfNotAllowed() {
    // when
    var document = new Document().setStatus(DRAFT);
    when(operation.associatedAction()).thenReturn(REJECT);

    // then
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> operation.executeValidated(document))
        .withMessage("Operation not allowed: REJECT");
  }

  @Test
  void shouldThrowExceptionIfNotEligible() {
    // when
    var document = new Document().setStatus(DRAFT);
    when(operation.associatedAction()).thenReturn(SUBMIT);
    when(operation.isEligible()).thenReturn(it -> false);

    // then
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> operation.executeValidated(document))
        .withMessage("Operation not eligible: SUBMIT");
  }
}
