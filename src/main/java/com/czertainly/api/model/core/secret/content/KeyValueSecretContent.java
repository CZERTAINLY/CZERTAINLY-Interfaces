package com.czertainly.api.model.core.secret.content;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(
        title = "KeyValueSecretContent",
        description = "Secret representing key-value pairs"
)
public class KeyValueSecretContent extends SecretContent {

    @Schema(description = "Key-Value pairs stored as the secret content, represented by JSON object", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, Object> content;

}
