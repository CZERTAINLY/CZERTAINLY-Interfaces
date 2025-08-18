package com.czertainly.api.model.core.compliance.v2;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class BaseComplianceRuleDto {

    @Hidden
    private UUID connectorUuid;

    @Hidden
    private String connectorName;

    @Hidden
    private String kind;

}
