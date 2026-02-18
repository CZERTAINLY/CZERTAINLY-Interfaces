package com.czertainly.api.model.core.secret.content;

import com.czertainly.api.model.core.secret.SecretType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

@Data
public class KeyValueSecretContentDto implements SecretContentDto {

    @Schema(description = "Key-value pairs representing the content of the secret", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, Object> content;

    @Schema(description = "Type of the Secret content", requiredMode = Schema.RequiredMode.REQUIRED)
    private SecretType secretType;

    @Override
    public SecretType getSecretType() {
        return SecretType.KEY_VALUE;
    }
}
