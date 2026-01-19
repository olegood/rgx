package olegood.rgx.domain.project.type.research.lifecycle;

import lombok.extern.slf4j.Slf4j;
import olegood.rgx.domain.project.type.research.event.ResearchApproved;
import olegood.rgx.domain.project.type.research.event.ResearchArchived;
import olegood.rgx.domain.project.type.research.event.ResearchCancelled;
import olegood.rgx.domain.project.type.research.event.ResearchCompleted;
import olegood.rgx.domain.project.type.research.event.ResearchOnHold;
import olegood.rgx.domain.project.type.research.event.ResearchProposed;
import olegood.rgx.domain.project.type.research.event.ResearchRejected;
import olegood.rgx.domain.project.type.research.event.ResearchResumed;
import olegood.rgx.domain.project.type.research.event.ResearchStarted;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResearchEventsManager {

  @EventListener(ResearchApproved.class)
  public void onApproval(ResearchApproved event) {
    log.info("Research approved. {}", event.research());
  }

  @EventListener(ResearchArchived.class)
  public void onArchival(ResearchArchived event) {
    log.info("Research archived. {}", event.research());
  }

  @EventListener(ResearchCancelled.class)
  public void onCancellation(ResearchCancelled event) {
    log.info("Research cancelled. {}", event.research());
  }

  @EventListener(ResearchCompleted.class)
  public void onCompletion(ResearchCompleted event) {
    log.info("Research completed. {}", event.research());
  }

  @EventListener(ResearchOnHold.class)
  public void onHold(ResearchOnHold event) {
    log.info("Research on hold. {}", event.research());
  }

  @EventListener(ResearchProposed.class)
  public void onProposal(ResearchProposed event) {
    log.info("Research proposed. {}", event.research());
  }

  @EventListener(ResearchRejected.class)
  public void onRejection(ResearchRejected event) {
    log.info("Research rejected. {}", event.research());
  }

  @EventListener(ResearchResumed.class)
  public void onResume(ResearchResumed event) {
    log.info("Research resumed. {}", event.research());
  }

  @EventListener(ResearchStarted.class)
  public void onStart(ResearchStarted event) {
    log.info("Research started. {}", event.research());
  }
}
