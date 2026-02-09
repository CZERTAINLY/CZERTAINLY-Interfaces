package com.czertainly.api.model.core.secret.content;

import com.czertainly.api.model.core.secret.SecretType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "secretType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = KeyStoreSecretContentDto.class, name = SecretType.Codes.KEY_STORE),
        @JsonSubTypes.Type(value = BasicAuthSecretContentDto.class, name = SecretType.Codes.BASIC_AUTH),
        @JsonSubTypes.Type(value = KeyValueSecretContentDto.class, name = SecretType.Codes.KEY_VALUE),
        @JsonSubTypes.Type(value = PrivateKeySecretContentDto.class, name = SecretType.Codes.PRIVATE_KEY),
        @JsonSubTypes.Type(value = GenericSecretContentDto.class, name = SecretType.Codes.GENERIC),
        @JsonSubTypes.Type(value = SecretKeySecretContentDto.class, name = SecretType.Codes.SECRET_KEY),
        @JsonSubTypes.Type(value = JwtTokenSecretContentDto.class, name = SecretType.Codes.JWT_TOKEN),
        @JsonSubTypes.Type(value = ApiKeySecretContentDto.class, name = SecretType.Codes.API_KEY)
}
)
@Schema(
        description = "Base interface for Secret content. The actual content structure depends on the SecretType.",
        discriminatorProperty = "secretType",
        discriminatorMapping = {
                @DiscriminatorMapping(value = SecretType.Codes.KEY_STORE, schema = KeyStoreSecretContentDto.class),
                @DiscriminatorMapping(value = SecretType.Codes.BASIC_AUTH, schema = BasicAuthSecretContentDto.class),
                @DiscriminatorMapping(value = SecretType.Codes.KEY_VALUE, schema = KeyValueSecretContentDto.class),
                @DiscriminatorMapping(value = SecretType.Codes.PRIVATE_KEY, schema = PrivateKeySecretContentDto.class),
                @DiscriminatorMapping(value = SecretType.Codes.GENERIC, schema = GenericSecretContentDto.class),
                @DiscriminatorMapping(value = SecretType.Codes.SECRET_KEY, schema = SecretKeySecretContentDto.class),
                @DiscriminatorMapping(value = SecretType.Codes.JWT_TOKEN, schema = JwtTokenSecretContentDto.class),
                @DiscriminatorMapping(value = SecretType.Codes.API_KEY, schema = ApiKeySecretContentDto.class)
        },
        oneOf = {
                KeyStoreSecretContentDto.class,
                BasicAuthSecretContentDto.class,
                KeyValueSecretContentDto.class,
                PrivateKeySecretContentDto.class,
                GenericSecretContentDto.class,
                SecretKeySecretContentDto.class,
                JwtTokenSecretContentDto.class,
                ApiKeySecretContentDto.class
        }
)
public interface SecretContentDto {
    
    @Schema(description = "Type of the Secret content", requiredMode = Schema.RequiredMode.REQUIRED)
    SecretType getSecretType();

}
