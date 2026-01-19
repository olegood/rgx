package olegood.rgx.domain.project.type.research.lifecycle;

import static olegood.rgx.domain.project.ProjectStatus.DRAFT;
import static olegood.rgx.domain.project.ProjectStatus.PROPOSED;
import static olegood.rgx.domain.project.ProjectStatus.UNDER_REVIEW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import olegood.rgx.domain.project.ProjectStatus;
import olegood.rgx.domain.project.smachine.TypedStateMachine;
import olegood.rgx.domain.project.type.research.Research;
import olegood.rgx.domain.project.type.research.content.ComplianceProfile;
import olegood.rgx.domain.project.type.research.content.ResearchTimeline;
import olegood.rgx.domain.project.type.research.content.ethics.EthicalProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

@SpringBootTest(classes = {ResearchLifecycleConfig.class})
class ResearchLifecycleConfigITCase {

  @Autowired private ApplicationEventPublisher eventPublisher;

  @Autowired private TypedStateMachine<Research> researchStateMachine;

  @Test
  void testDraftToProposedTransition() {
    // given
    var research = createSampleResearch(DRAFT);

    // when
    researchStateMachine.machine().evolve(research, PROPOSED);

    // then
    assertThat(research.getStatus()).isEqualTo(PROPOSED);
  }

  @Test
  void testProposedToUnderReviewTransition() {
    // given
    var research = createSampleResearch(PROPOSED);

    // when
    researchStateMachine.machine().evolve(research, UNDER_REVIEW);

    // then
    assertThat(research.getStatus()).isEqualTo(UNDER_REVIEW);
  }

  @Test
  void testUnderReviewToApprovedTransitionWithEligibleGuard() {
    // given
    var research = createSampleResearch(UNDER_REVIEW);

    var ethicalProfile = new EthicalProfile();
    ethicalProfile.setEthicsReviewRequired(false);
    research.setEthicalProfile(ethicalProfile);

    var complianceProfile = new ComplianceProfile();
    complianceProfile.setInternallyCompliant(true);
    complianceProfile.setLastReviewAt(Instant.now());
    research.setComplianceProfile(complianceProfile);

    // when
    researchStateMachine.machine().evolve(research, ProjectStatus.APPROVED);

    // then
    assertThat(research.getStatus()).isEqualTo(ProjectStatus.APPROVED);
  }

  @Test
  void testUnderReviewToCancelledTransition() {
    // given
    var research = createSampleResearch(UNDER_REVIEW);

    // when
    researchStateMachine.machine().evolve(research, ProjectStatus.CANCELLED);

    // then
    assertThat(research.getStatus()).isEqualTo(ProjectStatus.CANCELLED);
  }

  @Test
  void testApprovedToInProgressTransition() {
    // given
    var research = createSampleResearch(ProjectStatus.APPROVED);

    // when
    researchStateMachine.machine().evolve(research, ProjectStatus.IN_PROGRESS);

    // then
    assertThat(research.getStatus()).isEqualTo(ProjectStatus.IN_PROGRESS);
  }

  @Test
  void testInProgressToCompletedTransitionWithTimelineGuard() {
    // given
    var research = createSampleResearch(ProjectStatus.IN_PROGRESS);

    var researchTimeline = new ResearchTimeline();
    researchTimeline.setActualEndDate(LocalDate.now());
    research.setTimeline(researchTimeline);

    // when
    researchStateMachine.machine().evolve(research, ProjectStatus.COMPLETED);

    // then
    assertThat(research.getStatus()).isEqualTo(ProjectStatus.COMPLETED);
  }

  @Test
  void testCompletedToArchivedTransition() {
    // given
    var research = createSampleResearch(ProjectStatus.COMPLETED);

    // when
    researchStateMachine.machine().evolve(research, ProjectStatus.ARCHIVED);

    // then
    assertThat(research.getStatus()).isEqualTo(ProjectStatus.ARCHIVED);
  }

  @Test
  void testCannotTransitionFromDraftToInProgress() {
    // when
    var research = createSampleResearch(DRAFT);

    // then
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(
            () -> researchStateMachine.machine().evolve(research, ProjectStatus.IN_PROGRESS));
  }

  private Research createSampleResearch(ProjectStatus status) {
    var research = new Research();
    research.setId(UUID.randomUUID());
    research.setCode("RSCH-123");
    research.setStatus(status);
    research.setCreatedAt(Instant.now());
    research.setModifiedAt(Instant.now());
    research.setTitle("Sample Research");
    return research;
  }
}
