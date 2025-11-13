package com.czertainly.api.model.common.attribute.v3.content;

import com.czertainly.api.model.common.attribute.v2.content.data.SecretAttributeContentData;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        description = "Secret attribute content carrying secrets with defined protection level",
        type = "object")
public class SecretAttributeContentV3 extends BaseAttributeContentV3<SecretAttributeContentData> {

    @Schema(description = "Secret attribute content data", requiredMode = Schema.RequiredMode.REQUIRED)
    private SecretAttributeContentData data;

    public SecretAttributeContentV3(String reference, SecretAttributeContentData data) {
        super(reference, data);
        this.data = data;
    }

    public SecretAttributeContentV3() {
    }

    @Override
    public SecretAttributeContentData getData() {
        return data;
    }

    @Override
    public void setData(SecretAttributeContentData data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecretAttributeContentV3)) return false;
        SecretAttributeContentV3 that = (SecretAttributeContentV3) o;
        return Objects.equals(this.data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }
}
