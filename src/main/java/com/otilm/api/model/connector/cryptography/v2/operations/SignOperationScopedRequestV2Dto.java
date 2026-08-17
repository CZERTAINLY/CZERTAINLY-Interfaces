package com.otilm.api.model.connector.cryptography.v2.operations;

import com.otilm.api.model.common.attribute.common.MetadataAttribute;
import com.otilm.api.model.connector.cryptography.v2.KeyScopedRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.validation.ValidMetadataAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Context for polling or cancelling an asynchronous sign operation. The metadata is the tracking handle returned in the
 * original {@code 202 Accepted} response.
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(name = "SignOperationScopedRequestV2Dto", additionalProperties = Schema.AdditionalPropertiesValue.FALSE)
public class SignOperationScopedRequestV2Dto extends KeyScopedRequestV2Dto {

    @Schema(description = "Connector-defined signing operation metadata returned in the original sign data "
            + "202 Accepted response", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "operationMeta is required and must not be empty")
    private List<@NotNull @ValidMetadataAttribute MetadataAttribute> operationMeta;
}
