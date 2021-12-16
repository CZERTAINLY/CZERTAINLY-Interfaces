package com.czertainly.api.model.connector;

import io.swagger.v3.oas.annotations.media.Schema;

public class ConnectDto {

    @Schema(description = "Function Group information of a connector",
            implementation = FunctionGroupDto.class,
            required = true)
    private FunctionGroupDto functionGroup;

    public FunctionGroupDto getFunctionGroup() {
        return functionGroup;
    }

    public void setFunctionGroup(FunctionGroupDto functionGroup) {
        this.functionGroup = functionGroup;
    }
}
