package olegood.rgx.api;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.OrganizationRepository;
import olegood.rgx.mapper.APage;
import olegood.rgx.mapper.OrganizationData;
import olegood.rgx.mapper.OrganizationMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

  private final OrganizationRepository repository;
  private final OrganizationMapper mapper;

  @GetMapping
  public APage<OrganizationData> getAll(@PageableDefault(sort = "code") Pageable pageable) {
    return APage.from(repository.findAll(pageable), mapper::toResponse);
  }

  @GetMapping("/{organizationId}")
  public OrganizationData getById(@PathVariable Long organizationId) {
    return repository.findById(organizationId).map(mapper::toResponse).orElseThrow();
  }

  @PostMapping
  public OrganizationData save(@RequestBody OrganizationData body) {
    return Optional.of(body)
        .map(mapper::toEntity)
        .map(repository::save)
        .map(mapper::toResponse)
        .orElseThrow();
  }
}
