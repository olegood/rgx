package olegood.rgx.service.document.status.impl;

import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.service.document.DocumentStatusService;
import olegood.rgx.service.document.status.DocumentOperation;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Terminate implements DocumentOperation {

  private final DocumentStatusService documentStatusService;

  @Override
  public DocumentAction associatedAction() {
    return DocumentAction.TERMINATE;
  }

  @Override
  public void execute(Document document) {
    documentStatusService.terminate(document);
  }
}
