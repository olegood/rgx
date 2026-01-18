package olegood.rgx.domain.project.smachine;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import olegood.rgx.domain.project.Project;
import olegood.rgx.domain.project.ProjectStatus;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Represents a finite state machine capable of managing transitions between states for projects of
 * type {@link Project} or its subclasses. This class uses a transition matrix to define allowed
 * state changes and publishes events when transitions occur.
 *
 * @param <P> The type parameter specifying the concrete subclass of {@link Project} this state
 *     machine operates on.
 */
public record StateMachine<P extends Project>(
    Map<ProjectStatus, List<Transition<P>>> transitionMatrix,
    ApplicationEventPublisher eventPublisher) {

  /**
   * Attempts to transition a project from its current state to a target state by consulting the
   * state machine's transition matrix.
   *
   * @param project The project whose state is being transitioned.
   * @param target The desired {@link ProjectStatus} to transition to.
   * @throws IllegalStateException If no valid transition exists from the current state to the
   *     target state, or the transition fails due to guard constraints.
   */
  public void evolve(P project, ProjectStatus target) {
    var current = project.getStatus();

    var available = transitionMatrix.getOrDefault(current, List.of());
    for (var transition : available) {
      if (transition.matches(project, target)) {
        project.setStatus(target);
        Optional.ofNullable(transition.getEventFactory())
            .map(it -> it.apply(project))
            .ifPresent(eventPublisher::publishEvent);
        return;
      }
    }

    throw new IllegalStateException(
        "Illegal transition from '%s' to '%s' for project type %s (transition might be undefined or failed due to a guard rule)."
            .formatted(current, target, project.getClass().getSimpleName()));
  }
}
