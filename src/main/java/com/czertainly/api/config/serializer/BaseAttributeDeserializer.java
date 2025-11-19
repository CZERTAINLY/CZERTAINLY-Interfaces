package com.czertainly.api.config.serializer;

import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.common.attribute.v2.DataAttributeV2;
import com.czertainly.api.model.common.attribute.v3.DataAttributeV3;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.io.IOException;

public class BaseAttributeDeserializer extends JsonDeserializer<BaseAttribute> {
    @Override
    public BaseAttribute deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);

        String key1 = node.has("version") ? node.get("version").asText() : null;
        String key2 = node.has("type") ? node.get("type").asText() : null;

        if (key1 == null || key2 == null) {
            throw new IllegalArgumentException("Missing required fields: key1 or key2");
        }

        Class<? extends BaseAttribute> valueType;
        if ("2".equals(key1) && "data".equals(key2)) {
            valueType = DataAttributeV2.class;
        } else if ("3".equals(key1) && "data".equals(key2)) {
            valueType = DataAttributeV3.class;
        } else {
            return jp.getCodec().treeToValue(node, BaseAttribute.class);
        }

        return jp.getCodec().treeToValue(node, valueType);
    }
}
