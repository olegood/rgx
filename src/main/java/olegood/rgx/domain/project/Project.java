package olegood.rgx.domain.project;

import jakarta.persistence.*;
import lombok.Data;
import olegood.rgx.domain.Organization;

@Data
@Entity
@Table(name = "PROJECT")
public class Project {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    private Organization organization;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COMPLIANCE_LEVEL")
    private ComplianceLevel complianceLevel;

}
