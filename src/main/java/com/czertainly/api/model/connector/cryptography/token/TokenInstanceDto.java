package com.czertainly.api.model.connector.cryptography.token;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.common.attribute.v2.BaseAttribute;
import com.czertainly.api.model.common.attribute.v2.MetadataAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class TokenInstanceDto extends NameAndUuidDto {

    @Schema(
            description = "List of Token instance Attributes",
            required = true
    )
    private List<BaseAttribute> attributes;

    @Schema(
            description = "Token instance Metadata"
    )
    private List<MetadataAttribute> metadata;

}
