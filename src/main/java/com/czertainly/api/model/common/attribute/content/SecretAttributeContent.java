package com.czertainly.api.model.common.attribute.content;

import com.czertainly.api.model.common.attribute.content.data.SecretAttributeContentData;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SecretAttributeContent extends BaseAttributeContent<SecretAttributeContentData> {

    @Schema(description = "File Content", required = true)
    private SecretAttributeContentData data;

    public SecretAttributeContent(String reference, SecretAttributeContentData data) {
        super(reference, data);
        this.data = data;
    }
}
