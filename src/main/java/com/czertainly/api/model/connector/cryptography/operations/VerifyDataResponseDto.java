package com.czertainly.api.model.connector.cryptography.operations;

import com.czertainly.api.model.connector.cryptography.operations.data.VerificationResponseData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VerifyDataResponseDto {

    @Schema(
            description = "Signatures",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<VerificationResponseData> verifications;

}
