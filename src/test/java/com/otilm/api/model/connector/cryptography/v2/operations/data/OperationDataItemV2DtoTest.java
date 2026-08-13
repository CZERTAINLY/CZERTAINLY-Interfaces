package com.otilm.api.model.connector.cryptography.v2.operations.data;

import com.otilm.api.model.connector.common.v2.OperationStatus;
import com.otilm.api.testsupport.ValidatorFixture;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Named.named;

class OperationDataItemV2DtoTest {

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();
    private static final Validator VALIDATOR = VALIDATORS.validator();

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidSignatureData")
    void signatureData_validateRejectsMissingRequiredField(InvalidItem invalid) {
        // given
        SignatureDataV2Dto item = (SignatureDataV2Dto) invalid.item();

        // when
        Set<ConstraintViolation<SignatureDataV2Dto>> violations = VALIDATOR.validate(item);

        // then
        assertHasViolation(violations, invalid.path(), invalid.message());
    }

    static Stream<Named<InvalidItem>> invalidSignatureData() {
        return Stream
                .of(named("null data",
                        new InvalidItem(new SignatureDataV2Dto(null, "item-1"), "data",
                                "data is required and must not be empty")),
                        named("empty data",
                                new InvalidItem(new SignatureDataV2Dto(new byte[0], "item-1"), "data",
                                        "data is required and must not be empty")),
                        named("null identifier",
                                new InvalidItem(new SignatureDataV2Dto(new byte[]{1}, null), "identifier",
                                        "identifier is required and must be unique within the batch")),
                        named("blank identifier", new InvalidItem(new SignatureDataV2Dto(new byte[]{1}, "   "),
                                "identifier", "identifier is required and must be unique within the batch")));
    }

    @Test
    void signatureData_toStringRedactsPayload() {
        // given
        String byteArrayMarker = "[101, 102, 103]";
        String identifier = "item-1";
        SignatureDataV2Dto item = new SignatureDataV2Dto(new byte[]{101, 102, 103}, identifier);

        // when
        String representation = item.toString();

        // then
        assertFalse(representation.contains(byteArrayMarker));
        assertTrue(representation.contains(identifier));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidSignatureResults")
    void signatureResult_validateRejectsMissingRequiredField(InvalidItem invalid) {
        // given
        SignatureResultItemV2Dto item = (SignatureResultItemV2Dto) invalid.item();

        // when
        Set<ConstraintViolation<SignatureResultItemV2Dto>> violations = VALIDATOR.validate(item);

        // then
        assertHasViolation(violations, invalid.path(), invalid.message());
    }

    static Stream<Named<InvalidItem>> invalidSignatureResults() {
        return Stream
                .of(named("null identifier",
                        new InvalidItem(new SignatureResultItemV2Dto(null, OperationStatus.IN_PROGRESS, null, null),
                                "identifier", "identifier is required")),
                        named("blank identifier",
                                new InvalidItem(
                                        new SignatureResultItemV2Dto("   ", OperationStatus.IN_PROGRESS, null, null),
                                        "identifier", "identifier is required")),
                        named("null status", new InvalidItem(new SignatureResultItemV2Dto("item-1", null, null, null),
                                "status", "status is required")));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("invalidVerificationItems")
    void verificationItem_validateRejectsMissingRequiredField(InvalidItem invalid) {
        // given
        VerificationResponseItemV2Dto item = (VerificationResponseItemV2Dto) invalid.item();

        // when
        Set<ConstraintViolation<VerificationResponseItemV2Dto>> violations = VALIDATOR.validate(item);

        // then
        assertHasViolation(violations, invalid.path(), invalid.message());
    }

    static Stream<Named<InvalidItem>> invalidVerificationItems() {
        return Stream
                .of(named("null result",
                        new InvalidItem(new VerificationResponseItemV2Dto(null, "item-1", null), "result",
                                "result is required")),
                        named("null identifier",
                                new InvalidItem(new VerificationResponseItemV2Dto(true, null, null), "identifier",
                                        "identifier is required")),
                        named("blank identifier", new InvalidItem(new VerificationResponseItemV2Dto(true, "   ", null),
                                "identifier", "identifier is required")));
    }

    @Test
    void verificationItem_toStringRedactsDetails() {
        // given
        String sensitiveDetails = "verification-provider-secret";
        String identifier = "item-1";
        VerificationResponseItemV2Dto item = new VerificationResponseItemV2Dto(true, identifier, sensitiveDetails);

        // when
        String representation = item.toString();

        // then
        assertFalse(representation.contains(sensitiveDetails));
        assertTrue(representation.contains(identifier));
    }

    private static void assertHasViolation(Set<? extends ConstraintViolation<?>> violations, String path,
            String message) {
        assertTrue(
                violations
                        .stream()
                        .anyMatch(violation -> violation.getPropertyPath().toString().equals(path)
                                && violation.getMessage().equals(message)),
                () -> "Expected " + path + ": " + message + ", got "
                        + violations.stream().map(v -> v.getPropertyPath() + ": " + v.getMessage()).toList());
    }

    private record InvalidItem(Object item, String path, String message) {
    }
}
