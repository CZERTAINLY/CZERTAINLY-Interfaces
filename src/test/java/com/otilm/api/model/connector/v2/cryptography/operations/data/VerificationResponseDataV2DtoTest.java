package com.otilm.api.model.connector.v2.cryptography.operations.data;

import com.otilm.api.model.connector.cryptography.v2.operations.data.VerificationResponseDataV2Dto;
import org.junit.jupiter.api.Test;

import static com.otilm.api.model.connector.v2.cryptography.RedactionTestUtils.valueWithToString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VerificationResponseDataV2DtoTest {

    @Test
    void toString_redactsProviderDetails() {
        // given
        var detailsMarker = "PROVIDER-SENSITIVE-DETAILS";
        var identifier = "item-1";
        var item = new VerificationResponseDataV2Dto(true, identifier, valueWithToString(detailsMarker));

        // when
        var representation = item.toString();

        // then
        assertFalse(representation.contains(detailsMarker));
        assertTrue(representation.contains(identifier));
    }
}
