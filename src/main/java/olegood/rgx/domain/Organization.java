package olegood.rgx.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Entity
@Table(name = Organization.TABLE_NAME)
public class Organization {

  public static final String TABLE_NAME = "ORGANIZATION";
  public static final String SEQUENCE_NAME = "organization_seq";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
  @SequenceGenerator(name = SEQUENCE_NAME, initialValue = 100, allocationSize = 1)
  @Column(name = "ID", unique = true)
  private Long id;

  @Column(name = "CODE", length = 15, unique = true)
  private String code;

  @Column(name = "NAME", length = 64, nullable = false)
  private String name;

  @Column(name = "WEBSITE", length = 128)
  private String website;

  @Column(name = "COUNTRY", length = 64, nullable = false)
  private String country;

  @Column(name = "DESCRIPTION", length = 300)
  private String description;

  @Column(name = "FOUNDED")
  private int founded;

  @Column(name = "INDUSTRY", length = 100, nullable = false)
  private String industry;

  @Column(name = "NUMBER_OF_EMPLOYEES")
  private int numberOfEmployees;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS", nullable = false)
  private OrganizationStatus status = OrganizationStatus.ACTIVE;
}
