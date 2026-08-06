package com.otilm.api.model.connector.discovery.v2;

import com.otilm.api.model.core.auth.Resource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Payload for a discovered {@code certificates} item.
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoveredCertificateDto implements DiscoveredItemPayloadDto {

    @Schema(description = "Resource type of this payload; the OpenAPI discriminator selecting this "
                  + "concrete payload shape",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "resource is required")
    private Resource resource = Resource.CERTIFICATE;

    @Schema(description = "Base64-encoded certificate data (DER)",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "certificateData is required")
    private String certificateData;
}
