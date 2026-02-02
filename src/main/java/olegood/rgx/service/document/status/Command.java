package olegood.rgx.service.document.status;

import java.util.Optional;
import java.util.function.Consumer;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.validation.engine.ValidationEngine;
import olegood.rgx.validation.engine.profile.ValidationProfile;
import olegood.rgx.validation.engine.profile.ValidationProfileException;

/**
 * Represents an operation that can be executed on a {@link Document}. Each implementation defines
 * its own logic for the operation and specifies the associated {@link DocumentAction}.
 */
public abstract class Command implements Consumer<Document> {

  /**
   * Retrieves the action associated with this operation.
   *
   * @return the {@link DocumentAction} associated with this operation
   */
  public abstract DocumentAction action();

  /**
   * A validation profile contains a set of rules that can be applied to validate a {@link
   * Document}.
   *
   * @return an {@link Optional} containing the {@link ValidationProfile} for documents, or {@link
   *     Optional#empty()} if no validation profile is associated with this operation
   */
  public ValidationProfile<Document> validationProfile() {
    return ValidationProfile.empty();
  }

  private void ensureCommandAllowed(Document document) {
    if (!document.isAllowed(action())) {
      throw new UnsupportedOperationException(
          "Cannot execute operation '%s' on document [ID: %s]: current status '%s' does not allow this action."
              .formatted(action(), document.getId(), document.getStatus()));
    }
  }

  private void applyValidationProfile(Document document) {
    var profile = validationProfile();
    var result = new ValidationEngine<>(profile).validate(document);
    if (result.hasViolations()) {
      throw new ValidationProfileException(result.violationMessages());
    }
  }

  /**
   * This method performs the core logic associated with the operation on the provided document.
   *
   * @param document the document on which the operation is to be executed
   */
  public void execute(final Document document) {
    ensureCommandAllowed(document);
    applyValidationProfile(document);
    accept(document);
  }
}
