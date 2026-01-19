package olegood.rgx.domain.project.type.research.lifecycle;

import olegood.rgx.domain.project.type.research.Research;

public interface ResearchLifecycle {

  void propose(Research research);

  void review(Research research);

  void approve(Research research);

  void reject(Research research);

  void hold(Research research);

  void start(Research research);

  void complete(Research research);

  void cancel(Research research);

  void resume(Research research);

  void archive(Research research);
}
