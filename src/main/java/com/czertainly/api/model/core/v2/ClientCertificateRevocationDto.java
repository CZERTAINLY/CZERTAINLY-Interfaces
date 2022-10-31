package com.czertainly.api.model.core.v2;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.core.authority.RevocationReason;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class with parameter to revoke any certificate.
 */
public class ClientCertificateRevocationDto {

    @Schema(description = "Reason for revocation",
            required = true)
    private RevocationReason reason;

    @Schema(description = "List of Attributes to revoke Certificate",
            required = true)
    private List<RequestAttributeDto> attributes;

    public RevocationReason getReason() {
        return reason;
    }

    public void setReason(RevocationReason reason) {
        this.reason = reason;
    }

    public List<RequestAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RequestAttributeDto> attributes) {
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
