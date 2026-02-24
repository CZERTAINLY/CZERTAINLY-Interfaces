package com.czertainly.api.model.connector.secrets.content;

import com.czertainly.api.model.connector.secrets.SecretType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(
        title = "JwtTokenSecretContent",
        description = "Secret representing JWT Token"
)
public class JwtTokenSecretContent extends SecretContent {

    @ToString.Exclude
    @Schema(description = "JWT Token content encoded as Base64 string", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    public JwtTokenSecretContent() {
        super(SecretType.JWT_TOKEN);
    }

}
