package olegood.rgx.api.organization;

import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import olegood.rgx.domain.Organization;
import olegood.rgx.domain.OrganizationRepository;
import olegood.rgx.mapper.APage;
import olegood.rgx.mapper.OrganizationData;
import olegood.rgx.mapper.OrganizationMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
    return mapper.toResponse(findOrThrow(organizationId));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OrganizationData save(@Valid @RequestBody OrganizationData body) {
    return Optional.of(body)
        .map(mapper::toEntity)
        .map(repository::save)
        .map(mapper::toResponse)
        .orElseThrow();
  }

  @PutMapping("/{organizationId}")
  public OrganizationData update(
      @PathVariable Long organizationId, @Valid @RequestBody OrganizationData body) {
    findOrThrow(organizationId);

    var updated = mapper.toEntity(body).setId(organizationId);
    return mapper.toResponse(repository.save(updated));
  }

  @DeleteMapping("/{organizationId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long organizationId) {
    repository.delete(findOrThrow(organizationId));
  }

  private Organization findOrThrow(Long organizationId) {
    return repository
        .findById(organizationId)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Organization not found"));
  }
}
