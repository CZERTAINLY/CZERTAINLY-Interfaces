package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.core.certificate.CertificateType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class PushCertificateRequestDto {

    @Schema(
            description = "Base64-encoded Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String certificate;

    @Schema(description = "Type of the Certificate",
            defaultValue = "X509",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private CertificateType certificateType;

    @Schema(
            description = "List of Location Attributes",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> locationAttributes;

    @Schema(
            description = "List of Attributes to push Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> pushAttributes;

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    public List<RequestAttributeDto> getLocationAttributes() {
        return locationAttributes;
    }

    public void setLocationAttributes(List<RequestAttributeDto> locationAttributes) {
        this.locationAttributes = locationAttributes;
    }

    public List<RequestAttributeDto> getPushAttributes() {
        return pushAttributes;
    }

    public void setPushAttributes(List<RequestAttributeDto> pushAttributes) {
        this.pushAttributes = pushAttributes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("certificate", certificate)
                .append("certificateType", certificateType)
                .append("locationAttributes", locationAttributes)
                .append("pushAttributes", pushAttributes)
                .toString();
    }
}
