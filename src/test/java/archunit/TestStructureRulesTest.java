package archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "olegood.rgx")
class TestStructureRulesTest {

  @ArchTest
  static final ArchRule production_code_should_not_depend_on_tests =
      noClasses()
          .that()
          .resideOutsideOfPackage("..test..")
          .should()
          .dependOnClassesThat()
          .resideInAPackage("..test..");
}
