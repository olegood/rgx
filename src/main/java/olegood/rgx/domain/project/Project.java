package olegood.rgx.domain.project;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import olegood.rgx.domain.Organization;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners(ProjectListener.class)
public abstract class Project {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ORGANIZATION_ID")
  private Organization organization;

  @Column(name = "CODE", nullable = false, unique = true)
  private String code;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS", nullable = false)
  private ProjectStatus status;

  @Column(name = "CREATED_AT", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "MODIFIED_AT", nullable = false)
  private Instant modifiedAt;
}
