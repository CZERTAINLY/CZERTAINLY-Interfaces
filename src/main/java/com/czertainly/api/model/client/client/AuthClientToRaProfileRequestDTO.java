package com.czertainly.api.model.client.client;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Class representing client to RA profile authorization request
 */
public class AuthClientToRaProfileRequestDTO {
	
	@Schema(
            description = "Client certificate to be authorized",
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

