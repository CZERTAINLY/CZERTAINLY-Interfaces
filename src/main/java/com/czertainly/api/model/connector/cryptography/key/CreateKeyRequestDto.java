package com.czertainly.api.model.connector.cryptography.key;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateKeyRequestDto {

    @Schema(description = "List of Token Profile Attributes",
            required = true)
    private List<RequestAttributeDto> tokenProfileAttributes;

    @Schema(description = "List of Attributes to create a Key",
            required = true)
    private List<RequestAttributeDto> createKeyAttributes;

}
