package com.otilm.api.model.connector.cryptography.v2.token;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.otilm.api.model.client.attribute.RequestAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Token context used by token and token-profile operations.
 */
@Getter
@Setter
@ToString
@Schema(description = "Token context for token and token-profile operations.",
        additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
public class TokenScopedRequestV2Dto {

    @Schema(description = "Token attributes", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "tokenAttributes is required (may be empty list, but must be present)")
    private List<@NotNull(message = "tokenAttributes must not contain null entries") RequestAttribute> tokenAttributes;

    @JsonAnySetter
    @Schema(hidden = true)
    public void rejectUnknownProperty(String property, Object ignoredValue) {
        throw new IllegalArgumentException("Unsupported v2 cryptography request property: " + property);
    }
}
