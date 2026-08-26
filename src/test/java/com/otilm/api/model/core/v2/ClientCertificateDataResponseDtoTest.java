package com.otilm.api.model.core.v2;

import com.otilm.api.model.common.UuidDto;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientCertificateDataResponseDtoTest {

    @Test
    void warningsDefaultToAnEmptyListRatherThanNull() {
        assertEquals(List.of(), new ClientCertificateDataResponseDto().getRequestAttributeWarnings());
    }

    @Test
    void toStringIncludesTheWarnings() {
        ClientCertificateDataResponseDto dto = new ClientCertificateDataResponseDto();
        dto.setUuid(UUID.randomUUID().toString());
        dto.setRequestAttributeWarnings(List.of("warning-sentinel"));

        assertTrue(dto.toString().contains("warning-sentinel"));
    }

    @Test
    void toLogDataOmitsTheWarnings() {
        UUID uuid = UUID.randomUUID();
        ClientCertificateDataResponseDto dto = new ClientCertificateDataResponseDto();
        dto.setUuid(uuid.toString());
        dto.setRequestAttributeWarnings(List.of("warning-sentinel"));

        Serializable logData = dto.toLogData();

        // An audit record must not carry a variable-length warning list.
        assertInstanceOf(UuidDto.class, logData);
        assertFalse(logData.toString().contains("warning-sentinel"));
        assertEquals(uuid.toString(), ((UuidDto) logData).getUuid());
    }
}
