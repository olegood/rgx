package archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@AnalyzeClasses(packages = "olegood.rgx")
class GuardrailRulesTest {

  @ArchTest
  static final ArchRule no_field_injection = noFields().should().beAnnotatedWith(Autowired.class);

  @ArchTest
  static final ArchRule no_optional_get = noClasses().should().callMethod(Optional.class, "get");
}
