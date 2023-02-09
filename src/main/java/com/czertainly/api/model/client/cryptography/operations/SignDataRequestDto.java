package com.czertainly.api.model.client.cryptography.operations;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SignDataRequestDto {

    @Schema(
            description = "List of cipher Attributes",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<RequestAttributeDto> signatureAttributes;

    @Schema(
            description = "Data to be signed"
    )
    private List<SignatureRequestData> data;

}
