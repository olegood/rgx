package olegood.rgx.validation.engine;

import java.util.Collection;
import lombok.Builder;
import lombok.Singular;
import olegood.rgx.validation.rule.Rule;

/**
 * Represents a validation profile that encapsulates a collection of validation rules
 * for a specific type of object. It serves as a configuration for validation engines
 * that apply these rules to validate target objects.
 *
 * @param <T> the type of the object that the validation rules will be applied to
 */
@Builder
public record ValidationProfile<T>(@Singular Collection<Rule<T>> rules) {}
