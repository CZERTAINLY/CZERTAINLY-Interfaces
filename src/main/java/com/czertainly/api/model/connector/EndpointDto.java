package com.czertainly.api.model.connector;

import com.czertainly.api.model.Identified;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class EndpointDto implements Identified {

    @Schema(
            description = "ID of the endpoint",
            example = "1",
            required = true
    )
    private Long id;

    @Schema(
            description = "UUID of the endpoint",
            example = "204a57f6-2ed3-45b6-bf09-af8b8c900e33",
            required = true
    )
    private String uuid;

    @Schema(
            description = "Name of the endpoint",
            example = "listSupportedFunctions",
            required = true
    )
    private String name;
    @Schema(
            description = "Context of the endpoint",
            example = "/v1",
            required = true
    )
    private String context;
    @Schema(
            description = "Method to be used for the endpoint",
            example = "POST",
            required = true
    )
    private String method;
    @Schema(
            description = "True if the endpoint is required for implementation",
            example = "true",
            required = true
    )
    private boolean required;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("uuid", uuid)
                .append("name", name)
                .append("context", context)
                .append("method", method)
                .append("required", required)
                .toString();
    }
}
