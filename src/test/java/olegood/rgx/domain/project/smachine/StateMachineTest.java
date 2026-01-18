package olegood.rgx.domain.project.smachine;

import static olegood.rgx.domain.project.ProjectStatus.APPROVED;
import static olegood.rgx.domain.project.ProjectStatus.DRAFT;
import static olegood.rgx.domain.project.ProjectStatus.PROPOSED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import olegood.rgx.domain.project.Project;
import olegood.rgx.domain.project.ProjectEvent;
import olegood.rgx.domain.project.ProjectStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class StateMachineTest {

  @Mock private ApplicationEventPublisher eventPublisher;

  @Mock private Transition<Project> transition;

  @Mock private Project project;

  @Test
  void shouldTransitionToTargetStateWhenTransitionIsValid() {
    // given
    setupDraftProject();
    when(transition.matches(project, PROPOSED)).thenReturn(true);

    var stateMachine = new StateMachine<>(Map.of(DRAFT, List.of(transition)), eventPublisher);

    // when
    stateMachine.evolve(project, PROPOSED);

    // then
    assertThat(project.getStatus()).isEqualTo(PROPOSED);

    verify(transition).matches(project, PROPOSED);
    verifyNoInteractions(eventPublisher);
  }

  @Test
  void shouldPublishEventWhenTransitionHasEventFactory() {
    // given
    setupDraftProject();
    var mockEvent = new ProjectEvent() {};

    when(transition.matches(project, PROPOSED)).thenReturn(true);
    when(transition.getEventFactory()).thenReturn(p -> mockEvent);

    var stateMachine = new StateMachine<>(Map.of(DRAFT, List.of(transition)), eventPublisher);

    // when
    stateMachine.evolve(project, PROPOSED);

    // then
    assertThat(project.getStatus()).isEqualTo(PROPOSED);
    verify(eventPublisher).publishEvent(mockEvent);
  }

  @Test
  void shouldThrowExceptionWhenNoValidTransitionExists() {
    // when
    when(project.getStatus()).thenReturn(DRAFT);

    var stateMachine = new StateMachine<>(Map.of(DRAFT, List.of()), eventPublisher);

    // then
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(() -> stateMachine.evolve(project, PROPOSED))
        .withMessageContaining("Illegal transition from 'DRAFT' to 'PROPOSED' for project type")
        .withMessageContaining("(transition might be undefined or failed due to a guard rule).");

    verifyNoInteractions(eventPublisher);
  }

  @Test
  void shouldThrowExceptionWhenGuardRuleFails() {
    // given
    when(project.getStatus()).thenReturn(DRAFT);
    when(transition.matches(project, PROPOSED)).thenReturn(false);

    var stateMachine = new StateMachine<>(Map.of(DRAFT, List.of(transition)), eventPublisher);

    // then
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(() -> stateMachine.evolve(project, PROPOSED))
        .withMessageContaining("Illegal transition from 'DRAFT' to 'PROPOSED' for project type")
        .withMessageContaining("(transition might be undefined or failed due to a guard rule).");

    verify(transition).matches(project, PROPOSED);
    verifyNoInteractions(eventPublisher);
  }

  @Test
  void shouldThrowExceptionWhenNoTransitionsForCurrentState() {
    // given
    when(project.getStatus()).thenReturn(APPROVED);

    var stateMachine = new StateMachine<>(Map.of(), eventPublisher);

    // then
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(() -> stateMachine.evolve(project, PROPOSED))
        .withMessageContaining("Illegal transition from 'APPROVED' to 'PROPOSED' for project type")
        .withMessageContaining("(transition might be undefined or failed due to a guard rule).");

    verifyNoInteractions(eventPublisher);
  }

  private void setupDraftProject() {
    when(project.getStatus()).thenReturn(ProjectStatus.DRAFT);
    doAnswer(
            invocation -> {
              ProjectStatus newStatus = invocation.getArgument(0);
              when(project.getStatus()).thenReturn(newStatus);
              return null;
            })
        .when(project)
        .setStatus(any(ProjectStatus.class));
  }
}
