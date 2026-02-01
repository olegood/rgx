package olegood.rgx.validation.engine;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ValidationResultTest {

  @Test
  void shouldCreateValidationResultWithNoViolationsWhenEmptyCollectionProvided() {
    // given
    var violations = List.<Violation>of();

    // when
    var result = ValidationResult.of(violations);

    // then
    assertThat(result)
        .isNotNull()
        .extracting(ValidationResult::hasViolations, ValidationResult::violationMessages)
        .containsExactly(false, Set.of());
  }

  @Test
  void shouldCreateValidationResultWithSingleViolation() {
    // given
    var singleViolation = new Violation("Field is required");
    var violations = List.of(singleViolation);

    // when
    var result = ValidationResult.of(violations);

    // then
    assertThat(result).isNotNull();
    assertThat(result.hasViolations()).isTrue();
    assertThat(result.violationMessages()).containsExactly("Field is required");
  }

  @Test
  void shouldCreateValidationResultWithMultipleViolations() {
    // given
    var violation1 = new Violation("Field is required");
    var violation2 = new Violation("Value must be greater than zero");
    var violations = List.of(violation1, violation2);

    // when
    var result = ValidationResult.of(violations);

    // then
    assertThat(result).isNotNull();
    assertThat(result.hasViolations()).isTrue();
    assertThat(result.violationMessages())
        .containsExactlyInAnyOrder("Field is required", "Value must be greater than zero");
  }

  @Test
  void shouldDeduplicateViolationsWithSameMessage() {
    // given
    var duplicateViolation1 = new Violation("Field is required");
    var duplicateViolation2 = new Violation("Field is required");
    var violations = List.of(duplicateViolation1, duplicateViolation2);

    // when
    var result = ValidationResult.of(violations);

    // then
    assertThat(result).isNotNull();
    assertThat(result.hasViolations()).isTrue();
    assertThat(result.violationMessages()).hasSize(1).containsExactly("Field is required");
  }

  @Test
  void shouldHandleViolationWithNullMessage() {
    // given
    var nullMessageViolation = new Violation(null);
    var violations = List.of(nullMessageViolation);

    // when
    var result = ValidationResult.of(violations);

    // then
    assertThat(result).isNotNull();
    assertThat(result.hasViolations()).isTrue();
    assertThat(result.violationMessages()).containsExactly((String) null);
  }
}
