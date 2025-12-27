package com.czertainly.api.model.common.attribute.common.callback;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestAttributeCallback {

    @Schema(description = "UUID of the Attribute")
    private String uuid;

    @Schema(description = "Name of the Attribute",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(
            description = "Map of path variables supported by the callback method"
    )
    private Map<String, Serializable> pathVariable;

    @Schema(
            description = "Map of the query parameters supported by the callback method"
    )
    private Map<String, Serializable> requestParameter;

    @Schema(
            description = "Request body for the callback method"
    )
    private Map<String, Serializable> body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Map<String, Serializable> getPathVariable() {
        return pathVariable;
    }

    public void setPathVariable(Map<String, Serializable> pathVariable) {
        this.pathVariable = pathVariable;
    }

    public Map<String, Serializable> getRequestParameter() {
        return requestParameter;
    }

    public void setRequestParameter(Map<String, Serializable> requestParameter) {
        this.requestParameter = requestParameter;
    }

    public Map<String, Serializable> getBody() {
        return body;
    }

    public void setBody(Map<String, Serializable> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("uuid", uuid)
                .append("pathVariable", pathVariable)
                .append("requestParameter", requestParameter)
                .append("body", body)
                .toString();
    }
}
