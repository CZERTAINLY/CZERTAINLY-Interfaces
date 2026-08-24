package com.otilm.api.model.core.cbom;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Pins the additive asset-sync fields on the CBOM row: they serialize under the documented names and stay absent-safe,
 * so a Core that has never run an asset sync still produces a valid row.
 */
class CbomDtoTest {

    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void assetSyncFieldsRoundTripThroughJson() throws Exception {
        CbomDto dto = new CbomDto();
        dto.setUuid(UUID.fromString("00000000-0000-4000-8000-000000000299"));
        dto.setSerialNumber("urn:uuid:11111111-2222-3333-4444-555555555555");
        dto.setVersion(3);
        dto.setAssetSyncState(CbomAssetSyncState.SYNCED);
        dto.setAssetSyncedAt(OffsetDateTime.of(2026, 8, 1, 12, 0, 0, 0, ZoneOffset.UTC));

        JsonNode json = mapper.readTree(mapper.writeValueAsString(dto));
        Assertions.assertEquals("synced", json.get("assetSyncState").asText());
        Assertions.assertFalse(json.get("assetSyncedAt").isNull());
        Assertions.assertEquals(dto, mapper.readValue(json.toString(), CbomDto.class));
    }

    @Test
    void assetSyncFieldsAreOptional() throws Exception {
        CbomDto back = mapper.readValue("{\"serialNumber\":\"urn:x\",\"version\":1}", CbomDto.class);
        Assertions.assertNull(back.getAssetSyncState());
        Assertions.assertNull(back.getAssetSyncedAt());
    }
}
