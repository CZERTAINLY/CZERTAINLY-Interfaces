package com.czertainly.api.model.core.compliance.v2;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ComplianceRuleListDto {
    @Schema(description = "Compliance rule UUID", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID uuid;

    @Schema(description = "Compliance rule name", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Description of the compliance rule", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {"Sample rule description"})
    private String description;

    @Schema(description = "UUID of the Compliance Provider", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID connectorUuid;

    @Schema(description = "Kind of the Compliance Provider", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String kind;

    @Schema(description = "UUID of the group to which the rule belongs to", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {"166b5cf52-63f2-11ec-90d6-0242ac120003"})
    private UUID groupUuid;

    @Schema(description = "Resource of the rule", requiredMode = Schema.RequiredMode.REQUIRED, examples = {Resource.Codes.CERTIFICATE})
    private Resource resource;

    @Schema(description = "Type of the resource object", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {"X.509"})
    private String type;

    @Schema(description = "Format of the resource object", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {"pkcs7"})
    private String format;

    @Schema(description = "Attributes of the rule", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<BaseAttribute<?>> attributes;

}
