package olegood.rgx.service.document.status.impl;

import static olegood.rgx.domain.document.DocumentAction.ARCHIVE;
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
class ArchiveTest {

  @Mock private DocumentStatusService documentStatusService;

  @InjectMocks private Archive archive;

  @Test
  void associatedActionIsArchive() {
    // when
    var action = archive.associatedAction();

    // then
    assertThat(action).isEqualTo(ARCHIVE);
  }

  @Test
  void shouldCallArchive() {
    // when
    var document = new Document();

    // then
    archive.execute(document);

    // then
    verify(documentStatusService).archive(document);
  }
}
