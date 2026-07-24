package com.otilm.api.model.connector.v2.cryptography.key;

import com.otilm.api.model.connector.cryptography.v2.key.PublicKeyDataV2Dto;
import org.junit.jupiter.api.Test;

import java.security.KeyPairGenerator;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;

class PublicKeyDataV2DtoTest {

    @Test
    void toString_redactsPublicKeySpki() throws Exception {
        // given
        var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        var publicKeySpki = keyPairGenerator.generateKeyPair().getPublic().getEncoded();
        var spkiMarker = Arrays.toString(publicKeySpki);
        var keyData = new PublicKeyDataV2Dto();
        keyData.setPublicKeySpki(publicKeySpki);

        // when
        var representation = keyData.toString();

        // then
        assertFalse(representation.contains(spkiMarker));
    }
}
