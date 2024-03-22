package com.czertainly.api.config.serializer;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContent;
import com.czertainly.api.model.common.attribute.v2.content.CredentialAttributeContent;
import com.czertainly.api.model.common.attribute.v2.content.SecretAttributeContent;
import com.czertainly.api.model.common.attribute.v2.content.data.CredentialAttributeContentData;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResponseAttributeSerializer extends StdSerializer<List<BaseAttributeContent>> {

    public ResponseAttributeSerializer() {
        this(null);
    }

    public ResponseAttributeSerializer(Class<List<BaseAttributeContent>> t) {
        super(t);
    }

    @Override
    public void serialize(List<BaseAttributeContent> response, JsonGenerator gen, SerializerProvider provider) throws IOException {
        ResponseAttributeDto responseAttributeDto = (ResponseAttributeDto) gen.getCurrentValue();
        if (response == null) {
            gen.writeNull();
            return;
        }
        if (responseAttributeDto.getContentType() == null) {
            gen.writeStartArray();
            for (BaseAttributeContent content : response) {
                gen.writeObject(content);
            }
            gen.writeEndArray();
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (responseAttributeDto.getContentType().equals(AttributeContentType.SECRET)) {
            gen.writeStartArray();
            for (BaseAttributeContent content : responseAttributeDto.getContent()) {
                SecretAttributeContent secretAttributeContent = objectMapper.convertValue(content, SecretAttributeContent.class);
                secretAttributeContent.setData(null);
                gen.writeObject(secretAttributeContent);
            }
        } else if (responseAttributeDto.getContentType().equals(AttributeContentType.CREDENTIAL)) {
            gen.writeStartArray();
            for (BaseAttributeContent credential : responseAttributeDto.getContent()) {
                CredentialAttributeContent credentialAttributeContent = objectMapper.convertValue(credential, CredentialAttributeContent.class);
                List<DataAttribute> credentialAttributes = new ArrayList<>();
                CredentialAttributeContentData credentialDto = credentialAttributeContent.getData();

                // attributes can be null when serializing credential content not loaded with full credentials but as NameAndUuidDto
                if (credentialDto.getAttributes() != null) {
                    for (DataAttribute credentialAttribute : credentialDto.getAttributes()) {
                        List<BaseAttributeContent> credentialAttributeContents = new ArrayList<>();
                        if (credentialAttribute.getContentType().equals(AttributeContentType.SECRET)) {
                            for (BaseAttributeContent baseAttributeContent : credentialAttribute.getContent()) {
                                SecretAttributeContent secretAttributeContent = objectMapper.convertValue(baseAttributeContent, SecretAttributeContent.class);
                                secretAttributeContent.setData(null);
                                credentialAttributeContents.add(secretAttributeContent);
                            }
                        } else {
                            credentialAttributeContents.addAll(credentialAttribute.getContent());
                        }
                        credentialAttribute.setContent(credentialAttributeContents);
                        credentialAttributes.add(credentialAttribute);
                    }
                }

                credentialDto.setAttributes(credentialAttributes);
                credential.setData(credentialDto);
                gen.writeObject(credential);
            }
        } else {
            gen.writeStartArray();
            for (BaseAttributeContent content : response) {
                gen.writeObject(content);
            }
        }
        gen.writeEndArray();
    }
}

