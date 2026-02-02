package olegood.rgx.validation.engine;

/**
 * Violation with an associated message describing the nature of the violation.
 * <p>
 * The {@code message} human-readable information about why the violation occurred.
 */
public record Violation(String message) {}
