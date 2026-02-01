package olegood.rgx.validation.engine;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class ValidationResult {

  private final Set<Violation> violations;

  private ValidationResult(Set<Violation> violations) {
    this.violations = Set.copyOf(violations);
  }

  public static ValidationResult of(Collection<Violation> violations) {
    return new ValidationResult(new HashSet<>(violations));
  }

  public boolean hasViolations() {
    return !violations.isEmpty();
  }

  public Set<String> violationMessages() {
    return violations.stream().map(Violation::message).collect(Collectors.toSet());
  }
}
