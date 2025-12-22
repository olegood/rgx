package olegood.rgx.domain.document;

import org.junit.jupiter.api.Test;

import static olegood.rgx.domain.document.DocumentAction.*;
import static olegood.rgx.domain.document.DocumentStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

class DocumentStatusTest {

    @Test
    void statusDraftMakesAvailableSubmitAndTerminate() {
        // expect
        assertThat(DRAFT.getAvailableActions()).containsOnly(SUBMIT, TERMINATE);
    }

    @Test
    void statusInReviewMakesAvailableApproveRejectAndTerminate() {
        // expect
        assertThat(IN_REVIEW.getAvailableActions()).containsOnly(APPROVE, REJECT, TERMINATE);
    }

    @Test
    void statusApprovedMakesAvailableArchiveReopenAndTerminate() {
        // expect
        assertThat(APPROVED.getAvailableActions()).containsOnly(ARCHIVE, REOPEN, TERMINATE);
    }

    @Test
    void statusArchivedHasNoAvailableActions() {
        // expect
        assertThat(ARCHIVED.getAvailableActions()).isEmpty();
    }

    @Test
    void statusTerminatedMakesAvailableReopen() {
        // expect
        assertThat(TERMINATED.getAvailableActions()).containsOnly(REOPEN);
    }

}
