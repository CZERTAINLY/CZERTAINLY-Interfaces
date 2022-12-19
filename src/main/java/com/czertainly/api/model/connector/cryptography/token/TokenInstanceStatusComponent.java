package com.czertainly.api.model.connector.cryptography.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TokenInstanceStatusComponent {

    @Schema(
            description = "Token instance component status",
            required = true
    )
    private TokenInstanceStatus status;

    @Schema(
            description = "Token instance component details"
    )
    private Map<String, Object> details;

    public TokenInstanceStatus getStatus() {
        return status;
    }

    public void setStatus(TokenInstanceStatus status) {
        this.status = status;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("status", status)
                .append("details", details)
                .toString();
    }

}
