package com.otilm.api.model.connector.cryptography.v2.key;

import com.otilm.api.model.common.attribute.common.MetadataAttribute;
import com.otilm.api.model.connector.cryptography.v2.TokenProfileScopedRequestV2Dto;
import com.otilm.api.model.connector.cryptography.v2.validation.ValidMetadataAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/** Body for polling or cancelling an asynchronous V2 key operation. */
@Getter
@Setter
@ToString(callSuper = true)
public class KeyOperationRequestV2Dto extends TokenProfileScopedRequestV2Dto {

    @Schema(description = "Connector-defined metadata returned in the original 202 Accepted response",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "operationMeta is required and must not be empty")
    private List<@NotNull @ValidMetadataAttribute MetadataAttribute> operationMeta;
}
