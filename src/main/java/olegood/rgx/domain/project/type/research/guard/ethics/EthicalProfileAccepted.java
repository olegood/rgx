package olegood.rgx.domain.project.type.research.guard.ethics;

import java.util.function.Predicate;
import olegood.rgx.domain.project.type.research.Research;

public class EthicalProfileAccepted implements Predicate<Research> {

  @Override
  public boolean test(Research research) {
    var reviewRequired = new EthicsReviewRequired();
    var profileApproved = new EthicalProfileApproved();

    // If a review is required, check if approved.
    // Otherwise, it's complete by default.
    return !reviewRequired.test(research) || profileApproved.test(research);
  }
}
