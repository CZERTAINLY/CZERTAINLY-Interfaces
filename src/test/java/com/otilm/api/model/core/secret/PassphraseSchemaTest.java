package com.otilm.api.model.core.secret;

import com.otilm.api.model.client.cryptography.key.KeyExportRequestDto;
import io.swagger.v3.oas.models.media.Schema;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static com.otilm.api.testsupport.OpenApiSchemaTestSupport.openApi31Schemas;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * A passphrase is held as a wrapper in Java so it never lives in a string, but the published document has to show it as
 * a plain string: the frontend generates its client from that document, and an object here would generate a type no
 * caller can fill in.
 */
class PassphraseSchemaTest {

    @Test
    void publishesAsAWriteOnlyString() {
        // given
        Map<String, Schema> schemas = openApi31Schemas(KeyExportRequestDto.class);

        // when
        Schema<?> request = schemas.get(KeyExportRequestDto.class.getSimpleName());
        Schema<?> passphrase = (Schema<?>) request.getProperties().get("passphrase");

        // then
        assertNotNull(passphrase, "passphrase must be published");
        assertEquals("string",
                passphrase.getTypes() == null ? passphrase.getType() : passphrase.getTypes().iterator().next());
        assertNull(passphrase.getProperties(), "a passphrase must not be published as an object");
        assertEquals(Boolean.TRUE, passphrase.getWriteOnly(),
                "a passphrase must be published write-only, so no generated client reads one back");
    }

    @Test
    void doesNotPublishTheWrapperAsASchemaOfItsOwn() {
        // given
        Map<String, Schema> schemas = openApi31Schemas(KeyExportRequestDto.class);

        // when
        boolean hasCharactersProperty = schemas
                .values()
                .stream()
                .filter(schema -> schema.getProperties() != null)
                .anyMatch(schema -> schema.getProperties().containsKey("characters"));

        // then
        assertFalse(hasCharactersProperty, "the passphrase characters must not reach the published document");
    }
}
