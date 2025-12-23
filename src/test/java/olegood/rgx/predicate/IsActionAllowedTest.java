package olegood.rgx.predicate;

import olegood.rgx.domain.document.Document;
import olegood.rgx.domain.document.DocumentStatus;
import org.junit.jupiter.api.Test;

import static olegood.rgx.domain.document.DocumentAction.APPROVE;
import static olegood.rgx.domain.document.DocumentAction.SUBMIT;
import static olegood.rgx.domain.document.DocumentStatus.DRAFT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IsActionAllowedTest {
    
    @Test
    void shouldReturnTrueIfActionAllowed() {
        // when
        var document = new Document().setStatus(DRAFT);

        // then
        assertThat(new IsActionAllowed(SUBMIT).test(document)).isTrue();
    }

    @Test
    void shouldReturnFalseIfActionIsNotAllowed() {
        // when
        Document document = new Document().setStatus(DRAFT);

        // then
        assertThat(new IsActionAllowed(APPROVE).test(document)).isFalse();
    }

    @Test
    void shouldReturnFalseIfDocumentIsNull() {
        // expect
        assertFalse(new IsActionAllowed(APPROVE).test(null));
    }

    @Test
    void shouldReturnFalseIfDocumentStatusIsNull() {
        // expect
        assertFalse(new IsActionAllowed(SUBMIT).test(new Document().setStatus(null)));
    }
    
}
