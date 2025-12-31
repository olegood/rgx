package olegood.rgx.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.util.List;
import olegood.rgx.domain.Organization;
import olegood.rgx.domain.OrganizationRepository;
import olegood.rgx.mapper.OrganizationMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class OrganizationControllerTest {

  @Mock private Page<Organization> page;

  @Mock private OrganizationRepository repository;

  @Mock private OrganizationMapper mapper;

  @InjectMocks private OrganizationController organizationController;

  @Test
  void getAll() {
    // given
    var organizationOne = new Organization().setId(42L);
    var organizationTwo = new Organization().setId(54L);

    var pageable = PageRequest.of(0, 10);

    when(repository.findAll(pageable)).thenReturn(page);
    when(page.getContent()).thenReturn(List.of(organizationOne, organizationTwo));
    when(page.getPageable()).thenReturn(pageable);

    // when
    var result = organizationController.getAll(pageable);

    // then
    assertThat(result.content()).hasSize(2);

    var inOrder = inOrder(repository, mapper);
    inOrder.verify(repository).findAll(pageable);
    inOrder.verify(mapper).toResponse(organizationOne);
    inOrder.verify(mapper).toResponse(organizationTwo);
  }
}
