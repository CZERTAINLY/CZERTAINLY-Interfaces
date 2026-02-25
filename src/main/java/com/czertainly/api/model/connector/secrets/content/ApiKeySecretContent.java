package com.czertainly.api.model.connector.secrets.content;

import com.czertainly.api.model.connector.secrets.SecretType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Schema(
        title = "ApiKeySecretContent",
        description = "Secret representing an API Key"
)
public class ApiKeySecretContent extends SecretContent {

    @ToString.Exclude
    @Schema(description = "API Key content encoded as Base64 string", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    public ApiKeySecretContent() {
        super(SecretType.API_KEY);
    }

    public ApiKeySecretContent(String content) {
        this();
        this.content = content;
    }
}
