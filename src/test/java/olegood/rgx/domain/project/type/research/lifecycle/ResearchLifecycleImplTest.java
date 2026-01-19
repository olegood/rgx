package olegood.rgx.domain.project.type.research.lifecycle;

import static olegood.rgx.domain.project.ProjectStatus.PROPOSED;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import olegood.rgx.domain.project.ProjectStatus;
import olegood.rgx.domain.project.smachine.StateMachine;
import olegood.rgx.domain.project.smachine.TypedStateMachine;
import olegood.rgx.domain.project.type.research.Research;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResearchLifecycleImplTest {

  @Mock private TypedStateMachine<Research> researchStateMachine;

  @Mock private StateMachine<Research> stateMachine;

  @InjectMocks private ResearchLifecycleImpl researchLifecycle;

  @Test
  void shouldChangeStatusToProposedWhenValidResearchGiven() {
    // given
    var research = createResearchInDraftState();
    when(researchStateMachine.machine()).thenReturn(stateMachine);

    // when
    researchLifecycle.propose(research);

    // then
    verify(researchStateMachine.machine()).evolve(research, PROPOSED);
    verifyNoMoreInteractions(researchStateMachine);
  }

  private Research createResearchInDraftState() {
    var research = new Research();
    research.setStatus(ProjectStatus.DRAFT);
    return research;
  }
}
