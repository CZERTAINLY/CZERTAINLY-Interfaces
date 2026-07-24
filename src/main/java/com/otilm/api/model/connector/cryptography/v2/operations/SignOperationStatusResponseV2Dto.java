package com.otilm.api.model.connector.cryptography.v2.operations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.common.attribute.v2.MetadataAttributeV2;
import com.otilm.api.model.connector.cryptography.v2.operations.data.SignatureItemResultV2Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Response from {@code POST /v2/cryptographyProvider/operations/sign/status}.
 *
 * <p>Status is reported per item and correlated by identifier. Polling is complete when every item has reached
 * a terminal status; the batch has failed if any item has failed.</p>
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
    private List<@NotNull(message = "items must not contain null entries")
    @Valid SignatureItemResultV2Dto> items;

    @Schema(description = "Optional updated connector-defined signing operation metadata",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<MetadataAttributeV2> signOperationMeta;
}
