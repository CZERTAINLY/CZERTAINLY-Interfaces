package com.otilm.api.model.connector.v2.cryptography.operations;

import com.otilm.api.model.connector.common.v2.OperationStatus;
import com.otilm.api.model.connector.cryptography.v2.operations.data.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.*;
import static org.junit.jupiter.api.Named.named;

class OperationDataV2ValidationTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("identifierDtos")
    void validation_rejectsBlankIdentifier(Object dto) {
        // given
        var expectedProperty = "identifier";

        // when
        var violations = validate(dto);

        // then
        assertViolation(violations, expectedProperty, NotBlank.class);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("requiredNonEmptyDataDtos")
    void validation_rejectsEmptyByteArray(Object dto) {
        // given
        var expectedProperty = "data";

        // when
        var violations = validate(dto);

        // then
        assertViolation(violations, expectedProperty, NotEmpty.class);
    }

    @Test
    void signatureRequestValidation_rejectsNullData_butAcceptsEmptyData() {
        // given
        var missingData = new SignatureRequestDataV2Dto(null, "item");
        var emptyData = new SignatureRequestDataV2Dto(new byte[0], "item");

        // when
        var missingDataViolations = validate(missingData);

        // then
        assertViolation(missingDataViolations, "data", NotNull.class);
        assertNoViolations(emptyData);
    }

    @Test
    void signatureItemResultValidation_requiresIdentifierAndStatus() {
        // given
        var result = new SignatureItemResultV2Dto(" ", null, null, null);

        // when
        var violations = validate(result);

        // then
        assertViolation(violations, "identifier", NotBlank.class);
        assertViolation(violations, "status", NotNull.class);
    }

    private static Stream<Named<Object>> identifierDtos() {
        return Stream.of(
                named("cipher request", new CipherRequestDataV2Dto(new byte[]{1}, " ")),
                named("cipher response", new CipherResponseDataV2Dto(new byte[]{1}, " ", null)),
                named("signature request", new SignatureRequestDataV2Dto(new byte[]{1}, " ")),
                named("signature response", new SignatureResponseDataV2Dto(new byte[]{1}, " ", null)),
                named("verification response", new VerificationResponseDataV2Dto(true, " ", null)),
                named("signature status item",
                        new SignatureItemResultV2Dto(" ", OperationStatus.COMPLETED, null, null)));
    }

    private static Stream<Named<Object>> requiredNonEmptyDataDtos() {
        return Stream.of(
                named("cipher request", new CipherRequestDataV2Dto(new byte[0], "item")),
                named("cipher response", new CipherResponseDataV2Dto(new byte[0], "item", null)),
                named("signature response", new SignatureResponseDataV2Dto(new byte[0], "item", null)));
    }
}
