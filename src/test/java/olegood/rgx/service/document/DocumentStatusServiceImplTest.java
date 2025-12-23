package olegood.rgx.service.document;

import static olegood.rgx.domain.document.DocumentStatus.APPROVED;
import static olegood.rgx.domain.document.DocumentStatus.ARCHIVED;
import static olegood.rgx.domain.document.DocumentStatus.DRAFT;
import static olegood.rgx.domain.document.DocumentStatus.IN_REVIEW;
import static olegood.rgx.domain.document.DocumentStatus.TERMINATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;

import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DocumentStatusServiceImplTest {

  @Mock private DocumentRepository documentRepository;

  @InjectMocks private DocumentStatusServiceImpl documentStatusService;

  @Test
  void shouldChangeStatusToInReviewAndSaveWhenSubmit() {
    // given
    var document = new Document().setStatus(DRAFT);

    // when
    documentStatusService.submit(document);

    // then
    assertThat(document.getStatus()).isEqualTo(IN_REVIEW);
    verify(documentRepository).save(document);
  }

  @Test
  void shouldChangeStatusToApprovedAndSaveWhenApprove() {
    // given
    var document = new Document().setStatus(IN_REVIEW);

    // when
    documentStatusService.approve(document);

    // then
    assertThat(document.getStatus()).isEqualTo(APPROVED);
    verify(documentRepository).save(document);
  }

  @Test
  void shouldChangeStatusToApprovedAndSaveWhenApproveAutomatically() {
    // given
    var document = new Document().setStatus(IN_REVIEW);

    // when
    documentStatusService.approveAutomatically(document);

    // then
    assertThat(document.getStatus()).isEqualTo(APPROVED);
    verify(documentRepository).save(document);
  }

  @Test
  void shouldChangeStatusToDraftAndSaveWhenReject() {
    // given
    var document = new Document().setStatus(APPROVED);

    // when
    documentStatusService.reject(document);

    // then
    assertThat(document.getStatus()).isEqualTo(DRAFT);
    verify(documentRepository).save(document);
  }

  @Test
  void shouldChangeStatusToTerminatedAndSaveWhenTerminate() {
    // given
    var document = new Document().setStatus(APPROVED);

    // when
    documentStatusService.terminate(document);

    // then
    assertThat(document.getStatus()).isEqualTo(TERMINATED);
    verify(documentRepository).save(document);
  }

  @Test
  void shouldChangeStatusToArchivedAndSaveWhenArchive() {
    // given
    var document = new Document().setStatus(APPROVED);

    // when
    documentStatusService.archive(document);

    // then
    assertThat(document.getStatus()).isEqualTo(ARCHIVED);
    verify(documentRepository).save(document);
  }

  @Test
  void shouldChangeStatusToInReviewAndSaveWhenApproved() {
    // given
    var document = new Document().setStatus(APPROVED);

    // when
    documentStatusService.reopen(document);

    // then
    assertThat(document.getStatus()).isEqualTo(IN_REVIEW);
    verify(documentRepository).save(document);
  }

  @Test
  void shouldChangeStatusToDraftAndSaveWhenTerminated() {
    // given
    var document = new Document().setStatus(TERMINATED);

    // when
    documentStatusService.reopen(document);

    // then
    assertThat(document.getStatus()).isEqualTo(DRAFT);
    verify(documentRepository).save(document);
  }

  @Test
  void shouldThrowAnExceptionWhenCannotReopen() {
    // when
    var document = new Document().setStatus(DRAFT);

    // then
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> documentStatusService.reopen(document))
        .withMessage("Cannot reopen document in status DRAFT");
  }
}
