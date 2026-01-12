package olegood.rgx.domain.organization.marker;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AgeTest {

  @Test
  void shouldReturnFalseWhenStartDateIsNull() {
    // when
    var age = new Age().setEndDate(null);

    // then
    assertThat(age.isActive()).isFalse();
  }

  @Test
  void shouldReturnFalseWhenTodayIsBeforeStartDate() {
    // when
    var age = new Age().setStartDate(now().plusDays(1)).setEndDate(null);

    // then
    assertThat(age.isActive()).isFalse();
  }

  @Test
  void shouldReturnFalseWhenTodayIsAfterEndDate() {
    // when
    var age = new Age().setStartDate(now().minusDays(10)).setEndDate(now().minusDays(1));

    // then
    assertThat(age.isActive()).isFalse();
  }

  @Test
  void shouldReturnTrueWhenTodayIsWithinStartDateAndEndDate() {
    // when
    var age = new Age().setStartDate(now().minusDays(5)).setEndDate(now().plusDays(5));

    // then
    assertThat(age.isActive()).isTrue();
  }

  @Test
  void shouldReturnTrueWhenTodayMatchesStartDateAndNoEndDateIsSet() {
    // when
    var age = new Age().setStartDate(now()).setEndDate(null);

    // then
    assertThat(age.isActive()).isTrue();
  }

  @Test
  void shouldReturnTrueWhenTodayMatchesEndDate() {
    // when
    var age = new Age().setStartDate(now().minusDays(10)).setEndDate(now());

    // then
    assertThat(age.isActive()).isTrue();
  }
}
