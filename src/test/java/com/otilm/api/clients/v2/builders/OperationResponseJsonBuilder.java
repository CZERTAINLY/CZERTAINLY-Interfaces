package com.otilm.api.clients.v2.builders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static com.otilm.api.model.connector.v2.cryptography.MetadataTestUtils.stringMetadata;

public final class OperationResponseJsonBuilder {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final ObjectNode response = OBJECT_MAPPER.createObjectNode();

    private OperationResponseJsonBuilder() {
        response.putArray("operationMeta");
    }

    public static OperationResponseJsonBuilder anOperationResponse() {
        return new OperationResponseJsonBuilder();
    }

    public OperationResponseJsonBuilder withOperationMetadata(String name, String value) {
        response.withArray("operationMeta")
                .add(OBJECT_MAPPER.valueToTree(stringMetadata(name, value)));
        return this;
    }

    public String build() {
        return response.toString();
    }
}
