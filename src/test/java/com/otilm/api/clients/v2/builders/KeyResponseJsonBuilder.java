package com.otilm.api.clients.v2.builders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.otilm.api.model.client.cryptography.key.KeyRequestType;

import static com.otilm.api.model.connector.v2.cryptography.MetadataTestUtils.stringMetadata;

public final class KeyResponseJsonBuilder {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final ObjectNode response;
    private ObjectNode keyData;
    private ObjectNode privateKeyData;
    private ObjectNode publicKeyData;

    private KeyResponseJsonBuilder(KeyRequestType keyType) {
        response = switch (keyType) {
            case SECRET -> {
                var secretKeyResponse = OBJECT_MAPPER.createObjectNode();
                secretKeyResponse.putArray("keyMeta");
                keyData = keyData("Secret", "AES", 256);
                secretKeyResponse.set("keyData", keyData);
                yield secretKeyResponse;
            }
            case KEY_PAIR -> {
                var keyPairResponse = OBJECT_MAPPER.createObjectNode();
                keyPairResponse.putArray("keyPairMeta");
                privateKeyData = asymetricKeyData("Private", "RSA", 2048);
                publicKeyData = asymetricKeyData("Public", "RSA", 2048);
                keyPairResponse.set("privateKeyData", privateKeyData);
                keyPairResponse.set("publicKeyData", publicKeyData);
                yield keyPairResponse;
            }
        };
    }

    public static KeyResponseJsonBuilder aSecretKeyResponse() {
        return new KeyResponseJsonBuilder(KeyRequestType.SECRET);
    }

    public static KeyResponseJsonBuilder aKeyPairResponse() {
        return new KeyResponseJsonBuilder(KeyRequestType.KEY_PAIR);
    }

    public KeyResponseJsonBuilder withKeyMetadata(String name, String value) {
        metadata(response, "keyMeta").add(OBJECT_MAPPER.valueToTree(stringMetadata(name, value)));
        return this;
    }

    public KeyResponseJsonBuilder withKeyPairMetadata(String name, String value) {
        metadata(response, "keyPairMeta").add(OBJECT_MAPPER.valueToTree(stringMetadata(name, value)));
        return this;
    }

    public KeyResponseJsonBuilder withPrivateKeyMetadata(String name, String value) {
        metadata(privateKeyData, "keyMeta").add(OBJECT_MAPPER.valueToTree(stringMetadata(name, value)));
        return this;
    }

    public KeyResponseJsonBuilder withPublicKeyMetadata(String name, String value) {
        metadata(publicKeyData, "keyMeta").add(OBJECT_MAPPER.valueToTree(stringMetadata(name, value)));
        return this;
    }

    public KeyResponseJsonBuilder withKeyType(String type) {
        keyData.put("type", type);
        return this;
    }

    public KeyResponseJsonBuilder withKeyAlgorithm(String algorithm) {
        keyData.put("algorithm", algorithm);
        return this;
    }

    public KeyResponseJsonBuilder withKeyLength(int length) {
        keyData.put("length", length);
        return this;
    }

    public KeyResponseJsonBuilder withoutKeyAlgorithm() {
        keyData.remove("algorithm");
        return this;
    }

    public KeyResponseJsonBuilder withKeyMaterial(String format, String value) {
        addKeyMaterial(keyData, format, value);
        return this;
    }

    public KeyResponseJsonBuilder withOuterKeyMaterial(String value) {
        response.put("value", value);
        return this;
    }

    public KeyResponseJsonBuilder withMalformedKeyMetadata(String name, String secret) {
        var metadata = OBJECT_MAPPER.createObjectNode();
        metadata.put("name", name);
        metadata.put("secret", secret);
        metadata(response, "keyMeta").add(metadata);
        return this;
    }

    public KeyResponseJsonBuilder withPrivateKeyType(String type) {
        data(privateKeyData).put("type", type);
        return this;
    }

    public KeyResponseJsonBuilder withPublicKeyType(String type) {
        data(publicKeyData).put("type", type);
        return this;
    }

    public KeyResponseJsonBuilder withKeyPairAlgorithm(String algorithm) {
        data(privateKeyData).put("algorithm", algorithm);
        data(publicKeyData).put("algorithm", algorithm);
        return this;
    }

    public KeyResponseJsonBuilder withKeyPairLength(int length) {
        data(privateKeyData).put("length", length);
        data(publicKeyData).put("length", length);
        return this;
    }

    public KeyResponseJsonBuilder withPrivateKeyMaterial(String format, String value) {
        addKeyMaterial(data(privateKeyData), format, value);
        return this;
    }

    public KeyResponseJsonBuilder withPublicKeySpki(String publicKeySpki) {
        data(publicKeyData).put("publicKeySpki", publicKeySpki);
        return this;
    }

    public String build() {
        return response.toString();
    }

    private static ObjectNode asymetricKeyData(String type, String algorithm, int length) {
        var response = OBJECT_MAPPER.createObjectNode();
        response.putArray("keyMeta");
        response.set("keyData", keyData(type, algorithm, length));
        return response;
    }

    private static ObjectNode keyData(String type, String algorithm, int length) {
        var keyData = OBJECT_MAPPER.createObjectNode();
        keyData.put("type", type);
        keyData.put("algorithm", algorithm);
        keyData.put("length", length);
        return keyData;
    }

    private static ObjectNode data(ObjectNode keyResponse) {
        return (ObjectNode) keyResponse.get("keyData");
    }

    private static ArrayNode metadata(ObjectNode keyResponse, String fieldName) {
        return (ArrayNode) keyResponse.get(fieldName);
    }

    private static void addKeyMaterial(ObjectNode keyData, String format, String value) {
        keyData.put("format", format);
        var encodedValue = OBJECT_MAPPER.createObjectNode();
        encodedValue.put("value", value);
        keyData.set("value", encodedValue);
    }

}
