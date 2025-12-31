package olegood.rgx.mapper;

import olegood.rgx.domain.OrganizationStatus;

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
    OrganizationStatus status) {}
