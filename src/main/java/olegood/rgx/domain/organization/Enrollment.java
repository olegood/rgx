package olegood.rgx.domain.organization;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import olegood.rgx.domain.organization.marker.Marker;

@Data
@Entity
@Table(name = "ENROLLMENT")
public class Enrollment {

  @Id
  @Column(name = "ID")
  private Long id;

  public enum Type {
    RESELL,
    EXPLORE,
    CONSULT,
    DEVELOP,
    MAINTAIN
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "TYPE", nullable = false)
  private Type type;

  public enum Status {
    ONBOARDING,
    ACTIVE,
    IN_TERMINATION,
    RETIRED
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS", nullable = false)
  private Status status;

  @OneToMany
  @JoinColumn(name = "ENROLLMENT_ID")
  private List<Marker> markers;
}
