package com.czertainly.api.model.core.secret.content;

import com.czertainly.api.model.core.secret.SecretType;
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
        title = "BasicAuthSecretContent",
        description = "Secret representing Basic Authentication credentials"
)
public class BasicAuthSecretContent extends SecretContent {

    @Schema(description = "Username for Basic Authentication", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "Password for Basic Authentication", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Override
    public SecretType getType() {
        return SecretType.BASIC_AUTH;
    }

}
