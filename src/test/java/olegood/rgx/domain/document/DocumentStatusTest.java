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
    void statusApprovedMakesAvailableArchiveAndTerminate() {
        // expect
        assertThat(APPROVED.getAvailableActions()).containsOnly(ARCHIVE, TERMINATE);
    }

    @Test
    void statusArchivedHasNoAvailableActions() {
        // expect
        assertThat(ARCHIVED.getAvailableActions()).isEmpty();
    }

    @Test
    void statusTerminatedHasNoAvailableActions() {
        // expect
        assertThat(TERMINATED.getAvailableActions()).isEmpty();
    }

}
