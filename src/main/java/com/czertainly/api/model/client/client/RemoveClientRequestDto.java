package com.czertainly.api.model.client.client;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class representing client removal request
 */
public class RemoveClientRequestDto {
	
	@Schema(
            description = "Base64 Content of the client certificate",
            required = true
    )
    private String clientCertificate;

    public String getClientCertificate() {
        return clientCertificate;
    }

    public void setClientCertificate(String clientCertificate) {
        this.clientCertificate = clientCertificate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("clientCertificate", clientCertificate)
                .toString();
    }
}
