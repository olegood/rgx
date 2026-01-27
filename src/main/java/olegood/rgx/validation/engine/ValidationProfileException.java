package olegood.rgx.validation.engine;

import java.util.Collection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class ValidationProfileException extends RuntimeException {

  private final Collection<String> messages;
}
