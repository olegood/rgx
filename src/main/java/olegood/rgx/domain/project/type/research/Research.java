package olegood.rgx.domain.project.type.research;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;
import olegood.rgx.domain.project.Project;
import olegood.rgx.domain.project.type.research.content.ComplianceProfile;
import olegood.rgx.domain.project.type.research.content.ConfidenceProfile;
import olegood.rgx.domain.project.type.research.content.ResearchScope;
import olegood.rgx.domain.project.type.research.content.ResearchTimeline;
import olegood.rgx.domain.project.type.research.content.ethics.EthicalProfile;
import olegood.rgx.domain.project.type.research.content.method.MethodologyProfile;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "RESEARCH")
public class Research extends Project {

  @Column(name = "TITLE", nullable = false)
  private String title;

  @Column(name = "ABSTRACT", length = 4000)
  private String abstractText;

  @Embedded private ResearchScope scope;

  @Embedded private EthicalProfile ethicalProfile;

  @Embedded private ComplianceProfile complianceProfile;

  @Embedded private MethodologyProfile methodology;

  @OneToMany(mappedBy = "research", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ResearchGoal> goals = new HashSet<>();

  @Embedded private ConfidenceProfile confidenceProfile;

  @Embedded private ResearchTimeline timeline;
}
