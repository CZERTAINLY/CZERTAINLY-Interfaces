package com.otilm.api.model.common.enums;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PlatformEnumJsonTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @ParameterizedTest(name = "{0}.{1}")
    @MethodSource("platformEnumConstants")
    void jsonRoundTrip_preservesEnumConstant(
            Class<? extends IPlatformEnum> enumType,
            IPlatformEnum enumConstant)
            throws JsonProcessingException {
        // given

        // when
        var serializedConstant = mapper.writeValueAsString(enumConstant);
        var deserializedConstant = mapper.readValue(serializedConstant, enumType);

        // then
        assertEquals(enumConstant, deserializedConstant);
    }

    private static Stream<Arguments> platformEnumConstants() {
        return Stream.of(PlatformEnum.values())
                .flatMap(platformEnum -> {
                    var enumType = platformEnum.getEnumClass();
                    return Arrays.stream(enumType.getEnumConstants())
                            .map(enumConstant -> arguments(
                                    named(enumType.getSimpleName(), enumType),
                                    named(enumConstant.name(), enumConstant)));
                });
    }
}
