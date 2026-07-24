package com.otilm.api.model.connector.cryptography.v2.operations;

import com.otilm.api.model.connector.cryptography.v2.operations.data.CipherResponseDataV2Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Response from {@code POST /v2/cryptographyProvider/operations/encrypt}. Always synchronous.
 */
@Getter
@Setter
@ToString
@Schema(name = "EncryptDataResponseV2Dto")
public class EncryptDataResponseV2Dto {

    @Schema(description = "Encrypted data",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "encryptedData must contain at least one item")
    private List<@NotNull(message = "encryptedData must not contain null items")
    @Valid CipherResponseDataV2Dto> encryptedData;
}
