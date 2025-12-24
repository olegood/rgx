package archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@AnalyzeClasses(packages = "olegood.rgx")
class AnnotationRulesTest {

  @ArchTest
  static final ArchRule entities_must_have_table_annotation =
      classes()
          .that()
          .areAnnotatedWith(Entity.class)
          .should()
          .beAnnotatedWith(Table.class)
          .because("JPA entities must explicitly declare table mapping");
}
