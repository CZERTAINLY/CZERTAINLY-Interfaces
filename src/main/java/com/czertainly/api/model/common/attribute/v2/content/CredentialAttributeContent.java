package com.czertainly.api.model.common.attribute.v2.content;

import com.czertainly.api.model.common.attribute.v2.content.data.CredentialAttributeContentData;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Credential attribute content carrying information about credential to use",
        type = "object")
public class CredentialAttributeContent extends BaseAttributeContent<CredentialAttributeContentData> {

    @Schema(description = "Credential attribute content data", requiredMode = Schema.RequiredMode.REQUIRED)
    private CredentialAttributeContentData data;

    public CredentialAttributeContent() {
    }

    public CredentialAttributeContent(CredentialAttributeContentData data) {
        this.data = data;
    }

    public CredentialAttributeContent(String reference, CredentialAttributeContentData data) {
        super(reference);
        this.data = data;
    }

    public CredentialAttributeContentData getData() {
        return data;
    }

    public void setData(CredentialAttributeContentData data) {
        this.data = data;
    }


}
