package olegood.rgx.service.document.status.impl;

import static olegood.rgx.domain.document.DocumentAction.SUBMIT;
import static olegood.rgx.domain.document.DocumentStatus.DRAFT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;

import olegood.rgx.api.ApplicationException;
import olegood.rgx.domain.document.Document;
import olegood.rgx.service.document.DocumentStatusService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubmitTest {

  @Mock private DocumentStatusService documentStatusService;

  @InjectMocks private Submit submit;

  @Test
  void actionIsSubmit() {
    // when
    var action = submit.action();

    // then
    assertThat(action).isEqualTo(SUBMIT);
  }

  //  @Test
  //  void shouldReturnTrueWhenDocumentIsEligibleForSubmit() {
  //    // given
  //    var eligibleDocument = new Document().setStatus(DRAFT).setTitle("<title>");
  //
  //    // when
  //    boolean isEligible = submit.isEligible(eligibleDocument);
  //
  //    // then
  //    assertThat(isEligible).isTrue();
  //  }
  //
  //  @Test
  //  void shouldReturnFalseWhenDocumentIsNotEligibleForSubmit() {
  //    // given
  //    var documentWithNoTitle = new Document().setStatus(DRAFT).setTitle(null);
  //
  //    // when
  //    boolean isEligible = submit.isEligible(documentWithNoTitle);
  //
  //    // then
  //    assertThat(isEligible).isFalse();
  //  }

  @Test
  void shouldSubmitDocumentWhenItIsEligibleForSubmit() {
    // given
    var eligibleDocument = new Document().setStatus(DRAFT).setTitle("<title>");

    // when
    submit.accept(eligibleDocument);

    // then
    InOrder inOrder = inOrder(documentStatusService);
    inOrder.verify(documentStatusService).submit(eligibleDocument);
    inOrder.verify(documentStatusService, never()).approveAutomatically(eligibleDocument);
  }

  @Test
  void shouldNotSubmitDocumentWhenItIsNotEligibleForSubmit() {
    // when
    var documentWithNoTitle = new Document().setStatus(DRAFT).setTitle(null);

    // then
    assertThatExceptionOfType(ApplicationException.class)
        .isThrownBy(() -> submit.execute(documentWithNoTitle))
        .withMessage("Validation failed")
        .satisfies(
            exception ->
                assertThat(exception.getErrors())
                    .containsExactlyInAnyOrder(
                        "Document must have a title.", "Document must have an owner."));

    InOrder inOrder = inOrder(documentStatusService);
    inOrder.verify(documentStatusService, never()).submit(documentWithNoTitle);
    inOrder.verify(documentStatusService, never()).approveAutomatically(documentWithNoTitle);
  }

  @Test
  void shouldAutomaticallyApproveWhenItCanBeApprovedAutomatically() {
    // given
    var vipDocument = new Document().setStatus(DRAFT).setTitle("<title>").setOwner("VIP");

    // then
    submit.accept(vipDocument);

    // then
    InOrder inOrder = inOrder(documentStatusService);
    inOrder.verify(documentStatusService).submit(vipDocument);
    inOrder.verify(documentStatusService).approveAutomatically(vipDocument);
  }

  @Test
  void shouldNotAutomaticallyApproveWhenItCannotBeApprovedAutomatically() {
    // given
    var regularDocument = new Document().setStatus(DRAFT).setTitle("<title>");

    // then
    submit.accept(regularDocument);

    // then
    InOrder inOrder = inOrder(documentStatusService);
    inOrder.verify(documentStatusService).submit(regularDocument);
    inOrder.verify(documentStatusService, never()).approveAutomatically(regularDocument);
  }
}
