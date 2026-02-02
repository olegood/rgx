package olegood.rgx.validation.rule.impl.research;

import java.util.Optional;
import olegood.rgx.domain.project.type.research.Research;
import olegood.rgx.validation.rule.Rule;

public class HasResearchGoals extends Rule<Research> {

  private final int expectedGoalsCount;

  protected HasResearchGoals(int expectedGoalsCount) {
    super("Research must have at least " + expectedGoalsCount + " goals");
    this.expectedGoalsCount = expectedGoalsCount;
  }

  @Override
  public boolean test(Research research) {
    return Optional.of(research)
        .map(Research::getGoals)
        .map(goals -> goals.size() >= expectedGoalsCount)
        .orElse(false);
  }
}
