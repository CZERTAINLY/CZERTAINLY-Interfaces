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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Named.named;

class SignatureResultItemV2DtoTest {

    private static final String FAILURE_REASON = "Operation did not complete";

    @AutoClose
    private static final ValidatorFixture VALIDATORS = new ValidatorFixture();
    private static final Validator VALIDATOR = VALIDATORS.validator();

    @Test
    void toString_redactsSignature() {
        // given
        var signatureMarker = "[101, 102, 103]";
        var identifier = "item-1";
        var item = new SignatureResultItemV2Dto(identifier, OperationStatus.COMPLETED, new byte[]{101, 102, 103}, null);

        // when
        var representation = item.toString();

        // then
        assertFalse(representation.contains(signatureMarker));
        assertTrue(representation.contains(identifier));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("signatureStatusMatrix")
    void validation_matchesStateContract_forSigningItem(SignatureStatusCase statusCase) {
        // given
        byte[] signature = statusCase.signaturePresent() ? new byte[]{1} : null;
        String reason = statusCase.reasonPresent() ? FAILURE_REASON : null;
        SignatureResultItemV2Dto item = resultWith(statusCase.status(), signature, reason);

        // when
        Set<ConstraintViolation<SignatureResultItemV2Dto>> violations = VALIDATOR.validate(item);

        // then
        assertEquals(statusCase.valid(), violations.isEmpty(), () -> "Expected valid=" + statusCase.valid() + ", got "
                + violations.stream().map(v -> v.getPropertyPath() + ": " + v.getMessage()).toList());
    }

    static Stream<Named<SignatureStatusCase>> signatureStatusMatrix() {
        return Stream
                .of(OperationStatus.values())
                .flatMap(status -> Stream
                        .of(false, true)
                        .flatMap(signaturePresent -> Stream.of(false, true).map(reasonPresent -> {
                            boolean valid = isSigningStateValid(status, signaturePresent, reasonPresent);
                            String name = status + ", signature " + presence(signaturePresent) + ", reason "
                                    + presence(reasonPresent);
                            return named(name, new SignatureStatusCase(status, signaturePresent, reasonPresent, valid));
                        })));
    }

    @Test
    void validation_rejectsEmptySignature_forCompletedStatus() {
        // given
        byte[] emptySignature = new byte[0];
        SignatureResultItemV2Dto item = resultWith(OperationStatus.COMPLETED, emptySignature, null);

        // when
        Set<ConstraintViolation<SignatureResultItemV2Dto>> violations = VALIDATOR.validate(item);

        // then
        assertHasStateViolation(violations);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("failedOrCancelledStatuses")
    void validation_rejectsBlankReason_forFailedOrCancelledStatus(OperationStatus status) {
        // given
        String blankReason = "   ";
        SignatureResultItemV2Dto item = resultWith(status, null, blankReason);

        // when
        Set<ConstraintViolation<SignatureResultItemV2Dto>> violations = VALIDATOR.validate(item);

        // then
        assertHasStateViolation(violations);
    }

    static Stream<Named<OperationStatus>> failedOrCancelledStatuses() {
        return Stream.of(named("failed", OperationStatus.FAILED), named("cancelled", OperationStatus.CANCELLED));
    }

    private static boolean isSigningStateValid(OperationStatus status, boolean signaturePresent,
            boolean reasonPresent) {
        return switch (status) {
            case IN_PROGRESS -> !signaturePresent && !reasonPresent;
            case COMPLETED -> signaturePresent && !reasonPresent;
            case FAILED, CANCELLED -> !signaturePresent && reasonPresent;
        };
    }

    private static String presence(boolean present) {
        return present ? "present" : "absent";
    }

    private static SignatureResultItemV2Dto resultWith(OperationStatus status, byte[] signature, String reason) {
        String identifier = "item-1";
        return new SignatureResultItemV2Dto(identifier, status, signature, reason);
    }

    private static void assertHasStateViolation(Set<ConstraintViolation<SignatureResultItemV2Dto>> violations) {
        String expectedPath = "resultConsistentWithStatus";
        String expectedMessage = "signature and reason must be consistent with status";
        assertTrue(
                violations
                        .stream()
                        .anyMatch(violation -> violation.getPropertyPath().toString().equals(expectedPath)
                                && violation.getMessage().equals(expectedMessage)),
                () -> "Expected " + expectedPath + ": " + expectedMessage + ", got "
                        + violations.stream().map(v -> v.getPropertyPath() + ": " + v.getMessage()).toList());
    }

    private record SignatureStatusCase(OperationStatus status, boolean signaturePresent, boolean reasonPresent,
            boolean valid) {
    }
}
