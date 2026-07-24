package com.otilm.api.model.connector.v2.cryptography.operations;

import com.otilm.api.model.connector.cryptography.v2.operations.RandomDataResponseV2Dto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class RandomDataResponseV2DtoTest {

    @Test
    void toString_redactsRandomData() {
        // given
        var dataMarker = "[101, 102, 103]";
        var response = new RandomDataResponseV2Dto();
        response.setData(new byte[]{101, 102, 103});

        // when
        var representation = response.toString();

        // then
        assertFalse(representation.contains(dataMarker));
    }
}
