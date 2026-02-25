package com.czertainly.api.model.connector.secrets.content;

import com.czertainly.api.model.connector.secrets.SecretType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(
        title = "KeyStoreSecretContent",
        description = "Secret representing Key Store"
)
public class KeyStoreSecretContent extends SecretContent {

    @Schema(description = "Key Store type", requiredMode = Schema.RequiredMode.REQUIRED)
    private KeyStoreType keyStoreType;

    @ToString.Exclude
    @Schema(description = "BASE64 encoded content of key store", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @ToString.Exclude
    @Schema(description = "Password for key store", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    public KeyStoreSecretContent() {
        super(SecretType.KEY_STORE);
    }

    public KeyStoreSecretContent(KeyStoreType keyStoreType, String content, String password) {
        this();
        this.keyStoreType = keyStoreType;
        this.content = content;
        this.password = password;
    }
}
