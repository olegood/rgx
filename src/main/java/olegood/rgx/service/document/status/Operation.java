package olegood.rgx.service.document.status;

import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentAction;

/**
 * Represents an operation that can be executed on a {@link Document}.
 * Each implementation defines its own logic for the operation and specifies
 * the associated {@link DocumentAction}.
 */
public interface Operation {

  /**
   * Retrieves the action associated with this operation.
   *
   * @return the {@link DocumentAction} associated with this operation
   */
  DocumentAction associatedAction();

  /**
   * This method performs the core logic associated with the operation on the provided document.
   *
   * @param document the document on which the operation is to be executed
   */
  void execute(Document document);

  /**
   * Determines whether the specified document allows the operation's associated action to be executed.
   *
   * @param document the document to be evaluated for action permission
   * @return {@code true} if the associated action is allowed on the document, {@code false} otherwise
   */
  default boolean isAllowed(Document document) {
    return document.isActionAllowed(associatedAction());
  }

  /**
   * Determines whether a given document meets the minimum criteria to be processed for a specific operation.
   *
   * @param document the document to evaluate for eligibility
   * @return {@code true} if the document is eligible for the operation, {@code false} otherwise
   */
  default boolean isEligible(Document document) {
    return true;
  }
}
