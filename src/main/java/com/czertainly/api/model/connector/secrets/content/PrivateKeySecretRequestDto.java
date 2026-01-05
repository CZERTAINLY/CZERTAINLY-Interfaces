package com.czertainly.api.model.connector.secrets.content;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PrivateKeySecretRequestDto extends SecretContentRequestDto {

    @Schema(description = "Private key content encoded as Base64 string", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "Indicates whether the private key is in PEM format", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean pemEncoded;

}
