package olegood.rgx.service.document.status;

import java.util.Optional;
import java.util.function.Consumer;
import olegood.rgx.api.ApplicationException;
import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;
import olegood.rgx.validation.engine.ValidationEngine;
import olegood.rgx.validation.engine.profile.ValidationProfile;

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
      throw ApplicationException.builder()
          .message("Invalid request")
          .error(
              "Cannot execute command '%s' on document [ID: %s]: current status '%s' does not allow this action."
                  .formatted(action(), document.getId(), document.getStatus()))
          .build();
    }
  }

  private void applyValidationProfile(Document document) {
    var profile = validationProfile();
    var result = new ValidationEngine<>(profile).validate(document);
    if (result.hasViolations()) {
      throw ApplicationException.builder()
          .message("Validation failed")
          .errors(result.violationMessages())
          .build();
    }
  }

  /**
   * Executes the command on the given document. Before executing the command, it ensures that the
   * command is allowed to be performed on the document based on its current state and validation
   * rules.
   *
   * @param document the document on which the command is to be executed
   */
  public final void execute(final Document document) {
    ensureCommandAllowed(document);
    applyValidationProfile(document);
    accept(document);
  }
}
