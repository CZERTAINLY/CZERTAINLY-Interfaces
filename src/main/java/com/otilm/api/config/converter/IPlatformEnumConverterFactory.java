package com.otilm.api.config.converter;

import com.otilm.api.model.common.enums.IPlatformEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * Converts a {@code @PathVariable} or {@code @RequestParam} string to any {@link IPlatformEnum} constant by its
 * {@link IPlatformEnum#getCode()} wire code, not its Java constant name.
 *
 * <p>
 * Spring's default enum binding calls {@code Enum.valueOf(String)}, which matches only the Java constant name —
 * {@code CERTIFICATE}, say. Every platform enum in this library serializes and is addressed on the wire by its code
 * instead, so {@code CERTIFICATE} appears as {@code "certificates"}.
 *
 * <p>
 * The two never meet, so a connector that registers no converter gets a 400 on every call that uses a real wire code.
 * {@code Resource} on {@code GET /v2/discoveryProvider/{resource}/attributes}, bound by
 * {@code DiscoveryMetadataController#listResourceAttributes}, is the case this library ships today.
 *
 * <p>
 * Registering this one factory covers every current and future {@link IPlatformEnum} implementation, so none of them
 * needs a converter written by hand. From a {@code WebMvcConfigurer}:
 *
 * <pre>{@code
 * @Override
 * public void addFormatters(FormatterRegistry registry) {
 *     registry.addConverterFactory(new IPlatformEnumConverterFactory());
 * }
 * }</pre>
 */
public class IPlatformEnumConverterFactory implements ConverterFactory<String, IPlatformEnum> {

    @Override
    public <T extends IPlatformEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToIPlatformEnumConverter<>(targetType);
    }

    private static final class StringToIPlatformEnumConverter<T extends IPlatformEnum> implements Converter<String, T> {

        private final Class<?> enumType;

        private StringToIPlatformEnumConverter(Class<T> targetType) {
            this.enumType = enumType(targetType);
        }

        @Override
        @SuppressWarnings("unchecked")
        public T convert(String source) {
            if (source == null || source.isEmpty()) {
                return null;
            }
            for (Object constant : enumType.getEnumConstants()) {
                IPlatformEnum candidate = (IPlatformEnum) constant;
                if (candidate.getCode().equals(source)) {
                    return (T) candidate;
                }
            }
            throw new IllegalArgumentException(
                    "No " + enumType.getSimpleName() + " constant with code \"" + source + "\"");
        }

        /**
         * Walks up to the declaring enum class. A constant declared with a body compiles to an anonymous subclass of
         * its enum, and {@code Class#getEnumConstants()} returns null for that subclass — so binding a value that
         * happens to resolve to such a constant would read the constants of the wrong class. Spring's own
         * {@code StringToEnumConverterFactory} normalizes the same way before reading them.
         */
        private static Class<?> enumType(Class<?> targetType) {
            Class<?> declaring = targetType;
            while (declaring != null && !declaring.isEnum()) {
                declaring = declaring.getSuperclass();
            }
            if (declaring == null) {
                throw new IllegalArgumentException(
                        "The target type " + targetType.getName() + " does not refer to an enum");
            }
            return declaring;
        }
    }
}
