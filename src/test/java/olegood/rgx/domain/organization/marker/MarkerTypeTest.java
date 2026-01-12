package olegood.rgx.domain.organization.marker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the MarkerType enum class.
 *
 * <p>These tests focus specifically on the fromLiteral method to ensure it behaves as expected when
 * valid and invalid input values are provided.
 */
class MarkerTypeTest {

  @Test
  void fromLiteralShouldReturnGoldWhenLiteralIsGold() {
    // when
    var result = MarkerType.fromLiteral("Gold");

    // then
    assertThat(result).isEqualTo(MarkerType.GOLD);
  }

  @Test
  void fromLiteralShouldReturnSilverWhenLiteralIsSilver() {
    // when
    var result = MarkerType.fromLiteral("Silver");

    // then
    assertThat(result).isEqualTo(MarkerType.SILVER);
  }

  @Test
  void fromLiteralShouldReturnBronzeWhenLiteralIsBronze() {
    // when
    var result = MarkerType.fromLiteral("Bronze");

    // then
    assertThat(result).isEqualTo(MarkerType.BRONZE);
  }

  @Test
  void fromLiteralShouldReturnHubSpecWhenLiteralIsHSpec() {
    // when
    var result = MarkerType.fromLiteral("HSpec");

    // then
    assertThat(result).isEqualTo(MarkerType.HUB_SPEC);
  }

  @Test
  void fromLiteralShouldReturnTouchSpecWhenLiteralIsTSpec() {
    // when
    var result = MarkerType.fromLiteral("TSpec");

    // then
    assertThat(result).isEqualTo(MarkerType.TOUCH_SPEC);
  }

  @Test
  void fromLiteralShouldReturnSelfDrivenWhenLiteralIsSDriven() {
    // when
    var result = MarkerType.fromLiteral("SDriven");

    // then
    assertThat(result).isEqualTo(MarkerType.SELF_DRIVEN);
  }

  @Test
  void fromLiteralShouldThrowExceptionWhenLiteralIsUnknown() {
    // expect
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> MarkerType.fromLiteral("UnknownLiteral"))
        .withMessage("Unknown marker type: UnknownLiteral");
  }

  @Test
  void fromLiteralShouldThrowExceptionWhenLiteralIsNull() {
    // expect
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> MarkerType.fromLiteral(null))
        .withMessage("Unknown marker type: null");
  }
}
