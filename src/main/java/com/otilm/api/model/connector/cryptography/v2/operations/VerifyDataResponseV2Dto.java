package com.otilm.api.model.connector.cryptography.v2.operations;

import com.otilm.api.model.connector.cryptography.v2.operations.data.VerificationResponseDataV2Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Response from {@code POST /v2/cryptographyProvider/operations/verify}. Always synchronous.
 */
@Getter
@Setter
@ToString
@Schema(name = "VerifyDataResponseV2Dto")
public class VerifyDataResponseV2Dto {

    @Schema(description = "Verification results, correlated to the request items by identifier",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "verifications must contain at least one item")
    private List<@NotNull(message = "verifications must not contain null items")
    @Valid VerificationResponseDataV2Dto> verifications;
}
