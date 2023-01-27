package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.core.certificate.group.GroupDto;
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
public class KeyDetailDto extends KeyDto {

    @Schema(
            description = "Custom Attributes for the Cryptographic Key"
    )
    private List<ResponseAttributeDto> customAttributes;

    @Schema(
            description = "Attributes for the Cryptographic Key",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<ResponseAttributeDto> attributes;

    @Schema(
            description = "Owner of the Key"
    )
    private String owner;

    @Schema(
            description = "Key Group"
    )
    private GroupDto group;

    @Schema(
            description = "Key Objects",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<KeyItemDto> items;
}
