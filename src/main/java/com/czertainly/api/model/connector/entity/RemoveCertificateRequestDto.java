package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.attribute.v2.InfoAttribute;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class RemoveCertificateRequestDto {

    @Schema(
            description = "Metadata of the Certificate",
            required = true
    )
    private List<InfoAttribute> certificateMetadata;

    @Schema(
            description = "List of Location Attributes",
            required = true
    )
    private List<RequestAttributeDto> locationAttributes;

    public List<InfoAttribute> getCertificateMetadata() {
        return certificateMetadata;
    }

    public void setCertificateMetadata(List<InfoAttribute> certificateMetadata) {
        this.certificateMetadata = certificateMetadata;
    }

    public List<RequestAttributeDto> getLocationAttributes() {
        return locationAttributes;
    }

    public void setLocationAttributes(List<RequestAttributeDto> locationAttributes) {
        this.locationAttributes = locationAttributes;
    }
}
