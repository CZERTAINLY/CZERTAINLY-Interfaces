package com.czertainly.api.model.connector.cryptography.key;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DestroyKeyRequestDto {

    @Schema(description = "List of Token Profile Attributes",
            required = true)
    private List<RequestAttributeDto> tokenProfileAttributes;

    @Schema(
            description = "Attributes of the Key"
    )
    private List<MetadataAttribute> keyAttributes;

}
