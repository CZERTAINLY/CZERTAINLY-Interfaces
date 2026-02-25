package com.czertainly.api.model.connector.secrets.content;

import com.czertainly.api.model.connector.secrets.SecretType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Schema(
        title = "KeyValueSecretContent",
        description = "Secret representing key-value pairs"
)
public class KeyValueSecretContent extends SecretContent {

    @ToString.Exclude
    @Schema(description = "Key-Value pairs stored as the secret content, represented by JSON object", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, Object> content;

    public KeyValueSecretContent() {
        super(SecretType.KEY_VALUE);
    }

    public KeyValueSecretContent(Map<String, Object> content) {
        this();
        this.content = content;
    }
}
