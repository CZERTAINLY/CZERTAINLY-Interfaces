package com.czertainly.api.model.core.secret.content;

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
        title = "PrivateKeySecretContent",
        description = "Secret representing private key"
)
public class PrivateKeySecretContent extends SecretContent {

    @Schema(description = "BASE64 encoded content of key in PEM format", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

}
