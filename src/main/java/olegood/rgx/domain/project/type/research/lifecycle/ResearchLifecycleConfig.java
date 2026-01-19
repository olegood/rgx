package olegood.rgx.domain.project.type.research.lifecycle;

import static olegood.rgx.domain.project.ProjectStatus.APPROVED;
import static olegood.rgx.domain.project.ProjectStatus.ARCHIVED;
import static olegood.rgx.domain.project.ProjectStatus.CANCELLED;
import static olegood.rgx.domain.project.ProjectStatus.COMPLETED;
import static olegood.rgx.domain.project.ProjectStatus.DRAFT;
import static olegood.rgx.domain.project.ProjectStatus.IN_PROGRESS;
import static olegood.rgx.domain.project.ProjectStatus.ON_HOLD;
import static olegood.rgx.domain.project.ProjectStatus.PROPOSED;
import static olegood.rgx.domain.project.ProjectStatus.UNDER_REVIEW;

import olegood.rgx.domain.project.smachine.StateMachineBuilder;
import olegood.rgx.domain.project.smachine.TypedStateMachine;
import olegood.rgx.domain.project.type.research.Research;
import olegood.rgx.domain.project.type.research.event.ResearchApproved;
import olegood.rgx.domain.project.type.research.event.ResearchArchived;
import olegood.rgx.domain.project.type.research.event.ResearchCancelled;
import olegood.rgx.domain.project.type.research.event.ResearchCompleted;
import olegood.rgx.domain.project.type.research.event.ResearchOnHold;
import olegood.rgx.domain.project.type.research.event.ResearchProposed;
import olegood.rgx.domain.project.type.research.event.ResearchRejected;
import olegood.rgx.domain.project.type.research.event.ResearchResumed;
import olegood.rgx.domain.project.type.research.event.ResearchStarted;
import olegood.rgx.domain.project.type.research.guard.EligibleForApproval;
import olegood.rgx.domain.project.type.research.guard.timeline.ResearchTimelineIsComplete;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResearchLifecycleConfig {

  @Bean
  public TypedStateMachine<Research> researchStateMachine(ApplicationEventPublisher events) {
    return () ->
        StateMachineBuilder.<Research>begin()
            // DRAFT -> PROPOSED
            .from(DRAFT)
            .to(PROPOSED)
            .fires(ResearchProposed::new)

            // PROPOSED -> UNDER_REVIEW
            .from(PROPOSED)
            .to(UNDER_REVIEW)

            // UNDER_REVIEW -> APPROVED
            .from(UNDER_REVIEW)
            .to(APPROVED)
            .withGuard(new EligibleForApproval())
            .fires(ResearchApproved::new)

            // UNDER_REVIEW -> CANCELLED
            .from(UNDER_REVIEW)
            .to(CANCELLED)
            .fires(ResearchRejected::new)

            // UNDER_REVIEW -> ON_HOLD (fallback branch)
            .from(UNDER_REVIEW)
            .to(ON_HOLD)
            .fires(ResearchOnHold::new)

            // APPROVED -> IN_PROGRESS
            .from(APPROVED)
            .to(IN_PROGRESS)
            .fires(ResearchStarted::new)

            // IN_PROGRESS -> COMPLETED
            .from(IN_PROGRESS)
            .to(COMPLETED)
            .withGuard(new ResearchTimelineIsComplete())
            .fires(ResearchCompleted::new)

            // IN_PROGRESS -> CANCELLED
            .from(IN_PROGRESS)
            .to(CANCELLED)
            .fires(ResearchCancelled::new)

            // IN_PROGRESS -> ON_HOLD (fallback branch)
            .from(IN_PROGRESS)
            .to(ON_HOLD)
            .fires(ResearchOnHold::new)

            // ON_HOLD -> IN_PROGRESS
            .from(ON_HOLD)
            .to(IN_PROGRESS)
            .fires(ResearchResumed::new)

            // COMPLETED -> ARCHIVED
            .from(COMPLETED)
            .to(ARCHIVED)
            .fires(ResearchArchived::new)

            // CANCELLED -> ARCHIVED
            .from(CANCELLED)
            .to(ARCHIVED)
            .fires(ResearchArchived::new)

            // construct
            .build(events);
  }
}
