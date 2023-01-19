package com.czertainly.api.model.client.cryptography.operations;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.connector.cryptography.operations.SignatureDataRequestDto;
import com.czertainly.api.model.connector.cryptography.operations.data.SignatureRequestData;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
