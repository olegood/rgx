package olegood.rgx.api;

import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@Builder
public class ApplicationException extends RuntimeException {

  private final String message;

  @Singular private final Collection<String> errors;

  public ApplicationException(String message, Collection<String> errors) {
    super(message);
    this.message = message;
    this.errors = errors;
  }
}
