package com.czertainly.api.model.core.authority;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class representing a request to sign CSR
 */
public class CertificateSignRequestDto {

    @Schema(description = "End Entity password",
            required = true)
    private String password;

    @Schema(description = "Certificate sign request (PKCS#10) encoded as Base64 string",
            required = true)
    private String pkcs10;

    @Schema(description = "End Entity username",
            required = true)
    private String username;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPkcs10() {
        return pkcs10;
    }

    public void setPkcs10(String pkcs10) {
        this.pkcs10 = pkcs10;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("password", "masked")
                .append("pkcs10", pkcs10)
                .append("username", username)
                .toString();
    }
}
