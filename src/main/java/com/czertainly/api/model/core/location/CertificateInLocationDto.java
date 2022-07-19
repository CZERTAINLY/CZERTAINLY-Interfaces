package com.czertainly.api.model.core.location;

import com.czertainly.api.model.common.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

public class CertificateInLocationDto {

    @Schema(
            description = "UUID of the Certificate",
            required = true
    )
    private String certificateUuid;

    @Schema(
            description = "Common Name of the Subject DN field of the Certificate",
            required = true
    )
    private String commonName;

    @Schema(
            description = "Serial number in HEX of the Certificate",
            required = true
    )
    private String serialNumber;

    @Schema(
            description = "Metadata of the Certificate in Location",
            required = true
    )
    private Map<String, Object> metadata;

    @Schema(
            description = "Applied push attributes"
    )
    private List<RequestAttributeDto> pushAttributes;

    @Schema(
            description = "Applied issue attributes"
    )
    private List<RequestAttributeDto> csrAttributes;

    @Schema(
            description = "If the Certificate in Location has associated private key",
            defaultValue = "false",
            required = false
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

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public List<RequestAttributeDto> getPushAttributes() {
        return pushAttributes;
    }

    public void setPushAttributes(List<RequestAttributeDto> pushAttributes) {
        this.pushAttributes = pushAttributes;
    }

    public List<RequestAttributeDto> getCsrAttributes() {
        return csrAttributes;
    }

    public void setCsrAttributes(List<RequestAttributeDto> csrAttributes) {
        this.csrAttributes = csrAttributes;
    }

    public boolean isWithKey() {
        return withKey;
    }

    public void setWithKey(boolean withKey) {
        this.withKey = withKey;
    }
}
