package olegood.rgx.validation.engine;

import java.util.Collection;
import java.util.Optional;
import olegood.rgx.validation.engine.profile.ValidationProfile;

/**
 * The ValidationEngine class is responsible for validating an object of type {@code T}
 * against a set of rules encapsulated within a {@link ValidationProfile}. The validation
 * process generates a {@link ValidationResult}, which provides details about any violations
 * that occurred during validation.
 *
 * @param <T> the type of object that this validation engine is intended to validate
 */
public record ValidationEngine<T>(ValidationProfile<T> validationProfile) {

  /**
   * Validates the given target object against the set of rules defined in the associated
   * validation profile and returns the result of the validation.
   *
   * @param target the object to be validated
   * @return a {@link ValidationResult} containing the validation outcomes, including any
   *         violations if the target does not satisfy certain rules
   */
  public ValidationResult validate(T target) {
    return ValidationResult.of(collectViolationsFor(target));
  }

  /**
   * Collects all violations by checking the target against each rule in the profile.
   *
   * @param target the object to be validated
   * @return a stream of violations found during validation
   */
  private Collection<Violation> collectViolationsFor(T target) {
    return validationProfile().getRulesFor(target).stream()
        .map(rule -> rule.check(target))
        .flatMap(Optional::stream)
        .toList();
  }
}
