package com.czertainly.api.model.connector.compliance.v2;

import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/*
Contains the list of parameters required for creating a new compliance check request.
This object should only be used by the core and connector and not by the client.
When a new compliance check is to be initiated, Core should use this class to frame the
request for the connector. And the connector should accept only this object as input for
any incoming compliance check request
 */
@Getter
@Setter
@ToString
public class ComplianceRequestDto {

    @Schema(description = "Resource of rules to be checked", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {Resource.Codes.CERTIFICATE})
    private Resource resource;

    @Schema(description = "Type of the resource object that is sent to compliance check", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {"X.509"})
    private String type;

    @Schema(description = "Format of the resource object data that are sent to compliance check", requiredMode = Schema.RequiredMode.NOT_REQUIRED, examples = {"pkcs7"})
    private String format;

    @Schema(description = "Base64 encoded content of resource object", requiredMode = Schema.RequiredMode.REQUIRED)
    private String data;

    @Schema(description = "List of UUIDs of Compliance rules")
    private List<ComplianceRuleRequestDto> rules;

}
