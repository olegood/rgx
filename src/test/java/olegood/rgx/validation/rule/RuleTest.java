package olegood.rgx.validation.rule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import olegood.rgx.validation.engine.Violation;
import org.junit.jupiter.api.Test;

class RuleTest {

    @Test
    void shouldReturnEmptyOptionalWhenRuleIsSatisfied() {
        // given: a rule that always passes
        Rule<String> rule = new Rule<>("Violation message") {
            @Override
            public boolean test(String target) {
                return target.contains("valid");
            }
        };

        String target = "this is valid";

        // when: the check method is called
        Optional<Violation> result = rule.check(target);

        // then: no violation is returned
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnViolationWhenRuleIsNotSatisfied() {
        // given: a rule that fails when the target does not include "valid"
        Rule<String> rule = new Rule<>("Violation message") {
            @Override
            public boolean test(String target) {
                return target.contains("valid");
            }
        };

        String target = "this is incorrect";

        // when: the check method is called
        Optional<Violation> result = rule.check(target);

        // then: the violation is returned with the correct message
        assertThat(result).isPresent();
        assertThat(result.get().message()).isEqualTo("Violation message");
    }

    @Test
    void shouldHandleNullTargetGracefully() {
        // given: a rule that checks for non-null targets
        Rule<String> rule = new Rule<>("Target cannot be null") {
            @Override
            public boolean test(String target) {
                return target != null;
            }
        };

        String target = null;

        // when: the check method is called with a null target
        Optional<Violation> result = rule.check(target);

        // then: the violation is returned with the correct message
        assertThat(result).isPresent();
        assertThat(result.get().message()).isEqualTo("Target cannot be null");
    }
}
