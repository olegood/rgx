package olegood.rgx.validation.engine.profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Set;
import olegood.rgx.validation.rule.Rule;
import org.junit.jupiter.api.Test;

class ValidationProfileTest {

  @Test
  void shouldReturnRulesWhenMatcherSucceeds() {
    // given
    Rule<String> ruleMock = mock(Rule.class);
    Set<Rule<String>> rules = Set.of(ruleMock);
    ValidationProfile<String> profile = new ValidationProfile<>(s -> s.contains("valid"), rules);

    // when
    Set<Rule<String>> result = profile.getRulesFor("valid input");

    // then
    assertThat(result).isEqualTo(rules);
  }

  @Test
  void shouldReturnEmptySetWhenMatcherFails() {
    // given
    Rule<String> ruleMock = mock(Rule.class);
    Set<Rule<String>> rules = Set.of(ruleMock);
    ValidationProfile<String> profile = new ValidationProfile<>(s -> s.contains("valid"), rules);

    // when
    Set<Rule<String>> result = profile.getRulesFor("incorrect input");

    // then
    assertThat(result).isEmpty();
  }

  @Test
  void shouldAlwaysReturnEmptySetWithEmptyValidationProfile() {
    // given
    ValidationProfile<String> profile = ValidationProfile.empty();

    // when
    Set<Rule<String>> result = profile.getRulesFor("any input");

    // then
    assertThat(result).isEmpty();
  }

  @Test
  void shouldRespectCustomMatcherWithConditionalRules() {
    // given
    Rule<String> mockRule1 = mock(Rule.class);
    Rule<String> mockRule2 = mock(Rule.class);
    ValidationProfile<String> profile =
        ValidationProfile.withConditionalRules(s -> s.startsWith("A"), mockRule1, mockRule2);

    // when
    Set<Rule<String>> resultMatching = profile.getRulesFor("Apple");
    Set<Rule<String>> resultNonMatching = profile.getRulesFor("Banana");

    // then
    assertThat(resultMatching).containsExactlyInAnyOrder(mockRule1, mockRule2);
    assertThat(resultNonMatching).isEmpty();
  }

  @Test
  void shouldAlwaysReturnRulesWithWithRulesFactoryMethod() {
    // given
    Rule<String> mockRule1 = mock(Rule.class);
    Rule<String> mockRule2 = mock(Rule.class);
    ValidationProfile<String> profile = ValidationProfile.withRules(mockRule1, mockRule2);

    // when
    Set<Rule<String>> result = profile.getRulesFor("any input");

    // then
    assertThat(result).containsExactlyInAnyOrder(mockRule1, mockRule2);
  }
}
