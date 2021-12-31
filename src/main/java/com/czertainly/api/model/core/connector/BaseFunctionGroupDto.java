package com.czertainly.api.model.core.connector;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class BaseFunctionGroupDto {

    @Schema(description = "Enumerated code of functional group",
            required = true)
    protected FunctionGroupCode functionGroupCode;

    @Schema(description = "List of supported functional group kinds",
            example = "[\"SoftKeyStore\", \"Basic\", \"ApiKey\"]",
            required = true)
    protected List<String> kinds;

    @Schema(description = "List of end points related to functional group",
            required = true)
    protected List<EndpointDto> endPoints;

    public BaseFunctionGroupDto() {
        super();
    }

    public BaseFunctionGroupDto(List<String> kinds, FunctionGroupCode functionGroupCode, List<EndpointDto> endPoints) {
        super();
        this.kinds = kinds;
        this.functionGroupCode = functionGroupCode;
        this.endPoints = endPoints;
    }

    public List<String> getKinds() {
        return kinds;
    }

    public void setKinds(List<String> kinds) {
        this.kinds = kinds;
    }

    public FunctionGroupCode getFunctionGroupCode() {
        return functionGroupCode;
    }

    public void setFunctionGroupCode(FunctionGroupCode functionGroupCode) {
        this.functionGroupCode = functionGroupCode;
    }

    public List<EndpointDto> getEndPoints() {
        return endPoints;
    }

    public void setEndPoints(List<EndpointDto> endPoints) {
        this.endPoints = endPoints;
    }
}
