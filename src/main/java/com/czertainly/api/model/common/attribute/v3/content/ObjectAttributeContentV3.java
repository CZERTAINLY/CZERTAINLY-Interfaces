package com.czertainly.api.model.common.attribute.v3.content;

import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.common.content.data.CodeBlockAttributeContentData;
import com.czertainly.core.util.AttributeDefinitionUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Schema(
        description = "Object attribute content for data with custom structure",
        type = "object")
    public class ObjectAttributeContentV3 extends BaseAttributeContentV3<Serializable> {

    @Schema(description = "Object attribute content data", requiredMode = Schema.RequiredMode.REQUIRED)
    private Serializable data;

    public ObjectAttributeContentV3() {
        setContentType(AttributeContentType.OBJECT);
    }

    public ObjectAttributeContentV3(Serializable data) {
        this.data = data;
        setContentType(AttributeContentType.OBJECT);
    }

    public ObjectAttributeContentV3(String reference, Serializable data) {
        super(reference);
        this.data = data;
        setContentType(AttributeContentType.OBJECT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectAttributeContentV3 that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }

    @Override
    public Serializable getDataFromDecrypted(String decrypted) {
        return decrypted;
    }
}
