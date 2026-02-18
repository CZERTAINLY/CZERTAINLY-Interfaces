package com.czertainly.api.model.core.secret.content;

import com.czertainly.api.model.core.secret.SecretType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BasicAuthSecretContentDto implements SecretContentDto {

    @Schema(description = "Username for Basic Authentication", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "Password for Basic Authentication", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "Type of the Secret content", requiredMode = Schema.RequiredMode.REQUIRED)
    private SecretType secretType;

    @Override
    public SecretType getSecretType() {
        return SecretType.BASIC_AUTH;
    }
}
