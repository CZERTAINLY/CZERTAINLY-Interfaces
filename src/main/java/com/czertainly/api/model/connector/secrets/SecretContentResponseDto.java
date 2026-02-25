package com.czertainly.api.model.connector.secrets;

import com.czertainly.api.model.connector.secrets.content.SecretContent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SecretContentResponseDto {

    @Schema(description = "Secret version, if versioning is supported", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String version;

    @Schema(description = "Secret Content", requiredMode = Schema.RequiredMode.REQUIRED)
    private SecretContent content;

}
