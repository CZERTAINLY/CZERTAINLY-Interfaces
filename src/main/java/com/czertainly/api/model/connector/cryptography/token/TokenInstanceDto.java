package com.czertainly.api.model.connector.cryptography.token;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.v2.MetadataAttributeV2;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TokenInstanceDto extends NameAndUuidDto {

    @Schema(
            description = "Token instance Metadata"
    )
    private List<MetadataAttributeV2> metadata;

}
