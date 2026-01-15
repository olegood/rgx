package olegood.rgx.domain.project.smachine;

import static olegood.rgx.domain.project.ProjectStatus.APPROVED;
import static olegood.rgx.domain.project.ProjectStatus.DRAFT;
import static olegood.rgx.domain.project.ProjectStatus.PROPOSED;
import static org.assertj.core.api.Assertions.assertThat;

import olegood.rgx.domain.project.ProjectStatus;
import olegood.rgx.domain.project.type.research.Research;
import org.junit.jupiter.api.Test;

class TransitionTest {

  @Test
  void shouldReturnTrueIfMatches() {
    // when
    var transition = new Transition<>(DRAFT, PROPOSED);
    transition.setGuard(proj -> proj.getCode().equals("RGX-001"));

    var project = createProjectWithStatus(DRAFT);

    // then
    assertThat(transition.matches(project, PROPOSED)).isTrue();
  }

  @Test
  void shouldReturnFalseIfInvalidSourceStatus() {
    // when
    var transition = new Transition<>(DRAFT, PROPOSED);
    var project = createProjectWithStatus(APPROVED);

    // then
    assertThat(transition.matches(project, PROPOSED)).isFalse();
  }

  @Test
  void shouldReturnFalseIfInvalidTargetStatus() {
    // given
    var transition = new Transition<>(DRAFT, PROPOSED);
    var project = createProjectWithStatus(DRAFT);

    // when
    assertThat(transition.matches(project, APPROVED)).isFalse();
  }

  @Test
  void shouldReturnFalseIfGuardNotMet() {
    // when
    var transition = new Transition<>(DRAFT, PROPOSED);
    transition.setGuard(proj -> proj.getCode().equals("INVALID-CODE"));

    var project = createProjectWithStatus(DRAFT);

    // then
    assertThat(transition.matches(project, PROPOSED)).isFalse();
  }

  @Test
  void shouldReturnTrueIfGuardNotSet() {
    // when
    var transition = new Transition<>(DRAFT, PROPOSED);
    var project = createProjectWithStatus(DRAFT);

    // when
    assertThat(transition.matches(project, PROPOSED)).isTrue();
  }

  private Research createProjectWithStatus(ProjectStatus status) {
    Research project = new Research();
    project.setStatus(status);
    project.setCode("RGX-001");
    return project;
  }
}
