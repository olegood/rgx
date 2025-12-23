package olegood.rgx.domain.document;

import static olegood.rgx.domain.document.DocumentAction.APPROVE;
import static olegood.rgx.domain.document.DocumentAction.ARCHIVE;
import static olegood.rgx.domain.document.DocumentAction.REJECT;
import static olegood.rgx.domain.document.DocumentAction.REOPEN;
import static olegood.rgx.domain.document.DocumentAction.SUBMIT;
import static olegood.rgx.domain.document.DocumentAction.TERMINATE;

import java.util.Set;
import lombok.Getter;

/**
 * Enum representing the various statuses a document can have throughout its lifecycle.
 * Each status reflects a different stage in the document's workflow,
 * dictating possible actions that can be performed in that state.
 * <p>
 * The allowed actions for each state are encapsulated in the enum constants,
 * making it easy to determine what actions are available depending on the current status.
 */
@Getter
public enum DocumentStatus {

  /**
   * Represents the initial state of a document where it is created but not yet submitted for review.
   * In this state, the document's available actions include:
   * - SUBMIT: To submit the document for review.
   * - TERMINATE: To terminate the document, halting any further activity.
   */
  DRAFT(SUBMIT, TERMINATE),

  /**
   * Represents the state of a document that has been submitted for review and is awaiting a decision.
   * In this state, the document's available actions include:
   * - APPROVE: To approve the document, marking it as finalized and accepted.
   * - REJECT: To reject the document, requiring further revisions or halting its progress.
   * - TERMINATE: To terminate the document, stopping any further actions.
   */
  IN_REVIEW(APPROVE, REJECT, TERMINATE),

  /**
   * Represents the state of a document that has been approved. This state indicates
   * that the document has passed through the review process and is formally accepted.
   *
   * In the APPROVED state, the following actions are allowed:
   * - ARCHIVE: To move the document to an archived state for record-keeping.
   * - REOPEN: To reopen the document, allowing further discussion or changes.
   * - TERMINATE: To terminate the document, ending its lifecycle.
   */
  APPROVED(ARCHIVE, REOPEN, TERMINATE),

  /**
   * Represents the TERMINATED state of a document. This state indicates that
   * the document’s lifecycle is considered complete, with no further actions
   * or transitions permitted except for reopening the document.
   *
   * The TERMINATED state allows the following action:
   * - REOPEN: To transition the document back to an active state, enabling it
   *   for further workflows or edits.
   */
  TERMINATED(REOPEN),

  /**
   * Represents the state of a document that has been archived, signifying that
   * it is stored for record-keeping purposes and no further modifications
   * or transitions are allowed. This is a final state for the document,
   * indicating completion of its lifecycle.
   */
  ARCHIVED;

  private final Set<DocumentAction> availableActions;

  DocumentStatus(DocumentAction... actions) {
    this.availableActions = Set.of(actions);
  }

  public boolean isActionAllowed(DocumentAction action) {
    return availableActions.contains(action);
  }
}
