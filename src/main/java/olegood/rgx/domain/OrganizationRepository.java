package olegood.rgx.domain;

import org.springframework.data.jpa.repository.JpaRepository;

// todo: paging and sorting repo
public interface OrganizationRepository extends JpaRepository<Organization, Long> {}
