package olegood.rgx.domain.project.type.research.content;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.Instant;
import lombok.Data;

@Data
@Embeddable
public class ComplianceProfile {

  @Column(name = "REGULATORY_FRAMEWORKS")
  private String regulatoryFrameworks;

  @Column(name = "INTERNAL_COMPLIANCE_OK", nullable = false)
  private boolean internallyCompliant;

  @Column(name = "LAST_COMPLIANCE_REVIEW_AT")
  private Instant lastReviewAt;
}
