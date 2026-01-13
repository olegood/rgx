package olegood.rgx.domain.organization.marker;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Embeddable
public final class Age {

  @Column(name = "START_DATE", nullable = false)
  private LocalDate startDate;

  @Column(name = "END_DATE")
  private LocalDate endDate;

  /**
   * Determines whether the current instance is active based on the configured start and end dates.
   * The instance is considered active if the current date is not earlier than the start date and is
   * not later than the end date (if an end date is specified). If the start date is not set, the
   * instance is considered inactive.
   *
   * @return {@code true} if the instance is active, {@code false} otherwise.
   */
  public boolean isActive() {
    if (startDate == null) {
      return false;
    }
    var today = LocalDate.now();
    return !today.isBefore(startDate) && (endDate == null || !today.isAfter(endDate));
  }
}
