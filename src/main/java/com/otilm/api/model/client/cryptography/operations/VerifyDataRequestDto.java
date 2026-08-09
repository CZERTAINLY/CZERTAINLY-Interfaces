package com.otilm.api.model.client.cryptography.operations;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class VerifyDataRequestDto extends SignDataRequestDto {

    @Schema(description = "Signatures to verify", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SignatureRequestData> signatures;

}
