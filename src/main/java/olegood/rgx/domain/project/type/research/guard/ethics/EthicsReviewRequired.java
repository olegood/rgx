package olegood.rgx.domain.project.type.research.guard.ethics;

import java.util.Optional;
import java.util.function.Predicate;
import olegood.rgx.domain.project.type.research.Research;
import olegood.rgx.domain.project.type.research.content.ethics.EthicalProfile;

public class EthicsReviewRequired implements Predicate<Research> {

  @Override
  public boolean test(Research research) {
    return Optional.of(research)
        .map(Research::getEthicalProfile)
        .map(EthicalProfile::isEthicsReviewRequired)
        .orElse(false);
  }
}
