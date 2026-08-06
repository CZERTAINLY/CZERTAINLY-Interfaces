package com.otilm.api.config.converter;

import com.otilm.api.model.core.auth.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Proves {@link IPlatformEnumConverterFactory} does what
 * {@code DiscoveryMetadataController#listResourceAttributes}'s {@code @PathVariable Resource
 * resource} needs: Spring's default enum binding calls {@code Enum.valueOf}, which only matches
 * the Java constant name (e.g. {@code CERTIFICATE}), never the wire code (e.g.
 * {@code "certificates"}) that actually appears in the path. Every platform enum implements
 * {@link com.otilm.api.model.common.enums.IPlatformEnum#getCode()}, so one factory converts by
 * code for all of them; {@link Resource} is used here only as a concrete, representative example.
 */
class IPlatformEnumConverterFactoryTest {

    private final IPlatformEnumConverterFactory factory = new IPlatformEnumConverterFactory();

    @Test
    void convertsAKnownWireCodeToItsEnumConstant() {
        Converter<String, Resource> converter = factory.getConverter(Resource.class);

        assertEquals(Resource.CERTIFICATE, converter.convert("certificates"));
        assertEquals(Resource.CRYPTOGRAPHIC_KEY, converter.convert("keys"));
    }

    @Test
    void unknownWireCodeThrowsIllegalArgumentException() {
        Converter<String, Resource> converter = factory.getConverter(Resource.class);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> converter.convert("not-a-real-resource-code"));
        assertTrue(ex.getMessage().contains("not-a-real-resource-code"));
    }
}
