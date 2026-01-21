package com.czertainly.api.model.common.attribute.v3.content;

import com.czertainly.api.model.common.attribute.common.content.AttributeContentType;
import com.czertainly.api.model.common.attribute.common.content.data.CodeBlockAttributeContentData;
import com.czertainly.core.util.AttributeDefinitionUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(
        description = "Integer attribute content for integer numbers",
        type = "object")
public class IntegerAttributeContentV3 extends BaseAttributeContentV3<Integer> {

    @Schema(description = "Integer attribute value", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer data;

    public IntegerAttributeContentV3() {
    }

    public IntegerAttributeContentV3(Integer data) {
        this.data = data;
        setContentType(AttributeContentType.INTEGER);
    }

    public IntegerAttributeContentV3(String reference, Integer data) {
        super(reference);
        this.data = data;
        setContentType(AttributeContentType.INTEGER);
    }

    @Override
    public Integer getData() {
        return data;
    }

    @Override
    public void setData(Integer data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerAttributeContentV3 that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), data);
    }

    @Override
    public Integer getDataFromDecrypted(String decrypted) {
        return Integer.valueOf(decrypted);
    }
}
