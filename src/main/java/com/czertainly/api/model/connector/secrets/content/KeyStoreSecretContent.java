package com.czertainly.api.model.connector.secrets.content;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(
        title = "KeyStoreSecretContent",
        description = "Secret representing Key Store"
)
public class KeyStoreSecretContent extends SecretContent {

    @Schema(description = "Key Store type", requiredMode = Schema.RequiredMode.REQUIRED)
    private KeyStoreType keyStoreType;

    @Schema(description = "BASE64 encoded content of key store", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "Password for key store", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
