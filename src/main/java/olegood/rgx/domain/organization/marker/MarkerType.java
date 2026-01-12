package olegood.rgx.domain.organization.marker;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum MarkerType {
  GOLD("Gold"),
  SILVER("Silver"),
  BRONZE("Bronze"),

  HUB_SPEC("HSpec"),
  TOUCH_SPEC("TSpec"),
  SELF_DRIVEN("SDriven");

  private final String literal;

  MarkerType(String literal) {
    this.literal = literal;
  }

  public static MarkerType fromLiteral(String literal) {
    return Arrays.stream(values())
        .filter(type -> type.getLiteral().equals(literal))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown marker type: " + literal));
  }
}
