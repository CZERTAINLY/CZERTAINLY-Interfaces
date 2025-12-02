package com.czertainly.api.config.serializer;

import com.czertainly.api.model.client.attribute.ResponseAttribute;
import com.czertainly.api.model.client.attribute.ResponseAttributeV2Dto;
import com.czertainly.api.model.common.attribute.common.AttributeContent;
import com.czertainly.api.model.common.attribute.common.DataAttribute;
import com.czertainly.api.model.common.attribute.v2.DataAttributeV2;
import com.czertainly.api.model.common.attribute.v2.content.*;
import com.czertainly.api.model.common.attribute.v2.content.BaseAttributeContentV2;
import com.czertainly.api.model.common.attribute.v2.content.CredentialAttributeContentV2;
import com.czertainly.api.model.common.attribute.v2.content.data.CredentialAttributeContentData;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResponseAttributeSerializer extends StdSerializer<List<AttributeContent>> {

    public ResponseAttributeSerializer(Class<List<AttributeContent>> t) {
        super(t);
    }

    @Override
    public void serialize(List<AttributeContent> response, JsonGenerator gen, SerializerProvider provider) throws IOException {
        ResponseAttributeV2Dto responseAttribute = (ResponseAttributeV2Dto) gen.getCurrentValue();
        if (response == null) {
            gen.writeNull();
            return;
        }
        if (responseAttribute.getContentType() == null) {
            gen.writeStartArray();
            for (AttributeContent content : response) {
                gen.writeObject(content);
            }
            gen.writeEndArray();
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (responseAttribute.getContentType().equals(AttributeContentType.SECRET)) {
            gen.writeStartArray();
            for (Object content : responseAttribute.getContent()) {
                SecretAttributeContentV2 secretAttributeContent = objectMapper.convertValue(content, SecretAttributeContentV2.class);
                secretAttributeContent.setData(null);
                gen.writeObject(secretAttributeContent);
            }
        } else if (responseAttribute.getContentType().equals(AttributeContentType.CREDENTIAL)) {
            gen.writeStartArray();
            for (Object credential : responseAttribute.getContent()) {
                BaseAttributeContentV2 credentialV2 = (BaseAttributeContentV2) credential;
                CredentialAttributeContentV2 credentialAttributeContent = objectMapper.convertValue(credential, CredentialAttributeContentV2.class);
                List<DataAttribute<?>> credentialAttributes = new ArrayList<>();
                CredentialAttributeContentData credentialDto = credentialAttributeContent.getData();

                // attributes can be null when serializing credential content not loaded with full credentials but as NameAndUuidDto
                if (credentialDto.getAttributes() != null) {
                    for (DataAttribute<?> credentialAttribute : credentialDto.getAttributes()) {
                        DataAttributeV2 dataAttributeV2 = (DataAttributeV2) credentialAttribute;
                        List<BaseAttributeContentV2> credentialAttributeContents = new ArrayList<>();
                        if (dataAttributeV2.getContentType().equals(AttributeContentType.SECRET)) {
                            for (BaseAttributeContentV2 baseAttributeContent : dataAttributeV2.getContent()) {
                                SecretAttributeContentV2 secretAttributeContent = objectMapper.convertValue(baseAttributeContent, SecretAttributeContentV2.class);
                                secretAttributeContent.setData(null);
                                credentialAttributeContents.add(secretAttributeContent);
                            }
                        } else {
                            credentialAttributeContents.addAll(dataAttributeV2.getContent());
                        }
                        dataAttributeV2.setContent(credentialAttributeContents);
//                        credentialAttributes.add(dataAttributeV2);
                    }
                }

                credentialDto.setAttributes(credentialAttributes);
                credentialV2.setData(credentialDto);
                gen.writeObject(credentialV2);
            }
        } else {
            gen.writeStartArray();
            for (AttributeContent content : response) {
                gen.writeObject(content);
            }
        }
        gen.writeEndArray();
    }
}

