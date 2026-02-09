package com.czertainly.api.model.core.secret.content;

import com.czertainly.api.model.core.secret.SecretType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ApiKeySecretContentDto implements SecretContentDto {

    @Schema(description = "Base64 encoded content of the API Key", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Override
    public SecretType getSecretType() {
        return SecretType.API_KEY;
    }
}
