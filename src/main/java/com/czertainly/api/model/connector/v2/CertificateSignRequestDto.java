package com.czertainly.api.model.connector.v2;

import com.czertainly.api.model.commons.AttributeDefinition;
import com.czertainly.api.model.core.raprofile.RaProfileDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class representing a request to sign CSR
 */
public class CertificateSignRequestDto {

    @Schema(description = "Certificate sign request (PKCS#10) encoded as Base64 string",
            required = true)
    private String pkcs10;

    @Schema(description = "RA Profile information",
            required = true)
    private RaProfileDto raProfile;

    @Schema(description = "List of Attributes to issue Certificate",
            required = true)
    private List<AttributeDefinition> attributes;

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

    public List<AttributeDefinition> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDefinition> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("pkcs10", pkcs10)
                .append("raProfile", raProfile)
                .append("attributes", attributes)
                .toString();
    }
}
