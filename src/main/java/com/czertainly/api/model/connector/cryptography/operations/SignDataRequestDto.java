package com.czertainly.api.model.connector.cryptography.operations;

import com.czertainly.api.model.connector.cryptography.operations.data.SignatureRequestData;
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
public class SignDataRequestDto extends SignatureDataRequestDto {

    @Schema(
            description = "Raw data to be hashed and signed"
    )
    private List<SignatureRequestData> data;

    @Schema(
            description = "Digested data to be signed"
    )
    private List<SignatureRequestData> digestedData;

}
