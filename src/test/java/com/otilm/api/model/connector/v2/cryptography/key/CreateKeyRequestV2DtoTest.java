package com.otilm.api.model.connector.v2.cryptography.key;

import com.otilm.api.model.connector.common.v2.OperationExecutionMode;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyRequestV2Dto;
import com.otilm.api.model.core.cryptography.key.KeyUsage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.assertViolation;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CreateKeyRequestV2DtoTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest(name = "{0}")
    @MethodSource("requiredAttributeLists")
    void validation_rejectsMissingRequiredAttributeList(
            Consumer<CreateKeyRequestV2Dto> removeAttributeList,
            String expectedProperty) {
        // given
        var request = validRequest();
        removeAttributeList.accept(request);

        // when
        var violations = VALIDATOR.validate(request);

        // then
        assertEquals(1, violations.size());
        assertEquals(expectedProperty, violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void validation_acceptsEmptyAttributeLists() {
        // given
        var request = validRequest();

        // when
        var violations = VALIDATOR.validate(request);

        // then
        assertTrue(violations.isEmpty());
    }

    @Test
    void validation_rejectsMissingExecutionMode() {
        // given
        var request = validRequest();
        request.setExecutionMode(null);

        // when
        var violations = VALIDATOR.validate(request);

        // then
        assertViolation(violations, "executionMode", NotNull.class);
    }

    @Test
    void validation_rejectsEmptyAndNullKeyUsages() {
        // given
        var request = validRequest();

        // when
        request.setKeyUsages(Set.of());
        var emptyUsages = VALIDATOR.validate(request);
        request.setKeyUsages(Collections.singleton(null));
        var nullUsage = VALIDATOR.validate(request);

        // then
        assertViolation(emptyUsages, "keyUsages", NotEmpty.class);
        assertViolation(nullUsage, "keyUsages[].<iterable element>", NotNull.class);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidKeyCreationIds")
    void validation_rejectsInvalidKeyCreationId(String keyCreationId, String expectedMessage) {
        // given
        var request = validRequest();
        request.setKeyCreationId(keyCreationId);

        // when
        var violationMessages = VALIDATOR.validate(request).stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        // then
        assertEquals(List.of(expectedMessage), violationMessages);
    }

    @Test
    void validation_acceptsKeyCreationIdAtMaximumLength() {
        // given
        var maximumLengthKeyCreationId = "a".repeat(256);
        var request = validRequest();
        request.setKeyCreationId(maximumLengthKeyCreationId);

        // when
        var violations = VALIDATOR.validate(request);

        // then
        assertTrue(violations.isEmpty());
    }

    @Test
    void toString_excludesKeyCreationId() {
        // given
        var sensitiveKeyCreationId = "sensitive-operation-id";
        var request = validRequest();
        request.setKeyCreationId(sensitiveKeyCreationId);

        // when
        var logRepresentation = request.toString();

        // then
        assertFalse(logRepresentation.contains(sensitiveKeyCreationId));
    }

    private static Stream<Arguments> requiredAttributeLists() {
        return Stream.of(
                arguments(
                        named("token attributes",
                                (Consumer<CreateKeyRequestV2Dto>) request -> request.setTokenAttributes(null)),
                        "tokenAttributes"),
                arguments(
                        named("token profile attributes",
                                (Consumer<CreateKeyRequestV2Dto>) request -> request.setTokenProfileAttributes(null)),
                        "tokenProfileAttributes"),
                arguments(
                        named("create key attributes",
                                (Consumer<CreateKeyRequestV2Dto>) request -> request.setCreateKeyAttributes(null)),
                        "createKeyAttributes"));
    }

    private static Stream<Arguments> invalidKeyCreationIds() {
        return Stream.of(
                arguments(named("null", (String) null), "keyCreationId is required"),
                arguments(named("blank", " "), "keyCreationId is required"),
                arguments(
                        named("exceeds maximum length", "a".repeat(257)),
                        "keyCreationId must not exceed 256 characters"));
    }

    private static CreateKeyRequestV2Dto validRequest() {
        var request = new CreateKeyRequestV2Dto();
        request.setTokenAttributes(List.of());
        request.setTokenProfileAttributes(List.of());
        request.setCreateKeyAttributes(List.of());
        request.setKeyCreationId("operation-1");
        request.setKeyUsages(Set.of(KeyUsage.SIGN));
        request.setExecutionMode(OperationExecutionMode.SYNCHRONOUS);
        return request;
    }
}
