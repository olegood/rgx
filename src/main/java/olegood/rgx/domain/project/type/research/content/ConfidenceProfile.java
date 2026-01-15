package olegood.rgx.domain.project.type.research.content;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.Data;

@Data
@Embeddable
public class ConfidenceProfile {

  @Column(name = "BASE_CONFIDENCE", precision = 5, scale = 2)
  private BigDecimal baseConfidence;

  @Column(name = "METHOD_MATURITY_FACTOR", precision = 3, scale = 2)
  private BigDecimal methodMaturityFactor;

  @Column(name = "TEAM_EXPERTISE_FACTOR", precision = 3, scale = 2)
  private BigDecimal teamExpertiseFactor;

  @Column(name = "DATA_AVAILABILITY_FACTOR", precision = 3, scale = 2)
  private BigDecimal dataAvailabilityFactor;

  public BigDecimal calculateOverallConfidence() {
    if (baseConfidence == null) {
      return BigDecimal.ZERO;
    }

    BigDecimal multiplier =
        Stream.of(methodMaturityFactor, teamExpertiseFactor, dataAvailabilityFactor)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ONE, BigDecimal::multiply);

    return baseConfidence.multiply(multiplier).min(BigDecimal.valueOf(1.0)).max(BigDecimal.ZERO);
  }
}
