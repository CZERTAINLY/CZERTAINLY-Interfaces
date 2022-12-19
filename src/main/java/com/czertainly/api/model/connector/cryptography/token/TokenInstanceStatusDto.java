package com.czertainly.api.model.connector.cryptography.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TokenInstanceStatusDto {

    @Schema(description = "Token instance status",
            required = true)
    private TokenInstanceStatus status;

    @Schema(description = "Components of the Token instance status")
    private Map<String, TokenInstanceStatusComponent> components;

    public TokenInstanceStatus getStatus() {
        return status;
    }

    public void setStatus(TokenInstanceStatus status) {
        this.status = status;
    }

    public Map<String, TokenInstanceStatusComponent> getComponents() {
        return components;
    }

    public void setComponents(Map<String, TokenInstanceStatusComponent> components) {
        this.components = components;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("status", status)
                .append("components", components)
                .toString();
    }

}
