package com.otilm.api.model.connector.cryptography.operations;

import com.otilm.api.model.connector.cryptography.operations.data.CipherResponseData;
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
@ToString
public class EncryptDataResponseDto {

    @Schema(description = "Encrypted data", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CipherResponseData> encryptedData;

}
