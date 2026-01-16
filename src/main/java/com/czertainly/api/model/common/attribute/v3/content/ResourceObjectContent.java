package com.czertainly.api.model.common.attribute.v3.content;

import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.v3.content.data.ResourceObjectContentData;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(
        description = "Resource object attribute content carrying resource object data",
        type = "object")
public class ResourceObjectContent extends BaseAttributeContentV3<ResourceObjectContentData> {

    @Schema(description = "Resource Object content data", requiredMode = Schema.RequiredMode.REQUIRED)
    private ResourceObjectContentData data;

    @Override
    public ResourceObjectContentData getData() {
        return data;
    }

    @Override
    public void setData(ResourceObjectContentData data) {
        this.data = data;
    }

    public ResourceObjectContent(String reference, ResourceObjectContentData data) {
        setContentType(AttributeContentType.RESOURCE);
        this.data = data;
        this.setReference(reference);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResourceObjectContent that)) return false;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
