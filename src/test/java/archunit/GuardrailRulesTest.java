package archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@AnalyzeClasses(packages = "olegood.rgx")
class GuardrailRulesTest {

  @ArchTest
  static final ArchRule no_field_injection =
      fields()
          .that()
          .areDeclaredInClassesThat()
          .areNotAnnotatedWith(SpringBootTest.class)
          .should()
          .notBeAnnotatedWith(Autowired.class)
          .andShould()
          .notBeAnnotatedWith(Value.class)
          .andShould()
          .notBeAnnotatedWith(Inject.class)
          .andShould()
          .notBeAnnotatedWith(Resource.class)
          .because(
              "Constructor injection should be used instead of field injection for better testability and immutability");

  @ArchTest
  static final ArchRule no_optional_get = noClasses().should().callMethod(Optional.class, "get");
}
