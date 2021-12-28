package com.czertainly.api.model.client.client;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class representing client to RA profile deauthorization request
 */
public class DeauthClientToRaProfileRequestDto {
	
	@Schema(
            description = "Client Certificate",
            required = true
    )
    private String clientCertificate;
	
	@Schema(
            description = "Name of the RA Profile",
            required = true
    )
    private String raProfileName;

    public String getClientCertificate() {
        return clientCertificate;
    }

    public void setClientCertificate(String clientCertificate) {
        this.clientCertificate = clientCertificate;
    }

    public String getRaProfileName() {
        return raProfileName;
    }

    public void setRaProfileName(String raProfileName) {
        this.raProfileName = raProfileName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("clientCertificate", clientCertificate)
                .append("raProfileName", raProfileName)
                .toString();
    }
}
