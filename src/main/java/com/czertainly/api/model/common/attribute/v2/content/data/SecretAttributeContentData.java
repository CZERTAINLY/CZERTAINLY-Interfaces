package com.czertainly.api.model.common.attribute.v2.content.data;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SecretAttributeContentData {

    @Schema(description = "Secret attribute data")
    private String secret;

    @Schema(description = "Level of protection of the data")
    private ProtectionLevel protectionLevel;

    public SecretAttributeContentData() {
    }

    public SecretAttributeContentData(String secret) {
        this.secret = secret;
    }

    public SecretAttributeContentData(String secret, ProtectionLevel protectionLevel) {
        this.secret = secret;
        this.protectionLevel = protectionLevel;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public ProtectionLevel getProtectionLevel() {
        return protectionLevel;
    }

    public void setProtectionLevel(ProtectionLevel protectionLevel) {
        this.protectionLevel = protectionLevel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("secret", secret)
                .append("protectionLevel", protectionLevel)
                .toString();
    }
}
