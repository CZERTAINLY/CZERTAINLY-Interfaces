package com.czertainly.api.model.connector.entity;

import com.czertainly.api.model.connector.v2.CertificateDataResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

public class LocationDetailDto {

    @Schema(
            description = "List of Certificates in the Location",
            required = true
    )
    private List<CertificateDataResponseDto> certificates;

    @Schema(
            description = "Location metadata",
            required = false
    )
    private Map<String, Object> metadata;

    @Schema(
            description = "Support for multiple Certificates in the Location",
            defaultValue = "false",
            required = true
    )
    private boolean multipleEntries;

    @Schema(
            description = "Support for key pair management in the Location",
            defaultValue = "false",
            required = true
    )
    private boolean supportKeyManagement;
}
