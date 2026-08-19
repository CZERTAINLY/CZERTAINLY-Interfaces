package com.otilm.api.model.connector.discovery.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
}
