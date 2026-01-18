package archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.freeze.FreezingArchRule.freeze;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "olegood.rgx")
class TestStructureRulesTest {

  @ArchTest
  static final ArchRule assertions_should_use_assertj_and_not_junit_jupiter =
      freeze(
          noClasses()
              .should()
              .dependOnClassesThat()
              .haveFullyQualifiedName("org.junit.jupiter.api.Assertions")
              .because(
                  "AssertJ provides better readability and more powerful assertion capabilities."));
}
