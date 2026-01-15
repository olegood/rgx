package olegood.rgx.domain.project.smachine;

import java.util.function.Function;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.Setter;
import olegood.rgx.domain.project.Project;
import olegood.rgx.domain.project.ProjectEvent;
import olegood.rgx.domain.project.ProjectStatus;

/**
 * Represents a state transition within a finite state machine for a {@link Project}.
 *
 * <p>A transition encapsulates the following components: - A source state ({@code from}) and a
 * target state ({@code to}). - An optional guard condition ({@code guard}) that determines the
 * validity of the transition. - An optional event factory ({@code eventFactory}) which generates a
 * {@link ProjectEvent} when the transition occurs.
 *
 * @param <P> Type parameter specifying the subclass of {@link Project} this transition applies to.
 */
@Setter
public final class Transition<P extends Project> {

  /**
   * In the lifecycle of a {@link Project}, the {@code from} state indicates the current status of
   * the project before attempting a specific transition.
   */
  private final ProjectStatus from;

  /**
   * In the lifecycle of a project, the {@code to} state indicates the intended status of the
   * project after a successful transition. This is paired with a source state ({@link #from}), an
   * optional guard condition, and an optional event factory to define the transition logic.
   */
  private final ProjectStatus to;

  /**
   * A predicate representing the guard condition for a transition within a finite state machine.
   * This condition determines whether the transition is valid for a given project at runtime.
   */
  private Predicate<P> guard = project -> true;

  /**
   * A function that generates a {@link ProjectEvent} based on the current state of a given project.
   */
  @Getter private Function<P, ProjectEvent> eventFactory;

  /**
   * Constructs a state transition with a source state and a target state.
   *
   * @param from The current state before the transition.
   * @param to Desired state after the transition.
   */
  Transition(ProjectStatus from, ProjectStatus to) {
    this.from = from;
    this.to = to;
  }

  /**
   * Determines if the current transition matches the given project and target state.
   *
   * @param project The project for which the transition is being evaluated.
   * @param target The target {@code ProjectStatus} being tested for this transition.
   * @return {@code true} if the transition can occur, {@code false} otherwise.
   */
  boolean matches(P project, ProjectStatus target) {
    return project.getStatus() == from && target == to && guard.test(project);
  }
}
