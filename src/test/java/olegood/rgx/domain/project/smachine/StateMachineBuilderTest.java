package olegood.rgx.domain.project.smachine;

import static olegood.rgx.domain.project.ProjectStatus.DRAFT;
import static olegood.rgx.domain.project.ProjectStatus.PROPOSED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.function.Function;
import olegood.rgx.domain.project.Project;
import olegood.rgx.domain.project.ProjectEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class StateMachineBuilderTest {

  @Mock private Function<Project, ProjectEvent> eventFactory;

  @Mock private ApplicationEventPublisher eventPublisher;

  @Test
  void shouldCreateNewStateMachineBuilderInstanceOnBegin() {
    // when
    var builder = StateMachineBuilder.begin();

    // then
    assertThat(builder).as("StateMachineBuilder.begin() should return a new instance.").isNotNull();
  }

  @Test
  void shouldReturnDistinctInstancesWhenBeginIsCalledMultipleTimes() {
    // when
    var builder1 = StateMachineBuilder.begin();
    var builder2 = StateMachineBuilder.begin();

    // then
    assertThat(builder1)
        .as("Each call to StateMachineBuilder.begin() should return a new instance.")
        .isNotEqualTo(builder2);
  }

  @Test
  void shouldSetInitialStateOnFrom() {
    // given
    var builder = StateMachineBuilder.begin();

    // when
    var result = builder.from(DRAFT);

    // then
    assertThat(builder)
        .as("The from() method should return the same StateMachineBuilder instance for chaining.")
        .isEqualTo(result);
  }

  @Test
  void shouldDefineTransitionAfterFromOnTo() {
    // given
    var builder = StateMachineBuilder.begin();

    // when
    var result = builder.from(DRAFT).to(PROPOSED);

    // then
    assertThat(builder)
        .as("The to() method should return the same StateMachineBuilder instance for chaining.")
        .isEqualTo(result);
  }

  @Test
  void shouldThrowIllegalStateExceptionOnToWithoutFrom() {
    // when
    var builder = StateMachineBuilder.begin();

    // then
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(() -> builder.to(PROPOSED))
        .withMessage("from(...) must be called first");
  }

  @Test
  void shouldAddGuardToTransitionOnWithGuard() {
    // given
    var builder = StateMachineBuilder.begin();

    // when
    var result = builder.from(DRAFT).to(PROPOSED).withGuard(project -> true);

    // then
    assertThat(builder)
        .as(
            "The withGuard() method should return the same StateMachineBuilder instance for chaining.")
        .isEqualTo(result);
  }

  @Test
  void shouldThrowIllegalStateExceptionOnWithGuardWithoutTo() {
    // when
    var builder = StateMachineBuilder.begin();

    // then
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(() -> builder.withGuard(project -> true))
        .withMessage("withGuard(...) must follow to(...)");
  }

  @Test
  void shouldSetEventFactoryForTransitionOnFires() {
    // given
    var builder = StateMachineBuilder.begin();

    // when
    var result = builder.from(DRAFT).to(PROPOSED).fires(eventFactory);

    // then
    assertThat(builder)
        .as("The fires() method should return the same StateMachineBuilder instance for chaining.")
        .isEqualTo(result);
  }

  @Test
  void shouldThrowIllegalStateExceptionOnFiresWithoutTo() {
    // when
    var builder = StateMachineBuilder.begin();

    // then
    assertThatExceptionOfType(IllegalStateException.class)
        .isThrownBy(() -> builder.fires(eventFactory))
        .withMessage("fires(...) must follow to(...)");
  }

  @Test
  void shouldCreateStateMachineWithTransitionsOnBuild() {
    // when
    var stateMachine = StateMachineBuilder.begin().from(DRAFT).to(PROPOSED).build(eventPublisher);

    // then
    assertThat(stateMachine)
        .as("The build() method should return a StateMachine instance.")
        .isNotNull();
  }
}
