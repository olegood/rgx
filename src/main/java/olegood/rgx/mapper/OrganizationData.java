package olegood.rgx.mapper;

import java.util.List;
import olegood.rgx.domain.Organization;

public record OrganizationData(
    Long id,
    String code,
    String name,
    String website,
    String country,
    String description,
    int founded,
    String industry,
    int numberOfEmployees,
    Organization.Status status,
    List<EnrollmentData> enrollments) {}
