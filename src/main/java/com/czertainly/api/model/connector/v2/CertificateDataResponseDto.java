package com.czertainly.api.model.connector.v2;

import com.czertainly.api.model.core.certificate.CertificateType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Response containing signed certificate data
 */
public class CertificateDataResponseDto {

    @Schema(description = "Base64 encoded Certificate content",
            required = true)
    private String certificateData;

    @Schema(description = "UUID of Certificate",
            required = true)
    private String uuid;

    @Schema(
            description = "Metadata for the Certificate",
            required = true
    )
    private String meta;

    @Schema(description = "Type of the Certificate",
            defaultValue = "X509",
            required = false)
    private CertificateType certificateType;

    public String getCertificateData() {
        return certificateData;
    }

    public void setCertificateData(String certificateData) {
        this.certificateData = certificateData;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("certificateData", certificateData)
                .append("certificateUuid", uuid)
                .toString();
    }
}

