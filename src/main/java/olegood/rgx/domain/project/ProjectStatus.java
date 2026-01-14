package olegood.rgx.domain.project;

public enum ProjectStatus {

  /**
   * Project is defined but not yet actionable.
   * Used by: all project types.
   */
  DRAFT,

  /**
   * Initial validation or qualification phase.
   * Used by: Discovery, Research, Commercial.
   */
  PROPOSED,

  /**
   * Actively being assessed for feasibility, value, or risk.
   * Used by: Discovery, Research, Commercial.
   */
  UNDER_REVIEW,

  /**
   * Explicitly approved to proceed based on rules
   * (confidence thresholds, risk, governance).
   * Used by: Research, Delivery, Commercial.
   */
  APPROVED,

  /**
   * Planning and structuring work before execution.
   * Used by: Delivery, Commercial.
   */
  PLANNED,

  /**
   * Active execution phase.
   * Used by: Discovery, Research, Delivery, Operations.
   */
  IN_PROGRESS,

  /**
   * Commercial negotiation or contractual discussion.
   * Used by: Commercial.
   */
  IN_NEGOTIATION,

  /**
   * Work is paused intentionally.
   * Used by: all project types.
   */
  ON_HOLD,

  /**
   * Project reached its intended successful outcome.
   * Meaning varies by subtype:
   * - Discovery: validated learning
   * - Research: finished study
   * - Delivery: accepted delivery
   * - Commercial: deal won
   */
  COMPLETED,

  /**
   * Project terminated unsuccessfully.
   * Used by: all project types.
   */
  CANCELLED,

  /**
   * Commercially unsuccessful outcome.
   * Used by: Commercial.
   */
  LOST,

  /**
   * No longer active, kept for audit/history only.
   * Used by: all project types.
   */
  ARCHIVED
}
