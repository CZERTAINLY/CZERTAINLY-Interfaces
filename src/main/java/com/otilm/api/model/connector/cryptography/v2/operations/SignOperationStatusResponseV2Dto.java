package com.otilm.api.model.connector.cryptography.v2.operations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.connector.cryptography.v2.operations.data.SignatureResultItemV2Dto;
import com.otilm.api.model.connector.cryptography.v2.operations.validation.UniqueIdentifiers;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Response from {@code POST /v2/cryptographyProvider/operations/sign/status}.
 *
 * <p>
 * Status is reported per item and correlated by identifier. Polling is complete when every item has reached a terminal
 * status ({@code COMPLETED}, {@code FAILED}, or {@code CANCELLED}).
 * </p>
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Schema(name = "SignOperationStatusResponseV2Dto")
public class SignOperationStatusResponseV2Dto {

    @Schema(description = "Per-item results, one per item of the original sign request, correlated by identifier",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "items must contain at least one item")
    @UniqueIdentifiers
    private List<@NotNull(message = "items must not contain null entries") @Valid SignatureResultItemV2Dto> items;
}
