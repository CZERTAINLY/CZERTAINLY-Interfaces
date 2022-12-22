package com.czertainly.api.model.connector.cryptography.operations;

import com.czertainly.api.model.connector.cryptography.operations.data.CipherResponseData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EncryptDataResponseDto {

    @Schema(
            description = "Encrypted data",
            required = true
    )
    private List<CipherResponseData> encryptedData;

}
