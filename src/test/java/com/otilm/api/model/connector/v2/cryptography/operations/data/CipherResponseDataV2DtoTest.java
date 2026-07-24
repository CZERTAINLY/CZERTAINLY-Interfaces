package com.otilm.api.model.connector.v2.cryptography.operations.data;

import com.otilm.api.model.connector.cryptography.v2.operations.data.CipherResponseDataV2Dto;
import org.junit.jupiter.api.Test;

import static com.otilm.api.model.connector.v2.cryptography.RedactionTestUtils.valueWithToString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CipherResponseDataV2DtoTest {

    @Test
    void toString_redactsDataAndProviderDetails() {
        // given
        var dataMarker = "[101, 102, 103]";
        var detailsMarker = "PROVIDER-SENSITIVE-DETAILS";
        var identifier = "item-1";
        var item = new CipherResponseDataV2Dto(
                new byte[]{101, 102, 103}, identifier, valueWithToString(detailsMarker));

        // when
        var representation = item.toString();

        // then
        assertFalse(representation.contains(dataMarker));
        assertFalse(representation.contains(detailsMarker));
        assertTrue(representation.contains(identifier));
    }
}
