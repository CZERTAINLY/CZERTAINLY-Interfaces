package com.czertainly.api.model.common.attribute.v2.content.data;

import com.czertainly.api.model.core.logging.Sensitive;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public class SecretAttributeContentData {

    @Sensitive
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecretAttributeContentData)) return false;
        SecretAttributeContentData that = (SecretAttributeContentData) o;
        return Objects.equals(this.secret, that.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(secret);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("secret", secret)
                .append("protectionLevel", protectionLevel)
                .toString();
    }
}
