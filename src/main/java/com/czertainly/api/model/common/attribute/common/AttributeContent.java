package com.czertainly.api.model.common.attribute.common;

import com.czertainly.api.config.serializer.AttributeContentDeserializer;
import com.czertainly.api.model.common.attribute.v2.content.AttributeContentType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;


@JsonDeserialize(using = AttributeContentDeserializer.class)
public abstract class AttributeContent implements Serializable {

    public abstract <T> T getData();
    public abstract String getReference();
    public abstract AttributeContentType getContentType();
}
