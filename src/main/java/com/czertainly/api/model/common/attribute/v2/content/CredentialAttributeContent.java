package com.czertainly.api.model.common.attribute.v2.content;

import com.czertainly.api.model.core.credential.CredentialDto;
import io.swagger.v3.oas.annotations.media.Schema;

public class CredentialAttributeContent extends BaseAttributeContent<CredentialDto> {

    @Schema(description = "Text attribute value")
    private CredentialDto data;

    public CredentialAttributeContent() {
    }

    public CredentialAttributeContent(CredentialDto data) {
        this.data = data;
    }

    public CredentialAttributeContent(String reference, CredentialDto data) {
        super(reference);
        this.data = data;
    }

    public CredentialDto getData() {
        return data;
    }

    public void setData(CredentialDto data) {
        this.data = data;
    }
}
