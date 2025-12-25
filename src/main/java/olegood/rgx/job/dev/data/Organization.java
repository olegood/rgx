package olegood.rgx.job.dev.data;

import lombok.Data;
import olegood.rgx.domain.OrganizationStatus;

@Data
public class Organization {

  private Long index;
  private String code;
  private String name;
  private String website;
  private String country;
  private String description;
  private int founded;
  private String industry;
  private int numberOfEmployees;
  private OrganizationStatus status;
}
