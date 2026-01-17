package olegood.rgx.domain.project.type.research.guard;

import java.util.function.Predicate;
import olegood.rgx.domain.project.type.research.Research;
import olegood.rgx.domain.project.type.research.guard.ethics.EthicalProfileAccepted;

public final class EligibleForApproval implements Predicate<Research> {

  @Override
  public boolean test(Research research) {
    return new EthicalProfileAccepted().and(new ComplianceProfileAccepted()).test(research);
  }
}
