package com.otilm.api.model.connector.cryptography.operations;

import com.otilm.api.model.client.attribute.RequestAttribute;
import com.otilm.api.model.connector.cryptography.operations.data.CipherRequestData;
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
public class CipherDataRequestDto {

    @Schema(description = "List of cipher Attributes", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<RequestAttribute> cipherAttributes;

    @Schema(description = "Encrypted/decrypted data", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CipherRequestData> cipherData;

}
