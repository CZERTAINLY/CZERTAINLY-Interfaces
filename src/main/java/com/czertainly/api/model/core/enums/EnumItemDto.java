package com.czertainly.api.model.core.enums;

import com.czertainly.api.model.common.enums.IPlatformEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EnumItemDto {

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Enum item code",
            example = "X509"
    )
    private String code;

    @Schema(
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Enum item display label",
            example = "X.509"
    )
    private String label;

    @Schema(
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            description = "Enum item description",
            example = "X.509 Certificate type"
    )
    private String description;
}
