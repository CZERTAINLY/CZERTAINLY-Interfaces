package com.czertainly.api.model.connector.secrets.content;

import com.czertainly.api.model.connector.secrets.SecretType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BasicAuthSecretRequestDto.class, name = SecretType.Codes.BASIC_AUTH),
        @JsonSubTypes.Type(value = TokenSecretRequestDto.class, name = SecretType.Codes.TOKEN),
        @JsonSubTypes.Type(value = PrivateKeySecretRequestDto.class, name = SecretType.Codes.PRIVATE_KEY),
        @JsonSubTypes.Type(value = KeyValueSecretRequestDto.class, name = SecretType.Codes.KEY_VALUE),
        @JsonSubTypes.Type(value = GenericSecretRequestDto.class, name = SecretType.Codes.GENERIC)
})
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
public abstract class SecretContentResponseDto {

    @Schema(description = "Secret type", requiredMode = Schema.RequiredMode.REQUIRED, examples = {SecretType.Codes.TOKEN})
    private SecretType type;
}
