package olegood.rgx.validation.engine;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class ValidationResult {

  private final Set<Violation> violations = new HashSet<>();

  public void addViolation(Violation violation) {
    violations.add(violation);
  }

  public boolean isValid() {
    return violations.isEmpty();
  }

  public Set<String> messages() {
    return violations.stream().map(Violation::message).collect(Collectors.toSet());
  }
}
