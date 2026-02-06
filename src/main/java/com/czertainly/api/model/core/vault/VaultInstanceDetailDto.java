package com.czertainly.api.model.core.vault;

import com.czertainly.api.model.client.attribute.ResponseAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class VaultInstanceDetailDto extends VaultInstanceDto {

    @Schema
            (
                    description = "List of attributes of the Vault instance",
                    requiredMode = Schema.RequiredMode.REQUIRED
            )
    List<ResponseAttribute> attributes;

    @Schema
            (
                    description = "List of custom attributes of the Vault instance",
                    requiredMode = Schema.RequiredMode.NOT_REQUIRED
            )
    List<ResponseAttribute> customAttributes;
}
