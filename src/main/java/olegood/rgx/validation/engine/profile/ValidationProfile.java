package olegood.rgx.validation.engine.profile;

import static java.util.Collections.emptySet;

import java.util.Set;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import olegood.rgx.validation.rule.Rule;

@RequiredArgsConstructor
public final class ValidationProfile<T> {

  private final Predicate<T> matcher;
  private final Set<Rule<T>> rules;

  public Set<Rule<T>> getRulesFor(T target) {
    return matcher.test(target) ? rules : emptySet();
  }

  public static <T> ValidationProfile<T> empty() {
    return new ValidationProfile<>(t -> true, emptySet());
  }

  @SafeVarargs
  public static <T> ValidationProfile<T> withRules(Rule<T>... rules) {
    return withConditionalRules(t -> true, rules);
  }

  @SafeVarargs
  public static <T> ValidationProfile<T> withConditionalRules(
      Predicate<T> matcher, Rule<T>... rules) {
    return new ValidationProfile<>(matcher, Set.of(rules));
  }
}
