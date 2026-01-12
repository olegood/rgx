package olegood.rgx.service.document.status.impl;

import static olegood.rgx.domain.document.DocumentAction.SUBMIT;
import static olegood.rgx.domain.document.DocumentStatus.DRAFT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;

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
  void associatedActionIsSubmit() {
    // when
    var action = submit.associatedAction();

    // then
    assertThat(action).isEqualTo(SUBMIT);
  }

  @Test
  void shouldReturnTrueWhenDocumentIsEligibleForSubmit() {
    // given
    var eligibleDocument = new Document().setStatus(DRAFT).setTitle("<title>");

    // when
    boolean isEligible = submit.isEligible(eligibleDocument);

    // then
    assertThat(isEligible).isTrue();
  }

  @Test
  void shouldReturnFalseWhenDocumentIsNotEligibleForSubmit() {
    // given
    var documentWithNoTitle = new Document().setStatus(DRAFT).setTitle(null);

    // when
    boolean isEligible = submit.isEligible(documentWithNoTitle);

    // then
    assertThat(isEligible).isFalse();
  }

  @Test
  void shouldSubmitDocumentWhenItIsEligibleForSubmit() {
    // given
    var eligibleDocument = new Document().setStatus(DRAFT).setTitle("<title>");

    // when
    submit.execute(eligibleDocument);

    // then
    InOrder inOrder = inOrder(documentStatusService);
    inOrder.verify(documentStatusService).submit(eligibleDocument);
    inOrder.verify(documentStatusService, never()).approveAutomatically(eligibleDocument);
  }

  @Test
  void shouldNotSubmitDocumentWhenItIsNotEligibleForSubmit() {
    // given
    var documentWithNoTitle = new Document().setStatus(DRAFT).setTitle(null);

    // when
    submit.execute(documentWithNoTitle);

    // then
    InOrder inOrder = inOrder(documentStatusService);
    inOrder.verify(documentStatusService, never()).submit(documentWithNoTitle);
    inOrder.verify(documentStatusService, never()).approveAutomatically(documentWithNoTitle);
  }

  @Test
  void shouldAutomaticallyApproveWhenItCanBeApprovedAutomatically() {
    // given
    var vipDocument = new Document().setStatus(DRAFT).setTitle("<title>").setOwner("VIP");

    // then
    submit.execute(vipDocument);

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
    submit.execute(regularDocument);

    // then
    InOrder inOrder = inOrder(documentStatusService);
    inOrder.verify(documentStatusService).submit(regularDocument);
    inOrder.verify(documentStatusService, never()).approveAutomatically(regularDocument);
  }
}
