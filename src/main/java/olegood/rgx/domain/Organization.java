package olegood.rgx.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import olegood.rgx.domain.organization.Enrollment;

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

  @NotBlank
  @Size(max = 15)
  @Column(name = "CODE", length = 15, unique = true)
  private String code;

  @NotBlank
  @Size(max = 64)
  @Column(name = "NAME", length = 64, nullable = false)
  private String name;

  @Size(max = 128)
  @Pattern(regexp = "^(https?://).*$", message = "Website must start with `http://` or `https://`")
  @Column(name = "WEBSITE", length = 128)
  private String website;

  @NotBlank
  @Size(max = 64)
  @Column(name = "COUNTRY", length = 64, nullable = false)
  private String country;

  @Size(max = 300)
  @Column(name = "DESCRIPTION", length = 300)
  private String description;

  @Min(1900)
  @Max(2100)
  @Column(name = "FOUNDED")
  private int founded;

  @Size(max = 100)
  @Column(name = "INDUSTRY", length = 100, nullable = false)
  private String industry;

  @Min(1)
  @Column(name = "NUMBER_OF_EMPLOYEES")
  private int numberOfEmployees;

  public enum Status {

    /**
     * The organization is known but not yet formally engaged. Data may be incomplete, and no
     * contractual or operational relationship exists yet.
     */
    PROSPECTIVE,

    /**
     * Used when onboarding, compliance checks, or audits are in progress. This is useful if
     * activation requires explicit approval steps.
     */
    UNDER_REVIEW,

    /**
     * The organization is fully operational and engaged. It can enter contracts, participate in
     * projects, and interact with all relevant domain processes.
     */
    ACTIVE,

    /**
     * The organization exists but is temporarily restricted. This might be due to compliance
     * issues, payment problems, regulatory holds, or internal review. Reactivation is expected.
     */
    SUSPENDED,

    /**
     * The organization is no longer operating within the system, but may still exist legally. No
     * new engagements are allowed, though historical data remains relevant.
     */
    INACTIVE,

    /**
     * The relationship has been formally ended. This status usually implies contractual closure and
     * prevents any future activity. Often irreversible.
     */
    TERMINATED,

    /**
     * A purely technical or administrative status indicating the organization is retained only for
     * historical or reporting purposes.
     */
    ARCHIVED
  }

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS", nullable = false)
  private Status status;

  @OneToMany
  @JoinColumn(name = "ORGANIZATION_ID")
  private List<Enrollment> enrollments;
}
