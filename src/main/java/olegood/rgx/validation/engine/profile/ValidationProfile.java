package olegood.rgx.validation.engine.profile;

import static java.util.Collections.emptySet;

import java.util.Set;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import olegood.rgx.validation.rule.Rule;

/**
 * Represents a profile for validating objects of a specific type {@code T}.
 * A {@code ValidationProfile} consists of a predicate (matcher) that determines
 * the applicability of rules to a target object, and a set of validation rules
 * encapsulated as {@code Rule<T>}.
 *
 * Each profile can be configured to include conditional rules, which are applied
 * based on the evaluation of the supplied matcher, or unconditionally applied rules.
 *
 * This class provides mechanisms for creating validation profiles, retrieving applicable
 * rules for a given target, and defining empty profiles containing no rules.
 *
 * @param <T> the type of objects to which this validation profile applies
 */
@RequiredArgsConstructor
public final class ValidationProfile<T> {

  private final Predicate<T> matcher;
  private final Set<Rule<T>> rules;

  /**
   * Creates a {@code ValidationProfile} using a set of validation rules.
   * The created profile applies the specified rules unconditionally to any target.
   *
   * @param <T> the type of objects to which this validation profile applies
   * @param rules the set of {@code Rule<T>} objects to be included in the validation profile
   * @return a {@code ValidationProfile} containing the specified rules
   */
  @SafeVarargs
  public static <T> ValidationProfile<T> withRules(Rule<T>... rules) {
    return withConditionalRules(t -> true, rules);
  }

  /**
   * Creates a {@code ValidationProfile} using a specific condition and a set of validation rules.
   * The provided {@code matcher} determines whether the provided validation rules should be applied
   * to a given target.
   *
   * @param <T> the type of objects to which this validation profile applies
   * @param matcher a {@code Predicate} that serves as a condition to determine whether the validation rules
   *                should be applied to a target
   * @param rules the set of {@code Rule<T>} objects to be included in the validation profile
   * @return a {@code ValidationProfile} containing the specified matcher and rules
   */
  @SafeVarargs
  public static <T> ValidationProfile<T> withConditionalRules(
      Predicate<T> matcher, Rule<T>... rules) {
    return new ValidationProfile<>(matcher, Set.of(rules));
  }

  /**
   * Retrieves the set of validation rules applicable to the given target.
   *
   * @param target the object to be validated
   * @return a set of {@code Rule<T>} applicable to the provided target, or an empty set if no rules apply
   */
  public Set<Rule<T>> getRulesFor(T target) {
    return matcher.test(target) ? rules : emptySet();
  }

  /**
   * Creates an empty {@code ValidationProfile} with no rules and a matcher that always evaluates to {@code true}.
   *
   * @param <T> the type of objects to which this validation profile applies
   * @return an instance of {@code ValidationProfile} that contains no validation rules
   */
  public static <T> ValidationProfile<T> empty() {
    return new ValidationProfile<>(t -> true, emptySet());
  }
}
