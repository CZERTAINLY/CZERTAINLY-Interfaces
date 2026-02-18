package com.czertainly.api.model.core.secret.content;

import com.czertainly.api.model.core.secret.SecretType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class KeyStoreSecretContentDto implements SecretContentDto {

    @Schema(description = "Base64 encoded content of the Key Store", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "Password for the Key Store", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "Type of the Key Store (e.g., JKS, PKCS12)", requiredMode = Schema.RequiredMode.REQUIRED)
    private KeyStoreType keyStoreType;

    @Schema(description = "Type of the Secret content", requiredMode = Schema.RequiredMode.REQUIRED)
    private SecretType secretType;

    @Override
    public SecretType getSecretType() {
        return SecretType.KEY_STORE;
    }

}
