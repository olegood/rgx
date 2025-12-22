package olegood.rgx.service.document.status.impl;

import olegood.rgx.domain.document.Document;
import olegood.rgx.service.document.DocumentStatusService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static olegood.rgx.domain.document.DocumentAction.REJECT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RejectTest {

    @Mock
    private DocumentStatusService documentStatusService;

    @InjectMocks
    private Reject reject;

    @Test
    void associatedActionIsReject() {
        // when
        var action = reject.associatedAction();

        // then
        assertThat(action).isEqualTo(REJECT);
    }

    @Test
    void shouldCallReject() {
        // when
        var document = new Document();

        // then
        reject.execute(document);

        // then
        verify(documentStatusService).reject(document);
    }

}
