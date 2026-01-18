package olegood.rgx.domain.project.smachine;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import olegood.rgx.domain.project.Project;
import olegood.rgx.domain.project.ProjectEvent;
import olegood.rgx.domain.project.ProjectStatus;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Builder class for constructing a finite state machine for managing transitions between different
 * {@link ProjectStatus} states in a {@link Project} lifecycle.
 *
 * <p>The {@code StateMachineBuilder} supports the definition of state transitions along with
 * optional guard conditions and event factories. It ensures that transitions are properly sequenced
 * and validated during the construction of the state machine.
 *
 * @param <P> The type of {@link Project} for which the state machine is being built.
 */
public final class StateMachineBuilder<P extends Project> {

  private final Map<ProjectStatus, List<Transition<P>>> transitions =
      new EnumMap<>(ProjectStatus.class);

  private ProjectStatus currentFrom;
  private Transition<P> lastTransition;

  private StateMachineBuilder() {
    // empty constructor
  }

  /**
   * Creates a new {@code StateMachineBuilder} instance for constructing a finite state machine to
   * manage transitions between {@link ProjectStatus} states in a {@link Project} lifecycle.
   *
   * @param <P> The type of {@link Project} for which the state machine is being built.
   * @return A new {@code StateMachineBuilder} instance configured for the specific {@link Project}
   *     type.
   */
  public static <P extends Project> StateMachineBuilder<P> begin() {
    return new StateMachineBuilder<>();
  }

  /**
   * Specifies the initial state from which a transition is to be defined within the finite state
   * machine being constructed.
   *
   * @param from The {@link ProjectStatus} representing the starting state for a transition in the
   *     state machine.
   * @return The current {@code StateMachineBuilder} instance for further configuration of the state
   *     machine.
   */
  public StateMachineBuilder<P> from(ProjectStatus from) {
    currentFrom = from;
    return this;
  }

  /**
   * Defines a transition from the current state (set via {@code from(...)} method) to the specified
   * target {@link ProjectStatus} within the finite state machine being built. This method adds the
   * transition to the internal collection of transitions and sets it as the most recently defined
   * transition.
   *
   * @param to The {@link ProjectStatus} representing the target state for the transition.
   * @return The current {@code StateMachineBuilder} instance for further configuration of the state
   *     machine.
   * @throws IllegalStateException If {@code from(...)} has not been called prior to this method.
   */
  public StateMachineBuilder<P> to(ProjectStatus to) {
    Optional.ofNullable(currentFrom)
        .orElseThrow(() -> new IllegalStateException("from(...) must be called first"));

    var transition = new Transition<P>(currentFrom, to);
    transitions.computeIfAbsent(currentFrom, status -> new ArrayList<>()).add(transition);

    lastTransition = transition;
    return this;
  }

  /**
   * Adds a guard condition to the most recently defined transition within the state machine
   * builder. A guard is a predicate that determines whether the transition is valid for a given
   * project at runtime.
   *
   * @param guard A {@link Predicate} representing the guard condition. The predicate takes an
   *     instance of the project type {@code P} and returns {@code true} if the transition is
   *     permitted, or {@code false} otherwise.
   * @return The current {@code StateMachineBuilder} instance, allowing for further configuration of
   *     the state machine.
   * @throws IllegalStateException If this method is called without previously specifying a
   *     transition using {@code to(...)}.
   */
  public StateMachineBuilder<P> withGuard(Predicate<P> guard) {
    Optional.ofNullable(lastTransition)
        .orElseThrow(() -> new IllegalStateException("withGuard(...) must follow to(...)"));

    lastTransition.setGuard(guard);
    return this;
  }

  /**
   * Configures the event factory responsible for producing a {@link ProjectEvent} for the most
   * recently defined state transition. The event factory generates an event based on the current
   * state of the given project when the transition occurs.
   *
   * @param eventFactory A {@link Function} that takes an instance of the type {@code P} (the
   *     project) and produces a {@link ProjectEvent}. This factory is used to generate events
   *     triggered by the state transition.
   * @return The current {@code StateMachineBuilder} instance, allowing for further configuration of
   *     the state machine.
   * @throws IllegalStateException If this method is called without a preceding call to {@code
   *     to(...)}, which defines the transition for which the event factory should be set.
   */
  public StateMachineBuilder<P> fires(Function<P, ProjectEvent> eventFactory) {
    Optional.ofNullable(lastTransition)
        .orElseThrow(() -> new IllegalStateException("fires(...) must follow to(...)"));

    lastTransition.setEventFactory(eventFactory);
    return this;
  }

  /**
   * Finalizes the configuration of the state machine and builds an instance of {@link
   * StateMachine}.
   *
   * @param eventPublisher The {@link ApplicationEventPublisher} used to publish events triggered by
   *     state transitions.
   * @return A newly created {@link StateMachine} instance initialized with the configured
   *     transitions and the provided {@link ApplicationEventPublisher}.
   */
  public StateMachine<P> build(ApplicationEventPublisher eventPublisher) {
    return new StateMachine<>(transitions, eventPublisher);
  }
}
