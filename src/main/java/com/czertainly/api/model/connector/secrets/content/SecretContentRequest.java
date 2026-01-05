package com.czertainly.api.model.connector.secrets.content;

import com.czertainly.api.model.connector.secrets.SecretType;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request for secret dependent on secret type",
        type = "object",
        discriminatorProperty = "type",
        discriminatorMapping = {
                @DiscriminatorMapping(value = SecretType.Codes.BASIC_AUTH, schema = BasicAuthSecretRequestDto.class),
                @DiscriminatorMapping(value = SecretType.Codes.TOKEN, schema = TokenSecretRequestDto.class),
                @DiscriminatorMapping(value = SecretType.Codes.PRIVATE_KEY, schema = PrivateKeySecretRequestDto.class),
                @DiscriminatorMapping(value = SecretType.Codes.KEY_VALUE, schema = KeyValueSecretRequestDto.class),
                @DiscriminatorMapping(value = SecretType.Codes.GENERIC, schema = GenericSecretRequestDto.class),
        },
        oneOf = {
                BasicAuthSecretRequestDto.class,
                TokenSecretRequestDto.class,
                PrivateKeySecretRequestDto.class,
                KeyValueSecretRequestDto.class,
                GenericSecretRequestDto.class
        })
public interface SecretContentRequest {

    @Schema(description = "Secret type", requiredMode = Schema.RequiredMode.REQUIRED, examples = {SecretType.Codes.TOKEN})
    SecretType getType();
}
