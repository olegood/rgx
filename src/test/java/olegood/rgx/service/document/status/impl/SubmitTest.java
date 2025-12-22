package olegood.rgx.service.document.status.impl;

import olegood.rgx.domain.document.Document;
import olegood.rgx.service.document.DocumentStatusService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static olegood.rgx.domain.document.DocumentAction.SUBMIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmitTest {

    @Mock
    private DocumentStatusService documentStatusService;

    @Mock
    private Document document;

    @InjectMocks
    private Submit submit;

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
        when(document.canBeSubmitted()).thenReturn(true);

        // when
        boolean isEligible = submit.isEligible().test(document);

        // then
        assertThat(isEligible).isTrue();
    }

    @Test
    void shouldReturnFalseWhenDocumentIsNotEligibleForSubmit() {
        // given
        when(document.canBeSubmitted()).thenReturn(false);

        // when
        boolean isEligible = submit.isEligible().test(document);

        // then
        assertThat(isEligible).isFalse();
    }

    @Test
    void shouldSubmitDocumentWhenItIsEligibleForSubmit() {
        // when
        when(document.canBeSubmitted()).thenReturn(true);

        // then
        submit.execute(document);

        // then
        InOrder inOrder = inOrder(document, documentStatusService);
        inOrder.verify(document).canBeSubmitted();
        inOrder.verify(documentStatusService).submit(document);
        inOrder.verify(document).canBeApprovedAutomatically();
        inOrder.verify(documentStatusService, never()).approveAutomatically(document);
    }

    @Test
    void shouldNotSubmitDocumentWhenItIsNotEligibleForSubmit() {
        // when
        when(document.canBeSubmitted()).thenReturn(false);

        // then
        submit.execute(document);

        // then
        InOrder inOrder = inOrder(document, documentStatusService);
        inOrder.verify(document).canBeSubmitted();
        inOrder.verify(documentStatusService, never()).submit(document);
        inOrder.verify(document).canBeApprovedAutomatically();
        inOrder.verify(documentStatusService, never()).approveAutomatically(document);
    }

    @Test
    void shouldAutomaticallyApproveWhenItCanBeApprovedAutomatically() {
        // when
        when(document.canBeSubmitted()).thenReturn(true);
        when(document.canBeApprovedAutomatically()).thenReturn(true);

        // then
        submit.execute(document);

        // then
        InOrder inOrder = inOrder(document, documentStatusService);
        inOrder.verify(document).canBeSubmitted();
        inOrder.verify(documentStatusService).submit(document);
        inOrder.verify(document).canBeApprovedAutomatically();
        inOrder.verify(documentStatusService).approveAutomatically(document);
    }

    @Test
    void shouldNotAutomaticallyApproveWhenItCannotBeApprovedAutomatically() {
        // when
        when(document.canBeSubmitted()).thenReturn(true);
        when(document.canBeApprovedAutomatically()).thenReturn(false);

        // then
        submit.execute(document);

        // then
        InOrder inOrder = inOrder(document, documentStatusService);
        inOrder.verify(document).canBeSubmitted();
        inOrder.verify(documentStatusService).submit(document);
        inOrder.verify(document).canBeApprovedAutomatically();
        inOrder.verify(documentStatusService, never()).approveAutomatically(document);
    }

}
