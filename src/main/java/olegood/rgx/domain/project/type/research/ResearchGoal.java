package olegood.rgx.domain.project.type.research;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "RESEARCH_GOAL")
public class ResearchGoal {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "RESEARCH_ID", nullable = false)
  private Research research;

  @Column(name = "DESCRIPTION", nullable = false, length = 2000)
  private String description;

  @Column(name = "SUCCESS_CRITERIA", length = 2000)
  private String successCriteria;

  @Column(name = "ACHIEVED", nullable = false)
  private boolean achieved;

  @Column(name = "OUTCOME_NOTES", length = 2000)
  private String outcomeNotes;
}
