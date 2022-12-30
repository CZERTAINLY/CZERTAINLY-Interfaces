package com.czertainly.api.model.client.cryptography.operations;

import com.czertainly.api.model.connector.cryptography.operations.data.SignatureRequestData;
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
public class VerifyDataRequestDto extends SignDataRequestDto {

    @Schema(
            description = "Signatures to verify",
            required = true
    )
    private List<SignatureRequestData> signatures;

}
