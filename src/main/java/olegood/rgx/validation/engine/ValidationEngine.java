package olegood.rgx.validation.engine;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * The ValidationEngine class is responsible for validating an object of type {@code T}
 * against a set of rules encapsulated within a {@link ValidationProfile}. The validation
 * process generates a {@link ValidationResult}, which provides details about any violations
 * that occurred during validation.
 *
 * @param <T> the type of object that this validation engine is intended to validate
 */
public record ValidationEngine<T>(ValidationProfile<T> profile) {

  /**
   * Validates the given target object against the set of rules defined in the associated
   * validation profile and returns the result of the validation.
   *
   * @param target the object to be validated
   * @return a {@link ValidationResult} containing the validation outcomes, including any
   *         violations if the target does not satisfy certain rules
   */
  public ValidationResult validate(T target) {
    ValidationResult result = new ValidationResult();
    collectViolations(target).forEach(result::addViolation);
    return result;
  }

  /**
   * Collects all violations by checking the target against each rule in the profile.
   *
   * @param target the object to be validated
   * @return a stream of violations found during validation
   */
  private Stream<Violation> collectViolations(T target) {
    return profile().rules().stream().map(rule -> rule.check(target)).flatMap(Optional::stream);
  }
}
