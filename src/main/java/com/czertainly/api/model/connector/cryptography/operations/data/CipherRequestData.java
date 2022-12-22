package com.czertainly.api.model.connector.cryptography.operations.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CipherRequestData {

    @Schema(
            description = "Encrypted/decrypted data",
            required = true
    )
    private byte[] data;

    @Schema(
            description = "Custom identifier of the data, that should be the same as in the request, if available",
            example = "customId"
    )
    private String identifier;
}
