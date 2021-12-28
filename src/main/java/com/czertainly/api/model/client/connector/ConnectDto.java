package com.czertainly.api.model.client.connector;

import com.czertainly.api.model.core.connectors.FunctionGroupDto;
import io.swagger.v3.oas.annotations.media.Schema;

public class ConnectDto {

    @Schema(description = "Function Group information of a connector",
            required = true)
    private FunctionGroupDto functionGroup;

    public FunctionGroupDto getFunctionGroup() {
        return functionGroup;
    }

    public void setFunctionGroup(FunctionGroupDto functionGroup) {
        this.functionGroup = functionGroup;
    }
}
