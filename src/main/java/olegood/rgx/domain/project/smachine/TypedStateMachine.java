package olegood.rgx.domain.project.smachine;

import olegood.rgx.domain.project.Project;

/**
 * Represents a functional interface for creating a strongly typed state machine specific to a
 * {@link Project} or its subclasses. The main purpose of this interface is to provide a contract
 * for implementing a state machine that operates on a particular type of project.
 *
 * @param <P> The type parameter defining the specific subclass of {@link Project} that this state
 *     machine will operate on.
 */
@FunctionalInterface
public interface TypedStateMachine<P extends Project> {

  /**
   * Retrieves the state machine instance responsible for managing transitions between states for a
   * specific type of {@link Project} or its subclasses. The returned state machine encapsulates the
   * logic for state transitions, including guard conditions and event publishing.
   *
   * @return A {@link StateMachine} instance configured for the given type parameter {@code P}.
   */
  StateMachine<P> machine();
}
