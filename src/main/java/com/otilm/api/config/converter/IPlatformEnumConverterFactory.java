package com.otilm.api.config.converter;

import com.otilm.api.model.common.enums.IPlatformEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * Converts a {@code @PathVariable} or {@code @RequestParam} string to any {@link IPlatformEnum}
 * constant by its {@link IPlatformEnum#getCode()} wire code, not its Java constant name.
 *
 * <p>Spring's default enum binding calls {@code Enum.valueOf(String)}, which matches only the
 * Java constant name (e.g. {@code CERTIFICATE}). Every platform enum in this library — including
 * {@code Resource}, used by {@code DiscoveryMetadataController#listResourceAttributes}'s
 * {@code GET /v2/discoveryProvider/{resource}/attributes} — serializes and is addressed on the
 * wire by its {@link IPlatformEnum#getCode()} instead (e.g. {@code "certificates"}), so a
 * connector that does not register a converter for that path variable gets a 400 on every call
 * with a real resource code. Registering this single factory (for example, via
 * {@code WebMvcConfigurer#addFormatters}: {@code registry.addConverterFactory(new
 * IPlatformEnumConverterFactory())}) covers every current and future {@link IPlatformEnum}
 * implementation at once, since none of them need their own converter written by hand.
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
         * Walks up to the declaring enum class. A constant declared with a body compiles to an
         * anonymous subclass of its enum, and {@code Class#getEnumConstants()} returns null for
         * that subclass — so binding a value that happens to resolve to such a constant would read
         * the constants of the wrong class. Spring's own {@code StringToEnumConverterFactory}
         * normalizes the same way before reading them.
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
