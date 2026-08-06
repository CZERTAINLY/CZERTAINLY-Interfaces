package com.otilm.api.model.connector.discovery.v2;

import com.otilm.api.model.core.auth.Resource;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Describes what a discovery connector supports for a single resource type.
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscoverySupportedResourceDto {

    @Schema(description = "Resource type this entry describes", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "resource is required")
    private Resource resource;

    @Schema(description = "Capabilities this resource supports. A null value means all interface-level "
                  + "feature flags apply to this resource; an empty list means none apply. These are "
                  + "distinct and must not be normalized to one another.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<DiscoveryResourceCapability> capabilities;
}
