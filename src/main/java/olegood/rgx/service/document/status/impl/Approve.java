package olegood.rgx.service.document.status.impl;

import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.service.document.DocumentStatusService;
import olegood.rgx.service.document.status.Command;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Approve extends Command {

  private final DocumentStatusService documentStatusService;

  @Override
  public DocumentAction action() {
    return DocumentAction.APPROVE;
  }

  @Override
  public void accept(Document document) {
    documentStatusService.approve(document);
  }
}
