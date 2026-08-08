package com.otilm.api.model.connector.discovery.v2;

import com.otilm.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Body for the discovery v2 /initiate call: starts a new discovery run for the given
 * resource types.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class DiscoveryInitiateRequestDto extends DiscoveryV2ScopedRequestDto {

    @Schema(description = "Resource types to discover in this run",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "resources is required (must contain at least one resource type)")
    private List<@NotNull(message = "resources must not contain a null resource type") Resource> resources;
}
