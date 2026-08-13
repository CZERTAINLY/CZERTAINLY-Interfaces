package com.otilm.api.model.connector.cryptography.v2.token;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Response from {@code POST /v2/cryptographyProvider/tokens/status}.
 */
@Getter
@Setter
@ToString
@Schema(name = "TokenStatusResponseV2Dto")
public class TokenStatusResponseV2Dto {

    @Schema(description = "Token status", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "status is required")
    private TokenStatusV2 status;

    @Schema(description = "Optional provider-supplied detail explaining the token status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String detail;

}
