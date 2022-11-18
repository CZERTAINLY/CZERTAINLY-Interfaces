package com.czertainly.api.model.common.attribute.v2.content;

import com.czertainly.api.model.common.attribute.v2.content.data.SecretAttributeContentData;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SecretAttributeContent extends BaseAttributeContent<SecretAttributeContentData> {

    @Schema(description = "Secret attribute content data", required = true)
    private SecretAttributeContentData data;

    public SecretAttributeContent(String reference, SecretAttributeContentData data) {
        super(reference, data);
        this.data = data;
    }

    public SecretAttributeContent() {
    }

    @Override
    public SecretAttributeContentData getData() {
        return data;
    }

    @Override
    public void setData(SecretAttributeContentData data) {
        this.data = data;
    }
}
