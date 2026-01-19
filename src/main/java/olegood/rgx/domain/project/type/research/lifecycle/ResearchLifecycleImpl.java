package olegood.rgx.domain.project.type.research.lifecycle;

import static olegood.rgx.domain.project.ProjectStatus.APPROVED;
import static olegood.rgx.domain.project.ProjectStatus.ARCHIVED;
import static olegood.rgx.domain.project.ProjectStatus.CANCELLED;
import static olegood.rgx.domain.project.ProjectStatus.COMPLETED;
import static olegood.rgx.domain.project.ProjectStatus.IN_PROGRESS;
import static olegood.rgx.domain.project.ProjectStatus.ON_HOLD;
import static olegood.rgx.domain.project.ProjectStatus.PROPOSED;
import static olegood.rgx.domain.project.ProjectStatus.UNDER_REVIEW;

import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.project.ProjectStatus;
import olegood.rgx.domain.project.smachine.TypedStateMachine;
import olegood.rgx.domain.project.type.research.Research;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ResearchLifecycleImpl implements ResearchLifecycle {

  private final TypedStateMachine<Research> researchStateMachine;

  @Override
  public void propose(Research research) {
    changeStatus(research, PROPOSED);
  }

  @Override
  public void review(Research research) {
    changeStatus(research, UNDER_REVIEW);
  }

  @Override
  public void approve(Research research) {
    changeStatus(research, APPROVED);
  }

  @Override
  public void reject(Research research) {
    changeStatus(research, CANCELLED);
  }

  @Override
  public void hold(Research research) {
    changeStatus(research, ON_HOLD);
  }

  @Override
  public void start(Research research) {
    changeStatus(research, IN_PROGRESS);
  }

  @Override
  public void complete(Research research) {
    changeStatus(research, COMPLETED);
  }

  @Override
  public void cancel(Research research) {
    changeStatus(research, CANCELLED);
  }

  @Override
  public void resume(Research research) {
    changeStatus(research, IN_PROGRESS);
  }

  @Override
  public void archive(Research research) {
    changeStatus(research, ARCHIVED);
  }

  private void changeStatus(Research research, ProjectStatus status) {
    researchStateMachine.machine().evolve(research, status);
  }
}
