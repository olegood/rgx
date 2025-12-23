package olegood.rgx.domain.project;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import olegood.rgx.domain.Organization;

@Data
@Entity
@Table(name = "PROJECT")
public class Project {

  @Id private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ORGANIZATION_ID")
  private Organization organization;

  @Column(name = "NAME")
  private String name;

  @Column(name = "COMPLIANCE_LEVEL")
  private ComplianceLevel complianceLevel;
}
