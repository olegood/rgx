package olegood.rgx.mapper;

import olegood.rgx.domain.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * A mapper interface for converting between {@link Organization} entities and {@link
 * OrganizationData} records. This interface provides methods to map entities to records and vice
 * versa, enabling a convenient transformation layer for data transfer objects.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrganizationMapper {

  /**
   * Maps an {@link Organization} entity to an {@link OrganizationData} record.
   *
   * @param organization the {@link Organization} entity to be transformed
   * @return an {@link OrganizationData} record representing the provided entity
   */
  OrganizationData toResponse(Organization organization);

  /**
   * Converts an {@link OrganizationData} record into an {@link Organization} entity.
   *
   * @param organizationData the {@link OrganizationData} record to be transformed
   * @return an {@link Organization} entity representing the provided record
   */
  Organization toEntity(OrganizationData organizationData);
}
