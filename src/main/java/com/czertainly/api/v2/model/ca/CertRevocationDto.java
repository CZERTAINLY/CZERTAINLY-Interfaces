package com.czertainly.api.v2.model.ca;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.ca.RevocationReason;
import com.czertainly.api.model.raprofile.RaProfileDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class with parameter to revoke any certificate.
 */
public class CertRevocationDto {

    @Schema(description = "Revocation reason",
            implementation = RevocationReason.class,
            required = true)
    private RevocationReason reason;

    @Schema(description = "Full data of RA profile",
            required = true)
    protected RaProfileDto raProfile;

    @Schema(description = "List of attributes needed for revoking certificate",
            required = true)
    private List<AttributeDefinition> attributes;

    public RevocationReason getReason() {
        return reason;
    }

    public void setReason(RevocationReason reason) {
        this.reason = reason;
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
                .append("reason", reason)
                .append("raProfile", raProfile)
                .append("attributes", attributes)
                .toString();
    }
}
