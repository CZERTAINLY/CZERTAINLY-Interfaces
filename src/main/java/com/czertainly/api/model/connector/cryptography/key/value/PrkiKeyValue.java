package com.czertainly.api.model.connector.cryptography.key.value;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class PrkiKeyValue extends KeyValue {

    @Schema(
            description = "Base64 ASN.1 encoded PrivateKeyInfo",
            required = true
    )
    private String value;

}
