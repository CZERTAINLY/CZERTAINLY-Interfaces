package com.czertainly.api.model.core.rules;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RuleDto extends NameAndUuidDto {


    @Schema(
            description = "UUID of the Compliance Connector"
    )
    private String connector_uuid;

    @Schema(
            description = "Description of the Rule"
    )
    private String description;


    @Schema(
            description = "Resource associated with the Rule",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Resource resource;


    @Schema(
            description = "Type of the Resource associated with the Rule"
    )
    private String resourceType;


    @Schema(
            description = "Format of the Resource associated with the Rule"
    )
    private String resourceFormat;

    @Schema(
            description = "Attributes of the Rule",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<ResponseAttributeDto> attributes;


}
