package com.czertainly.api.model.connector.secrets.content;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class KeyValueSecretRequestDto extends SecretContentRequestDto {

    @Schema(description = "Key-Value pairs stored as the secret value, represented by JSON object", requiredMode = Schema.RequiredMode.REQUIRED)
    private Serializable value;

}
