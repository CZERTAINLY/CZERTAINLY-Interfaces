package com.otilm.api.model.connector.cryptography.token;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.connector.cryptography.enums.TokenInstanceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TokenInstanceStatusDto {

    @Schema(description = "Token instance status", requiredMode = Schema.RequiredMode.REQUIRED)
    private TokenInstanceStatus status;

    @Schema(description = "Components of the Token instance status")
    private Map<String, TokenInstanceStatusComponent> components;

}
