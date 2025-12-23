package olegood.rgx.service.document.status.impl;

import static olegood.rgx.domain.document.DocumentAction.APPROVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import olegood.rgx.domain.document.Document;
import olegood.rgx.service.document.DocumentStatusService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ApproveTest {

  @Mock private DocumentStatusService documentStatusService;

  @InjectMocks private Approve approve;

  @Test
  void associatedActionIsApprove() {
    // when
    var action = approve.associatedAction();

    // then
    assertThat(action).isEqualTo(APPROVE);
  }

  @Test
  void shouldCallApprove() {
    // when
    var document = new Document();

    // then
    approve.execute(document);

    // then
    verify(documentStatusService).approve(document);
  }
}
