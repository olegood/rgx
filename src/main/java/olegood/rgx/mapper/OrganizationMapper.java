package olegood.rgx.mapper;

import olegood.rgx.domain.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrganizationMapper {

  OrganizationData toResponse(Organization organization);

  Organization toEntity(OrganizationData organizationData);
}
