package olegood.rgx.validation.constraint;

import lombok.Builder;

@Builder
public record Violation(String message) {
}
