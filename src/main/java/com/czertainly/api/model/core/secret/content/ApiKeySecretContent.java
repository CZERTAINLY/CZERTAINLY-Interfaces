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
        title = "ApiKeySecretContent",
        description = "Secret representing an API Key"
)
public class ApiKeySecretContent extends SecretContent {

    @Schema(description = "API Key content encoded as Base64 string", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

}
