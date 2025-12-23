package olegood.rgx.api;

import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.service.document.DocumentStatusHandler;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/documents/{documentId}/status")
public class DocumentStatusController {

  private final DocumentStatusHandler documentStatusHandler;

  @PutMapping
  public void handleDocumentAction(
      @PathVariable("documentId") Long id, @RequestParam DocumentAction action) {
    documentStatusHandler.handleAction(id, action);
  }
}
