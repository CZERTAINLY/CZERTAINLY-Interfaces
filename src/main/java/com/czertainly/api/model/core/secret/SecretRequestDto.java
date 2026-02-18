package com.czertainly.api.model.core.secret;

import com.czertainly.api.model.client.attribute.RequestAttribute;
import com.czertainly.api.model.core.secret.content.SecretContentDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SecretRequestDto {

    @Schema(description = "Name of the secret", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Description of the secret", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "UUID of the vault profile where the secret is stored", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID sourceVaultProfileUuid;

    @Schema(description = "Content of the secret", requiredMode = Schema.RequiredMode.REQUIRED)
    private SecretContentDto secret;

    @Schema(description = "List of attributes associated with the secret", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<RequestAttribute> attributes;

    @Schema(description = "List of custom attributes associated with the secret", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttribute> customAttributes;
}
