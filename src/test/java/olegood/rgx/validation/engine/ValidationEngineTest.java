package olegood.rgx.validation.engine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import olegood.rgx.validation.engine.profile.ValidationProfile;
import olegood.rgx.validation.rule.Rule;
import org.junit.jupiter.api.Test;

class ValidationEngineTest {

  @Test
  void shouldReturnValidationResultWithoutViolationsWhenNoRulesApply() {
    // given
    ValidationProfile<String> profileMock = mock(ValidationProfile.class);
    when(profileMock.getRulesFor(anyString())).thenReturn(Set.of());
    ValidationEngine<String> validationEngine = new ValidationEngine<>(profileMock);

    // when
    ValidationResult result = validationEngine.validate("some input");

    // then
    assertThat(result).isNotNull();
    assertThat(result.hasViolations()).isFalse();
    assertThat(result.violationMessages()).isEmpty();
    verify(profileMock).getRulesFor("some input");
  }

  @Test
  void shouldCaptureSingleViolationForGivenTarget() {
    // given
    Rule<String> ruleMock = mock(Rule.class);
    Violation violation = new Violation("Input cannot be empty");
    when(ruleMock.check(anyString())).thenReturn(Optional.of(violation));
    ValidationProfile<String> profileMock = mock(ValidationProfile.class);
    when(profileMock.getRulesFor(anyString())).thenReturn(Set.of(ruleMock));

    ValidationEngine<String> validationEngine = new ValidationEngine<>(profileMock);

    // when
    ValidationResult result = validationEngine.validate("target");

    // then
    assertThat(result).isNotNull();
    assertThat(result.hasViolations()).isTrue();
    assertThat(result.violationMessages()).containsExactly("Input cannot be empty");
    verify(profileMock).getRulesFor("target");
    verify(ruleMock).check("target");
  }

  @Test
  void shouldCaptureMultipleViolationsForGivenTarget() {
    // given
    Rule<String> ruleMock1 = mock(Rule.class);
    Rule<String> ruleMock2 = mock(Rule.class);
    Violation violation1 = new Violation("Input cannot be empty");
    Violation violation2 = new Violation("Input is too short");
    when(ruleMock1.check(anyString())).thenReturn(Optional.of(violation1));
    when(ruleMock2.check(anyString())).thenReturn(Optional.of(violation2));
    ValidationProfile<String> profileMock = mock(ValidationProfile.class);
    when(profileMock.getRulesFor(anyString())).thenReturn(Set.of(ruleMock1, ruleMock2));

    ValidationEngine<String> validationEngine = new ValidationEngine<>(profileMock);

    // when
    ValidationResult result = validationEngine.validate("target");

    // then
    assertThat(result).isNotNull();
    assertThat(result.hasViolations()).isTrue();
    assertThat(result.violationMessages())
        .containsExactlyInAnyOrder("Input cannot be empty", "Input is too short");
    verify(profileMock).getRulesFor("target");
    verify(ruleMock1).check("target");
    verify(ruleMock2).check("target");
  }

  @Test
  void shouldIgnoreOptionalEmptyViolations() {
    // given
    Rule<String> ruleMock = mock(Rule.class);
    when(ruleMock.check(anyString())).thenReturn(Optional.empty());
    ValidationProfile<String> profileMock = mock(ValidationProfile.class);
    when(profileMock.getRulesFor(anyString())).thenReturn(Set.of(ruleMock));

    ValidationEngine<String> validationEngine = new ValidationEngine<>(profileMock);

    // when
    ValidationResult result = validationEngine.validate("target");

    // then
    assertThat(result).isNotNull();
    assertThat(result.hasViolations()).isFalse();
    assertThat(result.violationMessages()).isEmpty();
    verify(profileMock).getRulesFor("target");
    verify(ruleMock).check("target");
  }

  @Test
  void shouldHandleEmptyValidationProfile() {
    // given
    ValidationProfile<String> profile = ValidationProfile.empty();
    ValidationEngine<String> validationEngine = new ValidationEngine<>(profile);

    // when
    ValidationResult result = validationEngine.validate("any input");

    // then
    assertThat(result).isNotNull();
    assertThat(result.hasViolations()).isFalse();
    assertThat(result.violationMessages()).isEmpty();
  }
}
