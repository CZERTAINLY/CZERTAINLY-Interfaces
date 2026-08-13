package com.otilm.api.model.connector.cryptography.v2;

import com.otilm.api.model.client.attribute.RequestAttribute;
import com.otilm.api.model.client.attribute.RequestAttributeV2;
import com.otilm.api.model.common.attribute.common.MetadataAttribute;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import com.otilm.api.model.common.attribute.v2.MetadataAttributeV2;
import com.otilm.api.model.common.attribute.v2.content.StringAttributeContentV2;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyAttributesRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.CreateKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.DestroyKeyRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.KeyOperationResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.SecretKeyDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.SecretKeyDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.CipherDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.RandomDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.SignDataRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.SignDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.SignOperationScopedRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.token.TokenScopedRequestV2Dto;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.otilm.api.model.connector.cryptography.v2.utils.CryptographyDtoFixtures.validMetadataAttribute;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Named.named;

@SuppressWarnings("unchecked")
class SensitiveCryptographyDtoToStringTest {

    private static final String SENSITIVE_MARKER = "credential-or-provider-secret";

    @ParameterizedTest(name = "{0}")
    @MethodSource("credentialAndMetadataBearingDtos")
    void toString_doesNotExposeCredentialOrMetadataContent(Object dto) {
        // given
        Object sensitiveDto = dto;

        // when
        String representation = sensitiveDto.toString();

        // then
        assertFalse(representation.contains(SENSITIVE_MARKER), () -> sensitiveDto.getClass().getSimpleName()
                + " exposed sensitive attribute content: " + representation);
    }

    static Stream<Named<Object>> credentialAndMetadataBearingDtos() {
        TokenScopedRequestV2Dto tokenScope = new TokenScopedRequestV2Dto();
        tokenScope.setTokenAttributes(sensitiveRequestAttributes());

        TokenProfileScopedRequestV2Dto profileScope = new TokenProfileScopedRequestV2Dto();
        profileScope.setTokenProfileAttributes(sensitiveRequestAttributes());

        CreateKeyRequestV2Dto createKey = new CreateKeyRequestV2Dto();
        createKey.setCreateKeyAttributes(sensitiveRequestAttributes());

        CreateKeyAttributesRequestV2Dto createKeyAttributes = new CreateKeyAttributesRequestV2Dto();
        createKeyAttributes.setTokenProfileAttributes(sensitiveRequestAttributes());

        DestroyKeyRequestV2Dto destroyKey = new DestroyKeyRequestV2Dto();
        destroyKey.setKeyMeta(sensitiveMetadata());

        CipherDataRequestV2Dto cipher = new CipherDataRequestV2Dto();
        cipher.setCipherAttributes(sensitiveRequestAttributes());

        SignDataRequestV2Dto sign = new SignDataRequestV2Dto();
        sign.setSignatureAttributes(sensitiveRequestAttributes());

        RandomDataRequestV2Dto random = new RandomDataRequestV2Dto();
        random.setOperationAttributes(sensitiveRequestAttributes());

        KeyOperationRequestV2Dto keyOperation = new KeyOperationRequestV2Dto();
        keyOperation.setOperationMeta(sensitiveMetadata());

        KeyOperationResponseV2Dto keyOperationResponse = new KeyOperationResponseV2Dto();
        keyOperationResponse.setOperationMeta(sensitiveMetadata());

        SignOperationScopedRequestV2Dto signOperation = new SignOperationScopedRequestV2Dto();
        signOperation.setOperationMeta(sensitiveMetadata());

        SignDataResponseV2Dto signResponse = new SignDataResponseV2Dto();
        signResponse.setSignOperationMeta(sensitiveMetadata());

        SecretKeyDataV2Dto keyDescriptor = new SecretKeyDataV2Dto();
        keyDescriptor.setMetadata(sensitiveMetadata());

        SecretKeyDataResponseV2Dto keyResponse = new SecretKeyDataResponseV2Dto();
        keyResponse.setKeyMeta(sensitiveMetadata());

        return Stream
                .of(named("token attributes", tokenScope), named("token-profile attributes", profileScope),
                        named("create-key discovery scope", createKeyAttributes),
                        named("key creation attributes", createKey), named("key destruction metadata", destroyKey),
                        named("cipher attributes", cipher), named("signature attributes", sign),
                        named("random attributes", random), named("key operation request metadata", keyOperation),
                        named("key operation response metadata", keyOperationResponse),
                        named("sign operation metadata", signOperation), named("sign response metadata", signResponse),
                        named("key descriptive metadata", keyDescriptor), named("key response metadata", keyResponse));
    }

    private static List<RequestAttribute> sensitiveRequestAttributes() {
        String attributeName = "credential";
        RequestAttributeV2 attribute = new RequestAttributeV2(UUID.randomUUID(), attributeName,
                AttributeContentType.STRING, List.of(new StringAttributeContentV2(SENSITIVE_MARKER))) {
            @Override
            public String toString() {
                return SENSITIVE_MARKER;
            }
        };
        return List.of(attribute);
    }

    private static List<MetadataAttribute> sensitiveMetadata() {
        MetadataAttributeV2 metadata = new MetadataAttributeV2(validMetadataAttribute()) {
            @Override
            public String toString() {
                return SENSITIVE_MARKER;
            }
        };
        metadata.setContent(List.of(new StringAttributeContentV2(SENSITIVE_MARKER)));
        return List.of(metadata);
    }
}
