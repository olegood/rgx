package olegood.rgx.domain.organization.marker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class MarkerTypeConverterTest {

  @Test
  void shouldConvertGoldMarkerTypeToDatabaseColumn() {
    // given
    var goldMarker = MarkerType.GOLD;

    // when
    var result = new MarkerTypeConverter().convertToDatabaseColumn(goldMarker);

    // then
    assertThat(result).isEqualTo("Gold");
  }

  @Test
  void shouldConvertSilverMarkerTypeToDatabaseColumn() {
    // given
    var silverMarker = MarkerType.SILVER;

    // when
    var result = new MarkerTypeConverter().convertToDatabaseColumn(silverMarker);

    // then
    assertThat(result).isEqualTo("Silver");
  }

  @Test
  void shouldConvertBronzeMarkerTypeToDatabaseColumn() {
    // given
    var bronzeMarker = MarkerType.BRONZE;

    // when
    var result = new MarkerTypeConverter().convertToDatabaseColumn(bronzeMarker);

    // then
    assertThat(result).isEqualTo("Bronze");
  }

  @Test
  void shouldConvertHubSpecMarkerTypeToDatabaseColumn() {
    // given
    var hubSpecMarker = MarkerType.HUB_SPEC;

    // when
    var result = new MarkerTypeConverter().convertToDatabaseColumn(hubSpecMarker);

    // then
    assertThat(result).isEqualTo("HSpec");
  }

  @Test
  void shouldConvertTouchSpecMarkerTypeToDatabaseColumn() {
    // given
    var touchSpecMarker = MarkerType.TOUCH_SPEC;

    // when
    var result = new MarkerTypeConverter().convertToDatabaseColumn(touchSpecMarker);

    // then
    assertThat(result).isEqualTo("TSpec");
  }

  @Test
  void shouldConvertSelfDrivenMarkerTypeToDatabaseColumn() {
    // given
    var selfDrivenMarker = MarkerType.SELF_DRIVEN;

    // when
    var result = new MarkerTypeConverter().convertToDatabaseColumn(selfDrivenMarker);

    // then
    assertThat(result).isEqualTo("SDriven");
  }

  @Test
  void shouldConvertGoldDatabaseValueToMarkerType() {
    // given
    var dbValue = "Gold";

    // when
    var result = new MarkerTypeConverter().convertToEntityAttribute(dbValue);

    // then
    assertThat(result).isEqualTo(MarkerType.GOLD);
  }

  @Test
  void shouldConvertSilverDatabaseValueToMarkerType() {
    // given
    var dbValue = "Silver";

    // when
    var result = new MarkerTypeConverter().convertToEntityAttribute(dbValue);

    // then
    assertThat(result).isEqualTo(MarkerType.SILVER);
  }

  @Test
  void shouldConvertBronzeDatabaseValueToMarkerType() {
    // given
    var dbValue = "Bronze";

    // when
    var result = new MarkerTypeConverter().convertToEntityAttribute(dbValue);

    // then
    assertThat(result).isEqualTo(MarkerType.BRONZE);
  }

  @Test
  void shouldConvertHubSpecDatabaseValueToMarkerType() {
    // given
    var dbValue = "HSpec";

    // when
    var result = new MarkerTypeConverter().convertToEntityAttribute(dbValue);

    // then
    assertThat(result).isEqualTo(MarkerType.HUB_SPEC);
  }

  @Test
  void shouldConvertTouchSpecDatabaseValueToMarkerType() {
    // given
    var dbValue = "TSpec";

    // when
    var result = new MarkerTypeConverter().convertToEntityAttribute(dbValue);

    // then
    assertThat(result).isEqualTo(MarkerType.TOUCH_SPEC);
  }

  @Test
  void shouldConvertSelfDrivenDatabaseValueToMarkerType() {
    // given
    var dbValue = "SDriven";

    // when
    var result = new MarkerTypeConverter().convertToEntityAttribute(dbValue);

    // then
    assertThat(result).isEqualTo(MarkerType.SELF_DRIVEN);
  }

  @Test
  void shouldReturnNullWhenDatabaseValueIsNull() {
    // when
    String dbValue = null;

    // then
    assertThatThrownBy(() -> new MarkerTypeConverter().convertToEntityAttribute(dbValue))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Unknown marker type: null");
  }

  @Test
  void shouldThrowExceptionForInvalidDatabaseValue() {
    // when
    String dbValue = "InvalidValue";

    // then
    assertThatThrownBy(() -> new MarkerTypeConverter().convertToEntityAttribute(dbValue))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Unknown marker type: InvalidValue");
  }
}
