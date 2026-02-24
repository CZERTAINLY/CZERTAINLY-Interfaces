package com.czertainly.api.model.connector.secrets.content;

import com.czertainly.api.model.connector.secrets.SecretType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(
        title = "BasicAuthSecretContent",
        description = "Secret representing Basic Authentication credentials"
)
public class BasicAuthSecretContent extends SecretContent {

    @Schema(description = "Username for Basic Authentication", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @ToString.Exclude
    @Schema(description = "Password for Basic Authentication", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    protected BasicAuthSecretContent() {
        super(SecretType.BASIC_AUTH);
    }
}
