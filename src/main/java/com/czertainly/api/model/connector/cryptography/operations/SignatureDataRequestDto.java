package com.czertainly.api.model.connector.cryptography.operations;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SignatureDataRequestDto {

    @Schema(
            description = "Metadata of the Key",
            required = true
    )
    private List<MetadataAttribute> keyMetadata;

    @Schema(
            description = "List of cipher Attributes",
            required = true
    )
    private List<RequestAttributeDto> signatureAttributes;

}
