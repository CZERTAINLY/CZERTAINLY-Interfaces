package com.czertainly.api.model.connector.secrets;

import com.czertainly.api.model.client.attribute.RequestAttribute;
import com.czertainly.api.model.common.attribute.common.MetadataAttribute;
import com.czertainly.api.model.connector.secrets.content.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateSecretRequestDto {

    @Schema(description = "Name of the secret", requiredMode = Schema.RequiredMode.REQUIRED, examples = {"MyServerCredentials"})
    private String name;

    @Schema(description = "Secret content", requiredMode = Schema.RequiredMode.REQUIRED)
    private SecretContent secret;

    @Schema(description = "Vault attributes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttribute> vaultAttributes = new ArrayList<>();

    @Schema(description = "Secret attributes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttribute> secretAttributes = new ArrayList<>();

    @Schema(description = "Metadata for the secret", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<MetadataAttribute> metadata = new ArrayList<>();
}
