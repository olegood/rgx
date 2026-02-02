package olegood.rgx.api.document;

import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.service.document.DocumentStatusHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/documents/{documentId}/status")
public class DocumentStatusController {

  private final DocumentStatusHandler documentStatusHandler;

  @PutMapping
  public Document handleAction(@PathVariable Long documentId, @RequestParam DocumentAction action) {
    return documentStatusHandler.handleAction(documentId, action);
  }
}
