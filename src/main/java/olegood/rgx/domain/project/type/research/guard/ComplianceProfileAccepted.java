package olegood.rgx.domain.project.type.research.guard;

import java.util.Optional;
import java.util.function.Predicate;
import olegood.rgx.domain.project.type.research.Research;

public class ComplianceProfileAccepted implements Predicate<Research> {

  @Override
  public boolean test(Research research) {
    return Optional.of(research)
        .map(Research::getComplianceProfile)
        .map(it -> it.isInternallyCompliant() && it.getLastReviewAt() != null)
        .orElse(false);
  }
}
