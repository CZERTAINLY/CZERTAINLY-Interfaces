package com.otilm.api.config.converter;

import com.otilm.api.model.common.enums.IPlatformEnum;
import com.otilm.api.model.core.auth.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Proves {@link IPlatformEnumConverterFactory} does what
 * {@code DiscoveryMetadataController#listResourceAttributes}'s {@code @PathVariable Resource
 * resource} needs: Spring's default enum binding calls {@code Enum.valueOf}, which only matches
 * the Java constant name (e.g. {@code CERTIFICATE}), never the wire code (e.g.
 * {@code "certificates"}) that actually appears in the path. Every platform enum implements
 * {@link IPlatformEnum#getCode()}, so one factory converts by code for all of them; {@link Resource}
 * is used here only as a concrete, representative example.
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

    /**
     * An enum constant declared with a body is not an instance of its own enum class but of an
     * anonymous subclass, whose {@code getEnumConstants()} is null. No {@link IPlatformEnum} in this
     * library declares a constant body today, but the factory promises to cover every future one, so
     * the target type has to be normalized to the declaring enum before its constants are read.
     */
    @Test
    void convertsWhenTheTargetTypeIsAConstantBodySubclass() {
        Class<? extends EnumWithConstantBody> bodySubclass = EnumWithConstantBody.WITH_BODY.getClass();
        assertNotEquals(EnumWithConstantBody.class, bodySubclass,
                "a constant with a body must compile to an anonymous subclass for this test to mean anything");
        assertNull(bodySubclass.getEnumConstants(),
                "the anonymous subclass has no constants of its own - that is the trap being tested");

        Converter<String, ? extends EnumWithConstantBody> converter = factory.getConverter(bodySubclass);

        assertEquals(EnumWithConstantBody.WITH_BODY, converter.convert("withBody"));
        assertEquals(EnumWithConstantBody.PLAIN, converter.convert("plain"));
    }

    @Test
    void constantBodySubclassStillReportsAnUnknownCodeAsAConversionError() {
        Converter<String, ? extends EnumWithConstantBody> converter =
                factory.getConverter(EnumWithConstantBody.WITH_BODY.getClass());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> converter.convert("not-a-real-code"));
        assertTrue(ex.getMessage().contains("EnumWithConstantBody"),
                "the message must name the declaring enum, not the anonymous subclass: " + ex.getMessage());
    }

    @Test
    void aNonEnumTargetTypeIsRejectedRatherThanFailingLater() {
        assertThrows(IllegalArgumentException.class, () -> factory.getConverter(NotAnEnum.class));
    }

    private enum EnumWithConstantBody implements IPlatformEnum {

        PLAIN("plain"),

        WITH_BODY("withBody") {
            @Override
            public String getLabel() {
                return "overridden";
            }
        };

        private final String code;

        EnumWithConstantBody(String code) {
            this.code = code;
        }

        @Override
        public String getCode() {
            return this.code;
        }

        @Override
        public String getLabel() {
            return this.code;
        }

        @Override
        public String getDescription() {
            return null;
        }
    }

    /**
     * {@link IPlatformEnum} is an interface, so nothing stops a non-enum from implementing it. The
     * factory has to say so rather than dereference a null constant array.
     */
    private static final class NotAnEnum implements IPlatformEnum {

        @Override
        public String name() {
            return "NOT_AN_ENUM";
        }

        @Override
        public String getCode() {
            return "notAnEnum";
        }

        @Override
        public String getLabel() {
            return "Not an enum";
        }

        @Override
        public String getDescription() {
            return null;
        }
    }
}
