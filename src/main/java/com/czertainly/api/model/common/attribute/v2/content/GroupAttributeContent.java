package com.czertainly.api.model.common.attribute.v2.content;

import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import io.swagger.v3.oas.annotations.media.Schema;

public class GroupAttributeContent extends BaseAttributeContent<DataAttribute> {

    @Schema(description = "String attribute value")
    private DataAttribute data;

    public GroupAttributeContent() {
    }

    public GroupAttributeContent(DataAttribute data) {
        this.data = data;
    }

    public DataAttribute getData() {
        return data;
    }

    public void setData(DataAttribute data) {
        this.data = data;
    }
}
