package olegood.rgx.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ORGANIZATION")
public class Organization {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrganizationStatus status;

}
