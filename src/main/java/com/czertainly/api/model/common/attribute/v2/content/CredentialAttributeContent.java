package com.czertainly.api.model.common.attribute.v2.content;

import com.czertainly.api.model.common.attribute.v2.content.data.CredentialAttributeContentData;
import com.czertainly.api.model.core.credential.CredentialDto;
import io.swagger.v3.oas.annotations.media.Schema;

public class CredentialAttributeContent extends BaseAttributeContent<CredentialAttributeContentData> {

    @Schema(description = "Credential attribute content data", required = true)
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
