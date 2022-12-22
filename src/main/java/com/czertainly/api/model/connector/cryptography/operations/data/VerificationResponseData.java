package com.czertainly.api.model.connector.cryptography.operations.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VerificationResponseData {

    @Schema(
            description = "Data to be signed or verified",
            required = true
    )
    private boolean result;

    @Schema(
            description = "Custom identifier of the data, that should be the same as in the request, if available",
            example = "customId"
    )
    private String identifier;

    @Schema(
            description = "Additional details for the result, for example reason, etc."
    )
    private Object details;

}
