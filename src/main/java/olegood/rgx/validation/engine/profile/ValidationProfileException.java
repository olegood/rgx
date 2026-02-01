package olegood.rgx.validation.engine.profile;

import java.util.Collection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class ValidationProfileException extends RuntimeException {

  private final Collection<String> violations;
}
