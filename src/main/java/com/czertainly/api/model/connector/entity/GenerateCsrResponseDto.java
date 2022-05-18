package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.common.RequestAttributeDto;
import com.czertainly.api.model.core.certificate.CertificateType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class GenerateCsrResponseDto {

    @Schema(description = "Base64-encoded certificate signing request",
            required = true)
    private String csr;

    @Schema(description = "Type of the certificate expected to be issued",
            required = false)
    private CertificateType certificateType;

    @Schema(
            description = "List of Attributes to push Certificate",
            required = true
    )
    private List<RequestAttributeDto> pushAttributes;

    public String getCsr() {
        return csr;
    }

    public void setCsr(String csr) {
        this.csr = csr;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
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
                .append("csr", csr)
                .append("certificateType", certificateType)
                .append("pushAttributes", pushAttributes)
                .toString();
    }

}
