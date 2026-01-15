package olegood.rgx.domain.project.type.research.content;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.Data;

@Data
@Embeddable
public class ResearchTimeline {

  @Column(name = "START_DATE", nullable = false)
  private LocalDate startDate;

  @Column(name = "TARGET_END_DATE", nullable = false)
  private LocalDate targetEndDate;

  @Column(name = "ACTUAL_END_DATE")
  private LocalDate actualEndDate;

  public boolean isOverdue() {
    return actualEndDate == null && LocalDate.now().isAfter(targetEndDate);
  }
}
