package com.otilm.api.model.connector.cryptography.v2.key;

import com.otilm.api.model.connector.cryptography.v2.token.TokenScopedRequestV2Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Body for {@code POST /v2/cryptographyProvider/keys/import/result}.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(name = "ImportKeyResultRequestV2Dto", description = """
        Identifies the import whose outcome is being resolved.

        The connector answers from its own record of the operation, so a caller that lost the original response can
        complete its bookkeeping or remove a key it cannot account for.
        """, additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
public class ImportKeyResultRequestV2Dto extends TokenScopedRequestV2Dto {

    @Schema(description = "Identifier of the key import operation to resolve",
            requiredMode = Schema.RequiredMode.REQUIRED, minLength = 1, maxLength = 256)
    @NotBlank(message = "keyImportId is required")
    @Size(min = 1, max = 256, message = "keyImportId must contain between 1 and 256 characters")
    private String keyImportId;
}
