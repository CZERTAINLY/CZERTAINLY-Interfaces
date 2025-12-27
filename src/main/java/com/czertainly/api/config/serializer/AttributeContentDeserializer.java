package com.czertainly.api.config.serializer;

import com.czertainly.api.model.common.attribute.common.AttributeContent;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContentV2;
import com.czertainly.api.model.common.attribute.v3.content.BaseAttributeContentV3;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class AttributeContentDeserializer extends JsonDeserializer<AttributeContent> {

    @Override
    public AttributeContent deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        boolean hasContentType = node.has("contentType");

        if (hasContentType && !node.get("contentType").isNull()) return jsonParser.getCodec().treeToValue(node, BaseAttributeContentV3.class);
        else return jsonParser.getCodec().treeToValue(node, BaseAttributeContentV2.class);
    }
}
