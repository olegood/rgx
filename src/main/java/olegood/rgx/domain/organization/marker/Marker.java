package olegood.rgx.domain.organization.marker;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "MARKER")
public class Marker {

  @Id
  @Column(name = "ID")
  private Long id;

  @Convert(converter = MarkerTypeConverter.class)
  @Column(name = "TYPE", nullable = false)
  private MarkerType type;

  @Embedded private Age age;

  /**
   * Checks whether the associated {@link Age} instance is active. The {@link Age} instance is
   * considered active based on its start and end dates.
   *
   * @return {@code true} if the associated {@link Age} instance is active, {@code false} otherwise.
   */
  public boolean isActive() {
    return age.isActive();
  }
}
