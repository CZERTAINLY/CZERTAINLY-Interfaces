package com.czertainly.api.model.connector.compliance.v2;

import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
List of rules information from the Compliance Provider. The attributes of the rules is used
to request for additional information for the rule.
 */
@Getter
@Setter
@ToString
public class ComplianceRuleResponseDto {
    @Schema(description = "UUID of the rule", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"166b5cf52-63f2-11ec-90d6-0242ac120003"})
    private UUID uuid;

    @Schema(description = "UUID of the group to which the rule belongs to", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {"166b5cf52-63f2-11ec-90d6-0242ac120003"})
    private UUID groupUuid;

    @Schema(description = "Name of the rule", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"Rule1"})
    private String name;

    @Schema(description = "Description of the rule", examples = {"Sample rule description"})
    private String description;

    @Schema(description = "Resource of the rule", requiredMode = Schema.RequiredMode.REQUIRED, examples = {Resource.Codes.CERTIFICATE})
    private Resource resource;

    @Schema(description = "Type of the resource object to which this rule can be applied", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {"X.509"})
    private String type;

    @Schema(description = "Format of the resource object data that are sent to compliance check", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {"pkcs7"})
    private String format;

    @Schema(description = "Rule attributes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<BaseAttribute> attributes = new ArrayList<>();
}
