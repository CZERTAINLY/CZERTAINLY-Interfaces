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
        title = "SecretKeySecretContent",
        description = "Secret representing secret key"
)
public class SecretKeySecretContent extends SecretContent {

    @Schema(description = "BASE64 encoded binary (raw) content of key", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

}
