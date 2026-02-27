package com.czertainly.api.model.core.secret;

import com.czertainly.api.model.client.attribute.RequestAttribute;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SyncVaultProfileDto extends NameAndUuidDto {

    @Schema(description = "Request attributes for creating the secret in the vault")
    private List<RequestAttribute> secretAttributes;
}
