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
public class TokenSecretRequestDto extends SecretContentRequestDto {

    @Schema(description = "Token value encoded as Base64 string", requiredMode = Schema.RequiredMode.REQUIRED)
    private String value;

}
