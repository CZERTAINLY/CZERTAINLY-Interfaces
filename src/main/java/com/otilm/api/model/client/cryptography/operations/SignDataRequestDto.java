package com.otilm.api.model.client.cryptography.operations;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otilm.api.model.client.attribute.RequestAttribute;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SignDataRequestDto {

    @Schema(description = "List of signature Attributes", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<RequestAttribute> signatureAttributes;

    @Schema(description = "Data to be signed", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SignatureRequestData> data;

}
