package olegood.rgx.domain.document;

import org.junit.jupiter.api.Test;

import static olegood.rgx.domain.document.DocumentStatus.DRAFT;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DocumentTest {

    @Test
    void initialStatusIsDraft() {
        // expect
        assertEquals(DRAFT, new Document().getStatus());
    }

}
