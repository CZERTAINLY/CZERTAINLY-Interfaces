package com.czertainly.api.core.v2.model;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.ClientAttributeDefinition;
import com.czertainly.api.model.ca.RevocationReason;
import com.czertainly.api.model.raprofile.RaProfileDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class with parameter to revoke any certificate.
 */
public class ClientCertificateRevocationDto {

    @Schema(description = "Reason for revocation",
            implementation = RevocationReason.class,
            required = true)
    private RevocationReason reason;

    @Schema(description = "List of Attributes to revoke Certificate",
            required = true)
    private List<ClientAttributeDefinition> attributes;

    public RevocationReason getReason() {
        return reason;
    }

    public void setReason(RevocationReason reason) {
        this.reason = reason;
    }

    public List<ClientAttributeDefinition> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ClientAttributeDefinition> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("reason", reason)
                .append("attributes", attributes)
                .toString();
    }
}
