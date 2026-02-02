package olegood.rgx.service.document.status.impl;

import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.service.document.DocumentStatusService;
import olegood.rgx.service.document.status.Command;
import olegood.rgx.validation.engine.profile.ValidationProfile;
import olegood.rgx.validation.rule.impl.document.HasOwner;
import olegood.rgx.validation.rule.impl.document.HasTitle;
import olegood.rgx.validation.rule.impl.document.IsSubmitReady;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Submit extends Command {

  private final DocumentStatusService documentStatusService;

  @Override
  public DocumentAction action() {
    return DocumentAction.SUBMIT;
  }

  @Override
  public ValidationProfile<Document> validationProfile() {
    return ValidationProfile.withRules(new IsSubmitReady(), new HasTitle(), new HasOwner());
  }

  @Override
  public void accept(Document document) {
    documentStatusService.submit(document);
    if (document.canBeApprovedAutomatically()) {
      documentStatusService.approveAutomatically(document);
    }
  }
}
