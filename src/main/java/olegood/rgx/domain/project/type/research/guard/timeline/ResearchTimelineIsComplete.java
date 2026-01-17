package olegood.rgx.domain.project.type.research.guard.timeline;

import java.util.Optional;
import java.util.function.Predicate;
import olegood.rgx.domain.project.type.research.Research;

public final class ResearchTimelineIsComplete implements Predicate<Research> {

  @Override
  public boolean test(Research research) {
    return Optional.of(research)
        .map(Research::getTimeline)
        .map(it -> it.getActualEndDate() != null)
        .orElse(false);
  }
}
