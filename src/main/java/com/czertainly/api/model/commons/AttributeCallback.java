package com.czertainly.api.model.commons;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class AttributeCallback {

    @Schema(
            description = "Context part of callback URL",
            required = true
    )
    private String callbackContext;

    @Schema(
            description = "HTTP method of the callback",
            required = true
    )
    private String callbackMethod;

    @Schema(
            description = "Mappings for the callback method",
            required = true
    )
    private Set<AttributeCallbackMapping> mappings;

    @Schema(
            description = "Map of path variables supported by the callback method"
    )
    private Map<String, Serializable> pathVariables;

    @Schema(
            description = "Map of the query parameters supported by the callback method"
    )
    private Map<String, Serializable> queryParameters;

    @Schema(
            description = "Request body for the callback method"
    )
    private Map<String, Serializable> requestBody;

    public String getCallbackContext() {
        return callbackContext;
    }

    public void setCallbackContext(String callbackContext) {
        this.callbackContext = callbackContext;
    }

    public String getCallbackMethod() {
        return callbackMethod;
    }

    public void setCallbackMethod(String callbackMethod) {
        this.callbackMethod = callbackMethod;
    }

    public Set<AttributeCallbackMapping> getMappings() {
        return mappings;
    }

    public void setMappings(Set<AttributeCallbackMapping> mappings) {
        this.mappings = mappings;
    }

    public Map<String, Serializable> getPathVariables() {
        return pathVariables;
    }

    public void setPathVariables(Map<String, Serializable> pathVariables) {
        this.pathVariables = pathVariables;
    }

    public Map<String, Serializable> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(Map<String, Serializable> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public Map<String, Serializable> getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Map<String, Serializable> requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("callbackContext", callbackContext)
                .append("callbackMethod", callbackMethod)
                .append("mappings", mappings)
                .append("pathVariables", pathVariables)
                .append("queryParameters", queryParameters)
                .append("requestBody", requestBody)
                .toString();
    }
}
