package com.czertainly.api.model.core.compliance.v2;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class ComplianceGroupListDto extends NameAndUuidDto {
    @Schema(description = "Description of the compliance group", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {"Sample group description"})
    private String description;

    @Schema(description = "UUID of the Compliance Provider", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID connectorUuid;

    @Schema(description = "Kind of the Compliance Provider", requiredMode = Schema.RequiredMode.REQUIRED)
    private String kind;

    @Schema(description = "Resource of the rule", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {Resource.Codes.CERTIFICATE})
    private Resource resource;

}
