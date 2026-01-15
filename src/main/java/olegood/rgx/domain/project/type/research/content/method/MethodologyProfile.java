package olegood.rgx.domain.project.type.research.content.method;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class MethodologyProfile {

  @Enumerated(EnumType.STRING)
  @Column(name = "METHODOLOGY_TYPE", nullable = false)
  private MethodologyType methodologyType;

  @Column(name = "PRIMARY_METHODS", length = 2000)
  private String primaryMethods;

  @Column(name = "TOOLS", length = 2000)
  private String tools;
}
