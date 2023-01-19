package com.czertainly.api.model.client.connector;

import com.czertainly.api.model.core.connector.FunctionGroupDto;
import io.swagger.v3.oas.annotations.media.Schema;

public class ConnectDto {

    @Schema(description = "Function Group information of a connector",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private FunctionGroupDto functionGroup;

    public FunctionGroupDto getFunctionGroup() {
        return functionGroup;
    }

    public void setFunctionGroup(FunctionGroupDto functionGroup) {
        this.functionGroup = functionGroup;
    }
}
