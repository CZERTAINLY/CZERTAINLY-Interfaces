package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.common.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

public class RemoveCertificateRequestDto {

    @Schema(
            description = "Metadata of the Certificate",
            required = true
    )
    private Map<String, Object> certificateMetadata;

    @Schema(
            description = "List of Location Attributes",
            required = true
    )
    private List<RequestAttributeDto> locationAttributes;

    public Map<String, Object> getCertificateMetadata() {
        return certificateMetadata;
    }

    public void setCertificateMetadata(Map<String, Object> certificateMetadata) {
        this.certificateMetadata = certificateMetadata;
    }

    public List<RequestAttributeDto> getLocationAttributes() {
        return locationAttributes;
    }

    public void setLocationAttributes(List<RequestAttributeDto> locationAttributes) {
        this.locationAttributes = locationAttributes;
    }
}
