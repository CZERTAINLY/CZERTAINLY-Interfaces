package com.czertainly.api.config.serializer;

import com.czertainly.api.model.common.attribute.common.AttributeType;
import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import com.czertainly.api.model.common.attribute.v3.CustomAttributeV3;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class BaseAttributeSerializer
        extends JsonSerializer<BaseAttribute> {

    @Override
    public void serialize(BaseAttribute value,
                          JsonGenerator gen,
                          SerializerProvider serializers)
            throws IOException {

        if (value.getType() == AttributeType.CUSTOM) {
            CustomAttributeV3 customAttributeV3 = (CustomAttributeV3) value;
            gen.writeStartObject();

            gen.writeStringField("type", value.getType().getCode());
            gen.writeNumberField("version", value.getVersion());
            gen.writeStringField("uuid", value.getUuid());
            gen.writeStringField("name", value.getName());
            gen.writeStringField("description", value.getDescription());

            gen.writeFieldName("content");
            serializers.defaultSerializeValue(value.getContent(), gen);

            gen.writeStringField("contentType", customAttributeV3.getContentType().getCode());

            gen.writeFieldName("properties");
            serializers.defaultSerializeValue(customAttributeV3.getProperties(), gen);

            gen.writeEndObject();
        } else {
            JsonSerializer<Object> defaultSerializer =
                    serializers.findValueSerializer(value.getClass());

            defaultSerializer.serialize(value, gen, serializers);
        }
    }

    @Override
    public Class<BaseAttribute> handledType() {
        return BaseAttribute.class;
    }

}

