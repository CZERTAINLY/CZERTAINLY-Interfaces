package com.otilm.api.model.connector.v2.cryptography.operations.data;

import com.otilm.api.model.connector.common.v2.OperationStatus;
import com.otilm.api.model.connector.cryptography.v2.operations.data.SignatureItemResultV2Dto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignatureItemResultV2DtoTest {

    @Test
    void toString_redactsSignature() {
        // given
        var signatureMarker = "[101, 102, 103]";
        var identifier = "item-1";
        var item = new SignatureItemResultV2Dto(
                identifier, OperationStatus.COMPLETED, new byte[]{101, 102, 103}, null);

        // when
        var representation = item.toString();

        // then
        assertFalse(representation.contains(signatureMarker));
        assertTrue(representation.contains(identifier));
    }
}
