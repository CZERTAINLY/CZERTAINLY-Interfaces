package com.otilm.api.model.connector.discovery.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Describes what a discovery connector supports for a single resource type.
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscoverySupportedResourceDto {

    // No @Schema description on purpose: Resource is a platform-wide schema component, and OpenAPI 3.0 cannot
    // carry a description beside a $ref — swagger-core hoists a referencing field's description onto the
    // referenced component, rewriting it for every other API that references Resource
    // (discoveryDoesNotRewriteThePlatformWideResourceComponent pins this).
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "resource is required")
    private Resource resource;

    @Schema(description = "Per-resource capabilities this resource supports. Enumerates only "
            + "capabilities that can differ from one resource to another; a capability listed "
            + "here is valid only if the connector also advertises the interface-level feature "
            + "flag it maps to. A null value means every such capability the connector "
            + "advertises applies to this resource; an empty list means none apply. These are "
            + "distinct and must not be normalized to one another. This list must not be used "
            + "to decide whether the connector streams: streaming is a property of the whole "
            + "discovery interface, advertised once by the discoveryStreaming feature flag, " + "never per resource.",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<DiscoveryResourceCapability> capabilities;
}
