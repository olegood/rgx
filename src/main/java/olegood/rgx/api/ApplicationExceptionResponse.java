package olegood.rgx.api;

import java.util.Collection;
import java.util.UUID;
import lombok.Builder;
import lombok.Singular;

@Builder
public record ApplicationExceptionResponse(
    UUID correlatedId, String message, @Singular Collection<String> errors) {}
