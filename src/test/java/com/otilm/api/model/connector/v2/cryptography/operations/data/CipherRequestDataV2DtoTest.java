package com.otilm.api.model.connector.v2.cryptography.operations.data;

import com.otilm.api.model.connector.cryptography.v2.operations.data.CipherRequestDataV2Dto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CipherRequestDataV2DtoTest {

    @Test
    void toString_redactsData() {
        // given
        var dataMarker = "[101, 102, 103]";
        var identifier = "item-1";
        var item = new CipherRequestDataV2Dto(new byte[]{101, 102, 103}, identifier);

        // when
        var representation = item.toString();

        // then
        assertFalse(representation.contains(dataMarker));
        assertTrue(representation.contains(identifier));
    }
}
