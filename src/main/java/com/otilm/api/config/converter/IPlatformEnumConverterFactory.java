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

        private final Class<T> enumType;

        private StringToIPlatformEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if (source == null || source.isEmpty()) {
                return null;
            }
            for (T constant : enumType.getEnumConstants()) {
                if (constant.getCode().equals(source)) {
                    return constant;
                }
            }
            throw new IllegalArgumentException(
                    "No " + enumType.getSimpleName() + " constant with code \"" + source + "\"");
        }
    }
}
