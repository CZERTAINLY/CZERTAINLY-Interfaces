package com.otilm.api.model.connector.v2.cryptography.operations;

import com.otilm.api.model.connector.cryptography.v2.operations.RandomDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.RandomDataResponseV2Dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.otilm.api.model.connector.v2.cryptography.ValidationTestUtils.*;

class RandomDataV2DtoTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void requestValidation_rejectsNonPositiveLength(int invalidLength) {
        // given
        var request = new RandomDataRequestV2Dto();
        request.setLength(invalidLength);

        // when
        var violations = validate(request);

        // then
        assertViolation(violations, "length", Positive.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 32})
    void requestValidation_acceptsPositiveLength(int validLength) {
        // given
        var request = new RandomDataRequestV2Dto();
        request.setTokenAttributes(java.util.List.of());
        request.setTokenProfileAttributes(java.util.List.of());
        request.setKeyUsages(java.util.Set.of(com.otilm.api.model.core.cryptography.key.KeyUsage.SIGN));
        request.setLength(validLength);

        // when / then
        assertNoViolations(request);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void responseValidation_rejectsMissingOrEmptyData(byte[] invalidData) {
        // given
        var response = new RandomDataResponseV2Dto();
        response.setData(invalidData);

        // when
        var violations = validate(response);

        // then
        assertViolation(violations, "data", NotEmpty.class);
    }
}
