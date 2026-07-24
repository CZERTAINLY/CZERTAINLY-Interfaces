package com.otilm.api.model.connector.v2.cryptography.key;

import com.otilm.api.model.common.enums.cryptography.KeyAlgorithm;
import com.otilm.api.model.connector.cryptography.v2.key.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static com.otilm.api.model.connector.v2.cryptography.MetadataTestUtils.stringMetadata;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KeyResponseValidatorTest {

    @Test
    void validateCompletedSecret_rejectsMissingDescriptorAndMetadata() {
        // given
        var response = new SecretKeyDataResponseV2Dto();

        // when
        Executable missingDescriptor = () -> KeyResponseValidator.validateCompleted(response);
        response.setKeyData(validSecretKeyData());
        Executable missingMetadata = () -> KeyResponseValidator.validateCompleted(response);

        // then
        assertThrows(IllegalArgumentException.class, missingDescriptor);
        assertThrows(IllegalArgumentException.class, missingMetadata);
    }

    @Test
    void validateCompletedSecret_acceptsCompleteResponse() {
        // given
        var response = new SecretKeyDataResponseV2Dto();
        response.setKeyData(validSecretKeyData());
        response.setKeyMeta(List.of(stringMetadata("keyId", "secret-1")));

        // when
        Executable validate = () -> KeyResponseValidator.validateCompleted(response);

        // then
        assertDoesNotThrow(validate);
    }

    @Test
    void validateCompletedPair_requiresBothDescriptorsAndAllMetadataHandles() {
        // given
        var responseWithoutPairMetadata = validKeyPairResponse();
        responseWithoutPairMetadata.setKeyPairMeta(null);
        var responseWithoutPrivateDescriptor = validKeyPairResponse();
        responseWithoutPrivateDescriptor.setPrivateKeyData(null);

        // when
        Executable missingPairMetadata = () ->
                KeyResponseValidator.validateCompleted(responseWithoutPairMetadata);
        Executable missingPrivateDescriptor = () ->
                KeyResponseValidator.validateCompleted(responseWithoutPrivateDescriptor);

        // then
        assertThrows(IllegalArgumentException.class, missingPairMetadata);
        assertThrows(IllegalArgumentException.class, missingPrivateDescriptor);
    }

    @Test
    void validateCompletedPair_acceptsCompleteResponse() {
        // given
        var response = validKeyPairResponse();

        // when
        Executable validate = () -> KeyResponseValidator.validateCompleted(response);

        // then
        assertDoesNotThrow(validate);
    }

    private static KeyPairDataResponseV2Dto validKeyPairResponse() {
        var privateResponse = new PrivateKeyDataResponseV2Dto();
        privateResponse.setKeyMeta(List.of(stringMetadata("keyId", "private-1")));
        privateResponse.setKeyData(validPrivateKeyData());
        var publicResponse = new PublicKeyDataResponseV2Dto();
        publicResponse.setKeyMeta(List.of(stringMetadata("keyId", "public-1")));
        publicResponse.setKeyData(validPublicKeyData());
        var response = new KeyPairDataResponseV2Dto();
        response.setPrivateKeyData(privateResponse);
        response.setPublicKeyData(publicResponse);
        response.setKeyPairMeta(List.of(stringMetadata("pairId", "pair-1")));
        return response;
    }

    private static SecretKeyDataV2Dto validSecretKeyData() {
        var keyData = new SecretKeyDataV2Dto();
        keyData.setAlgorithm(KeyAlgorithm.AES);
        keyData.setLength(256);
        return keyData;
    }

    private static PrivateKeyDataV2Dto validPrivateKeyData() {
        var keyData = new PrivateKeyDataV2Dto();
        keyData.setAlgorithm(KeyAlgorithm.RSA);
        keyData.setLength(2048);
        return keyData;
    }

    private static PublicKeyDataV2Dto validPublicKeyData() {
        var keyData = new PublicKeyDataV2Dto();
        keyData.setAlgorithm(KeyAlgorithm.RSA);
        keyData.setLength(2048);
        return keyData;
    }
}
