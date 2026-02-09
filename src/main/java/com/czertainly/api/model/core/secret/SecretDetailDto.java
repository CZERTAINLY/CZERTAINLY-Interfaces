package com.czertainly.api.model.core.secret;

import com.czertainly.api.model.client.attribute.ResponseAttribute;
import com.czertainly.api.model.client.metadata.MetadataResponseDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SecretDetailDto extends SecretDto {

    @Schema(description = "List of vault profiles where the secret is stored", requiredMode = Schema.RequiredMode.REQUIRED)
    List<NameAndUuidDto> syncVaultProfiles;

    @Schema(description = "Date and time when the secret was created", requiredMode = Schema.RequiredMode.REQUIRED)
    private OffsetDateTime createdAt;

    @Schema(description = "Date and time when the secret was last updated", requiredMode = Schema.RequiredMode.REQUIRED)
    private OffsetDateTime updatedAt;

    @Schema(description = "List of attributes associated with the secret", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<ResponseAttribute> attributes;

    @Schema(description = "List of custom attributes associated with the secret", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<ResponseAttribute> customAttributes;

    @Schema(description = "List of metadata associated with the secret", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<MetadataResponseDto> metadata;

}
