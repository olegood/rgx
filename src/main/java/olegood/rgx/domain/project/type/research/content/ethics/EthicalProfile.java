package olegood.rgx.domain.project.type.research.content.ethics;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class EthicalProfile {

  @Column(name = "ETHICS_REVIEW_REQUIRED", nullable = false)
  private boolean ethicsReviewRequired;

  @Enumerated(EnumType.STRING)
  @Column(name = "ETHICS_STATUS")
  private EthicsApprovalStatus approvalStatus;

  @Column(name = "ETHICS_AUTHORITY")
  private String approvingAuthority;

  @Enumerated(EnumType.STRING)
  @Column(name = "ETHICAL_RISK_LEVEL")
  private EthicalRiskLevel riskLevel;
}
