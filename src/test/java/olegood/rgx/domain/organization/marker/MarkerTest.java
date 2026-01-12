package olegood.rgx.domain.organization.marker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MarkerTest {

  @Mock private Age age;

  private Marker marker;

  @BeforeEach
  void setUp() {
    marker = new Marker().setAge(age);
  }

  @Test
  void shouldReturnTrueWhenAgeIsActive() {
    // when
    when(age.isActive()).thenReturn(true);

    // then
    assertThat(marker.isActive()).isTrue();
  }

  @Test
  void shouldReturnFalseWhenAgeIsNotActive() {
    // when
    when(age.isActive()).thenReturn(false);

    // then
    assertThat(marker.isActive()).isFalse();
  }
}
