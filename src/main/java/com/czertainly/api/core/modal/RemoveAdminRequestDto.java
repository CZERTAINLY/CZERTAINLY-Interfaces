package com.czertainly.api.core.modal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class representing administrator removal request
 */
public class RemoveAdminRequestDto {
	
	@Schema(
            description = "Base64 Content of the admin certificate",
            required = true
    )
    private String adminCertificate;

    public String getAdminCertificate() {
        return adminCertificate;
    }

    public void setAdminCertificate(String adminCertificate) {
        this.adminCertificate = adminCertificate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("adminCertificate", adminCertificate)
                .toString();
    }
}
