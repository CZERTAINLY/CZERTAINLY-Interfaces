package com.czertainly.api.model.common.attribute.v3.content.data;

import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.client.attribute.ResponseAttribute;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.common.content.data.AttributeContentData;
import com.czertainly.api.model.common.attribute.common.content.data.CodeBlockAttributeContentData;
import com.czertainly.api.model.core.auth.AttributeResource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceObjectContentData extends NameAndUuidDto implements Serializable, AttributeContentData {

    @Schema(description = "Resource contained in data", requiredMode = Schema.RequiredMode.REQUIRED)
    private AttributeResource resource;

    @Schema(description = "Base64 encoded content of the resource object", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String content;

    @Schema(description = "Attributes of the resource object", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<ResponseAttribute> attributes;

    @Override
    public void validate() throws ValidationException {
        if (resource == null) {
            throw new ValidationException("Resource must be provided in Resource Object Content Data");
        }

        if (uuid == null || uuid.isEmpty()) {
            throw new ValidationException("UUID must be provided in Resource Object Content Data");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceObjectContentData that)) return false;
        return Objects.equals(resource, that.resource) && Objects.equals(attributes, that.attributes) && Objects.equals(content, that.content) && Objects.equals(getUuid(), that.getUuid()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource, content, attributes, getUuid(), getName());
    }
}
