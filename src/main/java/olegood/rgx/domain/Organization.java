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

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "WEBSITE")
    private String website;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "FOUNDED")
    private int founded;

    @Column(name = "INDUSTRY")
    private String industry;

    @Column(name = "NUMBER_OF_EMPLOYEES")
    private int numberOfEmployees;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private OrganizationStatus status;

}
