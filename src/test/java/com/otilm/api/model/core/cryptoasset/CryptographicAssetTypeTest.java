package com.otilm.api.model.core.cryptoasset;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otilm.api.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CryptographicAssetTypeTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void findByCode_resolvesWireCode() {
        Assertions.assertEquals(CryptographicAssetType.ALGORITHM, CryptographicAssetType.findByCode("algorithm"));
        Assertions.assertEquals(CryptographicAssetType.CERTIFICATE, CryptographicAssetType.findByCode("certificate"));
        Assertions.assertEquals(CryptographicAssetType.PROTOCOL, CryptographicAssetType.findByCode("protocol"));
        Assertions
                .assertEquals(CryptographicAssetType.RELATED_CRYPTO_MATERIAL,
                        CryptographicAssetType.findByCode("related-crypto-material"));
        Assertions.assertEquals(CryptographicAssetType.UNCLASSIFIED, CryptographicAssetType.findByCode("unclassified"));
    }

    @Test
    void findByCode_rejectsUnknownCode() {
        Assertions.assertThrows(ValidationException.class, () -> CryptographicAssetType.findByCode("keypair"));
    }

    @Test
    void serializesToWireCode() throws Exception {
        Assertions
                .assertEquals("\"related-crypto-material\"",
                        mapper.writeValueAsString(CryptographicAssetType.RELATED_CRYPTO_MATERIAL));
    }

    @Test
    void deserializesFromWireCode() throws Exception {
        Assertions
                .assertEquals(CryptographicAssetType.UNCLASSIFIED,
                        mapper.readValue("\"unclassified\"", CryptographicAssetType.class));
    }
}
