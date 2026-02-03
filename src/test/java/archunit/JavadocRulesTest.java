package archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.library.freeze.FreezingArchRule.freeze;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMember;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchIgnore;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import java.util.Optional;

@ArchIgnore
@AnalyzeClasses(packages = "olegood.rgx")
public class JavadocRulesTest {

  @ArchTest
  static final ArchRule public_classes_should_have_javadoc =
      freeze(
          classes()
              .that()
              .arePublic()
              .and()
              .areNotMemberClasses()
              .and()
              .resideOutsideOfPackage("..test..")
              .and()
              .haveSimpleNameNotEndingWith("Dto")
              .and()
              .haveSimpleNameNotEndingWith("Repository")
              .and()
              .haveSimpleNameNotEndingWith("Builder")
              .should(classHasJavadoc())
              .because("Public classes should be documented with Javadoc"));

  @ArchTest
  static final ArchRule public_interfaces_should_have_javadoc =
      freeze(
          classes()
              .that()
              .areInterfaces()
              .and()
              .arePublic()
              .and()
              .resideOutsideOfPackage("..test..")
              .and()
              .haveSimpleNameNotEndingWith("Dto")
              .and()
              .haveSimpleNameNotEndingWith("Repository")
              .should(classHasJavadoc())
              .because("Public interfaces should be documented with Javadoc"));

  @ArchTest
  static final ArchRule public_methods_should_have_javadoc =
      freeze(
          methods()
              .that()
              .arePublic()
              .and()
              .areDeclaredInClassesThat()
              .arePublic()
              .and()
              .areDeclaredInClassesThat()
              .resideOutsideOfPackage("..test..")
              .and()
              .areDeclaredInClassesThat()
              .haveSimpleNameNotEndingWith("Dto")
              .and()
              .areDeclaredInClassesThat()
              .haveSimpleNameNotEndingWith("Repository")
              .and()
              .areNotDeclaredIn(Object.class)
              .should(memberHasJavadoc())
              .because("Public methods should be documented with Javadoc"));

  private static ArchCondition<JavaClass> classHasJavadoc() {
    return new ArchCondition<>("have Javadoc") {
      @Override
      public void check(JavaClass javaClass, ConditionEvents events) {
        if (!hasJavadocComment(javaClass)) {
          String message = String.format("Class %s does not have Javadoc", javaClass.getFullName());
          events.add(SimpleConditionEvent.violated(javaClass, message));
        }
      }
    };
  }

  private static ArchCondition<JavaMember> memberHasJavadoc() {
    return new ArchCondition<>("have Javadoc") {
      @Override
      public void check(JavaMember member, ConditionEvents events) {
        if (!hasJavadocComment(member)) {
          String message =
              String.format(
                  "Method %s in class %s does not have Javadoc",
                  member.getName(), member.getOwner().getFullName());
          events.add(SimpleConditionEvent.violated(member, message));
        }
      }
    };
  }

  private static boolean hasJavadocComment(JavaClass javaClass) {
    return javaClass.getSource().map(source -> source.toString().contains("/**")).orElse(false);
  }

  private static boolean hasJavadocComment(JavaMember member) {
    return member
        .getOwner()
        .getSource()
        .flatMap(
            source -> {
              try {
                String sourceCode = source.toString();
                String memberName = member.getName();
                int memberIndex = sourceCode.indexOf(memberName);
                if (memberIndex > 0) {
                  String beforeMember =
                      sourceCode.substring(Math.max(0, memberIndex - 500), memberIndex);
                  return Optional.of(beforeMember.trim().endsWith("*/"));
                }
              } catch (Exception e) {
                // Ignore parsing errors
              }
              return Optional.of(false);
            })
        .orElse(false);
  }
}
