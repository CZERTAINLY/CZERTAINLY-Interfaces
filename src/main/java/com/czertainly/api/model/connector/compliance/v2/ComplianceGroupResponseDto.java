package com.czertainly.api.model.connector.compliance.v2;

import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/*
List of groups information from the Compliance Provider.
 */
@Getter
@Setter
@ToString
public class ComplianceGroupResponseDto {
    @Schema(description = "UUID of the group", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"166b5cf52-63f2-11ec-90d6-0242ac120003"})
    private UUID uuid;

    @Schema(description = "Name of the group", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"RFC"})
    private String name;

    @Schema(description = "Description of the group", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {"Sample description of the group"})
    private String description;

    @Schema(description = "Resource of the group", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {Resource.Codes.CERTIFICATE})
    private Resource resource;

}
