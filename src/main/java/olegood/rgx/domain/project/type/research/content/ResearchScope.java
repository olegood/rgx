package olegood.rgx.domain.project.type.research.content;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ResearchScope {

  @Column(name = "DOMAIN", nullable = false)
  private String domain;

  @Column(name = "SUB_DOMAIN")
  private String subDomain;

  @Column(name = "CONSTRAINTS", length = 2000)
  private String constraints;

  @Column(name = "KEYWORDS")
  private String keywords;
}
