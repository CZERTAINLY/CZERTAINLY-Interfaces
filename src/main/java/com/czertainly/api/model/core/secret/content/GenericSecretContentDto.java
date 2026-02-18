package com.czertainly.api.model.core.secret.content;

import com.czertainly.api.model.core.secret.SecretType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GenericSecretContentDto implements SecretContentDto {

    @Schema(description = "Base54 encoded content of the generic secret", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "Type of the Secret content", requiredMode = Schema.RequiredMode.REQUIRED)
    private SecretType secretType;

    @Override
    public SecretType getSecretType() {
        return SecretType.GENERIC;
    }
}
