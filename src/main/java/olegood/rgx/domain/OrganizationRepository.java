package olegood.rgx.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "organizations", collectionResourceRel = "organizations")
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

  Organization findByCode(String code);
}
