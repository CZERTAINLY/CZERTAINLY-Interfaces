package com.czertainly.api.model.core.location;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.client.metadata.MetadataResponseDto;
import com.czertainly.api.model.core.certificate.CertificateStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CertificateInLocationDto {

    @Schema(
            description = "UUID of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String certificateUuid;

    @Schema(
            description = "Status of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CertificateStatus status;

    @Schema(
            description = "Common Name of the Subject DN field of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String commonName;

    @Schema(
            description = "Serial number in HEX of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String serialNumber;

    @Schema(
            description = "Metadata of the Certificate in Location"
    )
    private List<MetadataResponseDto> metadata;

    @Schema(
            description = "Applied push attributes"
    )
    private List<ResponseAttributeDto> pushAttributes;

    @Schema(
            description = "Applied issue attributes"
    )
    private List<ResponseAttributeDto> csrAttributes;

    @Schema(
            description = "If the Certificate in Location has associated private key",
            defaultValue = "false",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private boolean withKey;

    public String getCertificateUuid() {
        return certificateUuid;
    }

    public void setCertificateUuid(String certificateUuid) {
        this.certificateUuid = certificateUuid;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public List<MetadataResponseDto> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<MetadataResponseDto> metadata) {
        this.metadata = metadata;
    }

    public List<ResponseAttributeDto> getPushAttributes() {
        return pushAttributes;
    }

    public void setPushAttributes(List<ResponseAttributeDto> pushAttributes) {
        this.pushAttributes = pushAttributes;
    }

    public List<ResponseAttributeDto> getCsrAttributes() {
        return csrAttributes;
    }

    public void setCsrAttributes(List<ResponseAttributeDto> csrAttributes) {
        this.csrAttributes = csrAttributes;
    }

    public boolean isWithKey() {
        return withKey;
    }

    public void setWithKey(boolean withKey) {
        this.withKey = withKey;
    }

    public CertificateStatus getStatus() {
        return status;
    }

    public void setStatus(CertificateStatus status) {
        this.status = status;
    }
}
