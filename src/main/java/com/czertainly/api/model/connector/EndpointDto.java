package com.czertainly.api.model.connector;

import com.czertainly.api.model.Identified;
import com.czertainly.api.model.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class EndpointDto extends NameAndUuidDto {


    @Schema(
            description = "Context of the Endpoint",
            example = "/v1",
            required = true
    )
    private String context;
    @Schema(
            description = "Method to be used for the Endpoint",
            example = "POST",
            required = true
    )
    private String method;
    @Schema(
            description = "True if the Endpoint is required for implementation",
            example = "true",
            required = true
    )
    private boolean required;

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
                .append("uuid", uuid)
                .append("name", name)
                .append("context", context)
                .append("method", method)
                .append("required", required)
                .toString();
    }
}
