package com.czertainly.api.model.connector.v2;

import com.czertainly.api.model.core.raprofile.RaProfileDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class representing a request to renew certificate
 */
public class CertificateRenewRequestDto {

    @Schema(description = "Certificate sign request (PKCS#10) encoded as Base64 string",
            required = true)
    private String pkcs10;

    @Schema(description = "RA Profile information",
            required = true)
    private RaProfileDto raProfile;

    public String getPkcs10() {
        return pkcs10;
    }

    public void setPkcs10(String pkcs10) {
        this.pkcs10 = pkcs10;
    }

    public RaProfileDto getRaProfile() {
        return raProfile;
    }

    public void setRaProfile(RaProfileDto raProfile) {
        this.raProfile = raProfile;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("pkcs10", pkcs10)
                .append("raProfile", raProfile)
                .toString();
    }
}
