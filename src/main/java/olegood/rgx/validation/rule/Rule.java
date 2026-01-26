package olegood.rgx.validation.rule;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import olegood.rgx.validation.engine.Violation;

/**
 * An abstract base class defining a validation rule. Subclasses implement the specific validation logic
 * by overriding the {@code test} method. This class also provides functionality to associate a violation
 * message with a rule and check if a given target satisfies the rule or not.
 *
 * @param <T> the type of the object that the rule will validate
 */
@RequiredArgsConstructor
public abstract class Rule<T> implements Predicate<T> {

  private final Violation violation;

  /**
   * Constructs a Rule with the given violation message.
   *
   * @param message the message describing the violation associated with this rule
   */
  protected Rule(String message) {
    this(new Violation(message));
  }

  /**
   * Evaluates the given target against the rule's predicate and determines if a violation occurs.
   *
   * @param target the object to be validated
   * @return an {@code Optional} containing a {@code Violation} if the target fails the rule,
   *         or an empty {@code Optional} if the target satisfies the rule
   */
  public Optional<Violation> check(T target) {
    return test(target) ? Optional.empty() : Optional.of(violation);
  }
}
