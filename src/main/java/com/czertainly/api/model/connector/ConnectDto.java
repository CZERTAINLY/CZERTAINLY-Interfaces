package com.czertainly.api.model.connector;

import java.util.List;

public class ConnectDto {

    private FunctionGroupDto functionGroup;
    private List<EndpointDto> endpoints;

    public FunctionGroupDto getFunctionGroup() {
        return functionGroup;
    }

    public void setFunctionGroup(FunctionGroupDto functionGroup) {
        this.functionGroup = functionGroup;
    }

    public List<EndpointDto> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<EndpointDto> endpoints) {
        this.endpoints = endpoints;
    }
}
