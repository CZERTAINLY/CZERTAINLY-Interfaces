package com.otilm.api.model.core.cbom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CbomAssetSyncStateTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void findByCode_resolvesWireCode() {
        Assertions.assertEquals(CbomAssetSyncState.PENDING, CbomAssetSyncState.findByCode("pending"));
        Assertions.assertEquals(CbomAssetSyncState.IN_PROGRESS, CbomAssetSyncState.findByCode("inProgress"));
        Assertions.assertEquals(CbomAssetSyncState.SYNCED, CbomAssetSyncState.findByCode("synced"));
        Assertions.assertEquals(CbomAssetSyncState.FAILED, CbomAssetSyncState.findByCode("failed"));
    }

    @Test
    void findByCode_rejectsUnknownCode() {
        Assertions.assertThrows(ValidationException.class, () -> CbomAssetSyncState.findByCode("done"));
    }

    @Test
    void serializesToWireCode() throws Exception {
        Assertions.assertEquals("\"inProgress\"", mapper.writeValueAsString(CbomAssetSyncState.IN_PROGRESS));
    }

    @Test
    void deserializesFromWireCode() throws Exception {
        Assertions.assertEquals(CbomAssetSyncState.FAILED, mapper.readValue("\"failed\"", CbomAssetSyncState.class));
    }
}
