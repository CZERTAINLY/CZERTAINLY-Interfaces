package com.otilm.api.model.connector.cryptography.v2.utils;

import com.otilm.api.model.common.attribute.common.MetadataAttribute;
import com.otilm.api.model.common.attribute.common.content.AttributeContentType;
import com.otilm.api.model.common.attribute.common.properties.MetadataAttributeProperties;
import com.otilm.api.model.common.attribute.v2.MetadataAttributeV2;
import com.otilm.api.model.common.attribute.v2.content.StringAttributeContentV2;
import com.otilm.api.model.common.enums.cryptography.KeyAlgorithm;
import com.otilm.api.model.connector.cryptography.v2.TokenProfileScopedRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.PrivateKeyDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.PrivateKeyDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.PublicKeyDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.PublicKeyDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.SecretKeyDataResponseV2Dto;
import com.otilm.api.model.connector.cryptography.v2.key.SecretKeyDataV2Dto;
import com.otilm.api.model.connector.cryptography.v2.token.TokenScopedRequestV2Dto;
import com.otilm.api.model.core.cryptography.key.KeyUsage;
import java.util.Base64;
import java.util.List;
import java.util.Set;

public final class CryptographyDtoFixtures {

    private static final String METADATA_UUID = "00000000-0000-0000-0000-000000000001";
    private static final String RSA_2048_PUBLIC_KEY_SPKI_BASE64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvdLbQTpr/mGvt4TmMcc4+eE4+ZzNU6ju4EZ33zkmNz5pPmHK+"
            + "r8LuKQLMTgIRp89xQMePPk0mTo/xiAzH9Oedej6JpdHr8dc96JgD/mVqu2GTj02bABV4One/tmDHzXGXf3dchT9HdYWOD"
            + "QmTk0ktQ2CdyASffiCelZOLLGdtZgGDVvPlvU+45sDqRVG3h4I/L3pnzgV6TJf6Rrh0HZEuBI+e7VVqUvbvPOIWGBiM+B"
            + "EEjUlUwYAkCKH1btk25hGejipeB/haHejOdvukQhPd85m5/94wz5+x4XiSTK5itd+zoYhVTXruLUrL4rC5hAYJ+y5RTuo"
            + "ZQDoM7cVebZG6wIDAQAB";

    private CryptographyDtoFixtures() {
    }

    public static MetadataAttributeV2 validMetadataAttribute() {
        MetadataAttributeV2 metadata = new MetadataAttributeV2();
        metadata.setUuid(METADATA_UUID);
        metadata.setName("provider handle");
        metadata.setContentType(AttributeContentType.STRING);
        metadata.setProperties(new MetadataAttributeProperties());
        metadata.setContent(List.of(new StringAttributeContentV2("provider-key-1")));
        return metadata;
    }

    public static List<MetadataAttribute> validMetadata() {
        return List.of(validMetadataAttribute());
    }

    public static <T extends TokenScopedRequestV2Dto> T withValidTokenScope(T dto) {
        dto.setTokenAttributes(List.of());
        return dto;
    }

    public static <T extends TokenProfileScopedRequestV2Dto> T withValidTokenProfileScope(T dto) {
        withValidTokenScope(dto);
        dto.setTokenProfileAttributes(List.of());
        dto.setKeyUsages(Set.of(KeyUsage.SIGN));
        return dto;
    }

    public static SecretKeyDataV2Dto validSecretKeyData() {
        SecretKeyDataV2Dto keyData = new SecretKeyDataV2Dto();
        keyData.setAlgorithm(KeyAlgorithm.RSA);
        keyData.setLength(2048);
        return keyData;
    }

    public static PrivateKeyDataV2Dto validPrivateKeyData() {
        PrivateKeyDataV2Dto keyData = new PrivateKeyDataV2Dto();
        keyData.setAlgorithm(KeyAlgorithm.RSA);
        keyData.setLength(2048);
        return keyData;
    }

    public static PublicKeyDataV2Dto validPublicKeyData() {
        PublicKeyDataV2Dto keyData = new PublicKeyDataV2Dto();
        keyData.setAlgorithm(KeyAlgorithm.RSA);
        keyData.setLength(2048);
        keyData.setPublicKeySpki(Base64.getDecoder().decode(RSA_2048_PUBLIC_KEY_SPKI_BASE64));
        return keyData;
    }

    public static SecretKeyDataResponseV2Dto validSecretKeyDataResponse() {
        SecretKeyDataResponseV2Dto response = new SecretKeyDataResponseV2Dto();
        response.setKeyMeta(validMetadata());
        response.setKeyData(validSecretKeyData());
        return response;
    }

    public static PrivateKeyDataResponseV2Dto validPrivateKeyDataResponse() {
        PrivateKeyDataResponseV2Dto response = new PrivateKeyDataResponseV2Dto();
        response.setKeyMeta(validMetadata());
        response.setKeyData(validPrivateKeyData());
        return response;
    }

    public static PublicKeyDataResponseV2Dto validPublicKeyDataResponse() {
        PublicKeyDataResponseV2Dto response = new PublicKeyDataResponseV2Dto();
        response.setKeyMeta(validMetadata());
        response.setKeyData(validPublicKeyData());
        return response;
    }
}
