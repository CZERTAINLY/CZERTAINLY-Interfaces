package com.czertainly.api.config.serializer;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
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

public class ResponseAttributeSerializer extends StdSerializer<List<BaseAttributeContentV2>> {

    public ResponseAttributeSerializer() {
        this(null);
    }

    public ResponseAttributeSerializer(Class<List<BaseAttributeContentV2>> t) {
        super(t);
    }

    @Override
    public void serialize(List<BaseAttributeContentV2> response, JsonGenerator gen, SerializerProvider provider) throws IOException {
        ResponseAttributeDto responseAttributeDto = (ResponseAttributeDto) gen.getCurrentValue();
        if (response == null) {
            gen.writeNull();
            return;
        }
        if (responseAttributeDto.getContentType() == null) {
            gen.writeStartArray();
            for (BaseAttributeContentV2 content : response) {
                gen.writeObject(content);
            }
            gen.writeEndArray();
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (responseAttributeDto.getContentType().equals(AttributeContentType.SECRET)) {
            gen.writeStartArray();
            for (BaseAttributeContentV2 content : responseAttributeDto.getContent()) {
                SecretAttributeContentV2 secretAttributeContent = objectMapper.convertValue(content, SecretAttributeContentV2.class);
                secretAttributeContent.setData(null);
                gen.writeObject(secretAttributeContent);
            }
        } else if (responseAttributeDto.getContentType().equals(AttributeContentType.CREDENTIAL)) {
            gen.writeStartArray();
            for (BaseAttributeContentV2 credential : responseAttributeDto.getContent()) {
                CredentialAttributeContentV2 credentialAttributeContent = objectMapper.convertValue(credential, CredentialAttributeContentV2.class);
                List<DataAttributeV2> credentialAttributes = new ArrayList<>();
                CredentialAttributeContentData credentialDto = credentialAttributeContent.getData();

                // attributes can be null when serializing credential content not loaded with full credentials but as NameAndUuidDto
                if (credentialDto.getAttributes() != null) {
                    for (DataAttributeV2 credentialAttribute : credentialDto.getAttributes()) {
                        List<BaseAttributeContentV2> credentialAttributeContents = new ArrayList<>();
                        if (credentialAttribute.getContentType().equals(AttributeContentType.SECRET)) {
                            for (BaseAttributeContentV2 baseAttributeContent : credentialAttribute.getContent()) {
                                SecretAttributeContentV2 secretAttributeContent = objectMapper.convertValue(baseAttributeContent, SecretAttributeContentV2.class);
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
            for (BaseAttributeContentV2 content : response) {
                gen.writeObject(content);
            }
        }
        gen.writeEndArray();
    }
}

