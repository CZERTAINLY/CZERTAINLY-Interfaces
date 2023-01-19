package com.czertainly.api.model.core.cryptography.token;

import com.czertainly.api.model.connector.cryptography.enums.TokenInstanceStatus;
import com.czertainly.api.model.connector.cryptography.token.TokenInstanceStatusComponent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TokenInstanceStatusDetailDto {
    @Schema(
            description = "Token instance status",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private TokenInstanceStatus status;

    @Schema(
            description = "Components of the Token instance status"
    )
    private Map<String, TokenInstanceStatusComponent> components;

    public TokenInstanceStatusDetailDto(TokenInstanceStatus status) {
        this.status = status;
    }
}
