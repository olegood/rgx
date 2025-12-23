package olegood.rgx.domain.document;

import static olegood.rgx.domain.document.DocumentAction.APPROVE;
import static olegood.rgx.domain.document.DocumentAction.ARCHIVE;
import static olegood.rgx.domain.document.DocumentAction.REJECT;
import static olegood.rgx.domain.document.DocumentAction.REOPEN;
import static olegood.rgx.domain.document.DocumentAction.SUBMIT;
import static olegood.rgx.domain.document.DocumentAction.TERMINATE;
import static olegood.rgx.domain.document.DocumentStatus.APPROVED;
import static olegood.rgx.domain.document.DocumentStatus.ARCHIVED;
import static olegood.rgx.domain.document.DocumentStatus.DRAFT;
import static olegood.rgx.domain.document.DocumentStatus.IN_REVIEW;
import static olegood.rgx.domain.document.DocumentStatus.TERMINATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

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

  @Test
  void availableActionsShouldBeUnmodifiable() {
    // expect
    var actions = TERMINATED.getAvailableActions();
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> actions.add(SUBMIT));
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> actions.remove(APPROVE));
  }

  // region isActionAllowed(DRAFT)

  @DisplayName("Allowed actions when Document in status: DRAFT")
  @ParameterizedTest(name = "Actor can {0} Document in status DRAFT")
  @EnumSource(
      value = DocumentAction.class,
      names = {"SUBMIT", "TERMINATE"})
  void isActionAllowedForDraft(DocumentAction action) {
    // expect
    assertThat(DRAFT.isActionAllowed(action)).isTrue();
  }

  @DisplayName("Forbidden actions when Document in status: DRAFT")
  @ParameterizedTest(name = "Actor cannot {0} Document in status DRAFT")
  @EnumSource(
      value = DocumentAction.class,
      names = {"SUBMIT", "TERMINATE"},
      mode = EnumSource.Mode.EXCLUDE)
  void isActionForbiddenForDraft(DocumentAction action) {
    // expect
    assertThat(DRAFT.isActionAllowed(action)).isFalse();
  }

  // endregion

  // region isActionAllowed(IN_REVIEW)

  @DisplayName("Allowed actions when Document in status: IN_REVIEW")
  @ParameterizedTest(name = "Actor can {0} Document in status IN_REVIEW")
  @EnumSource(
      value = DocumentAction.class,
      names = {"APPROVE", "REJECT", "TERMINATE"})
  void isActionAllowedForInReview(DocumentAction action) {
    // expect
    assertThat(IN_REVIEW.isActionAllowed(action)).isTrue();
  }

  @DisplayName("Forbidden actions when Document in status: IN_REVIEW")
  @ParameterizedTest(name = "Actor cannot {0} Document in status IN_REVIEW")
  @EnumSource(
      value = DocumentAction.class,
      names = {"APPROVE", "REJECT", "TERMINATE"},
      mode = EnumSource.Mode.EXCLUDE)
  void isActionForbiddenForInReview(DocumentAction action) {
    // expect
    assertThat(IN_REVIEW.isActionAllowed(action)).isFalse();
  }

  // endregion

  // region isActionAllowed(APPROVED)

  @DisplayName("Allowed actions when Document in status: APPROVED")
  @ParameterizedTest(name = "Actor can {0} Document in status APPROVED")
  @EnumSource(
      value = DocumentAction.class,
      names = {"ARCHIVE", "REOPEN", "TERMINATE"})
  void isActionAllowedForApproved(DocumentAction action) {
    // expect
    assertThat(APPROVED.isActionAllowed(action)).isTrue();
  }

  @DisplayName("Forbidden actions when Document in status: APPROVED")
  @ParameterizedTest(name = "Actor cannot {0} Document in status APPROVED")
  @EnumSource(
      value = DocumentAction.class,
      names = {"ARCHIVE", "REOPEN", "TERMINATE"},
      mode = EnumSource.Mode.EXCLUDE)
  void isActionForbiddenForApproved(DocumentAction action) {
    // expect
    assertThat(APPROVED.isActionAllowed(action)).isFalse();
  }

  // endregion

  // region isActionAllowed(TERMINATED)

  @DisplayName("Allowed actions when Document in status: TERMINATED")
  @ParameterizedTest(name = "Actor can {0} Document in status TERMINATED")
  @EnumSource(
      value = DocumentAction.class,
      names = {"REOPEN"})
  void isActionAllowedForTerminated(DocumentAction action) {
    // expect
    assertThat(TERMINATED.isActionAllowed(action)).isTrue();
  }

  @DisplayName("Forbidden actions when Document in status: TERMINATED")
  @ParameterizedTest(name = "Actor cannot {0} Document in status TERMINATED")
  @EnumSource(
      value = DocumentAction.class,
      names = {"REOPEN"},
      mode = EnumSource.Mode.EXCLUDE)
  void isActionForbiddenForTerminated(DocumentAction action) {
    // expect
    assertThat(TERMINATED.isActionAllowed(action)).isFalse();
  }

  // endregion

  @DisplayName("No actions allowed for Document in status: ARCHIVED")
  @ParameterizedTest(name = "Actor cannot {0} Document in status ARCHIVED")
  @EnumSource(DocumentAction.class)
  void isActionAllowedForArchived(DocumentAction action) {
    // expect
    assertThat(ARCHIVED.isActionAllowed(action)).isFalse();
  }
}
