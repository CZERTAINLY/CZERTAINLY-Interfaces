package com.czertainly.api.model.core.cryptography.key;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.auth.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class KeyAssociationDto extends NameAndUuidDto {

    @Schema(
            description = "Type of the resource",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Resource resource;

}
