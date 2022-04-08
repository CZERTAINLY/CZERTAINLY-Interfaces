package com.czertainly.api.model.connector.v2;

import com.czertainly.api.model.common.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class representing a request to renew certificate
 */
public class CertificateRenewRequestDto {

    @Schema(description = "Certificate sign request (PKCS#10) encoded as Base64 string",
            required = true)
    private String pkcs10;

    @Schema(description = "List of RA Profiles attributes",
            required = true)
    private List<RequestAttributeDto> raProfileAttributes;

    @Schema(description = "Base64 Certificate content. (Certificate to be renewed)",
            required = true)
    private String certificate;

    @Schema(
            description = "Metadata for the Certificate",
            required = true
    )
    private String meta;

    public String getPkcs10() {
        return pkcs10;
    }

    public void setPkcs10(String pkcs10) {
        this.pkcs10 = pkcs10;
    }

    public List<RequestAttributeDto> getRaProfileAttributes() {
        return raProfileAttributes;
    }

    public void setRaProfileAttributes(List<RequestAttributeDto> raProfileAttributes) {
        this.raProfileAttributes = raProfileAttributes;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
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
                .append("pkcs10", pkcs10)
                .append("raProfile", raProfileAttributes)
                .toString();
    }
}
