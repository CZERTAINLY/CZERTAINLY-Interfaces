package com.otilm.api.model.connector.cryptography.v2.key;

import com.otilm.api.model.client.attribute.RequestAttribute;
import com.otilm.api.model.client.cryptography.key.KeyRequestType;
import com.otilm.api.model.connector.common.v2.OperationExecutionMode;
import com.otilm.api.model.connector.cryptography.v2.TokenProfileScopedRequestV2Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Body for {@code POST /v2/cryptographyProvider/keys}.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(name = "CreateKeyRequestV2Dto", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
public class CreateKeyRequestV2Dto extends TokenProfileScopedRequestV2Dto {

    @Schema(description = "Type of key to create", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "keyRequestType is required")
    private KeyRequestType keyRequestType;

    @Schema(description = "Caller-selected execution mode. The connector must not switch modes implicitly.",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "executionMode is required")
    private OperationExecutionMode executionMode;

    @Schema(description = """
            Opaque identifier of one logical secret-key or key-pair creation operation. Use a new value for
            each intentional creation and reuse that value for retries of the same request. An equivalent
            retry may return the existing operation or result. Reuse with materially different creation data
            must be rejected with RESOURCE_ALREADY_EXISTS (HTTP 409).
            """, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "keyCreationId is required")
    @Size(max = 256, message = "keyCreationId must not exceed 256 characters")
    private String keyCreationId;

    @Schema(description = "Attributes to create the key", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "createKeyAttributes is required (may be empty list, but must be present)")
    private List<@NotNull(
            message = "createKeyAttributes must not contain null entries") RequestAttribute> createKeyAttributes;
}
