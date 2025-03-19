package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

@Data
public class UtilsSettingsDto implements Serializable {

    @URL
    @Schema(
            description = "URL of the Util Service",
            examples = {"http://util-service:8080"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String utilsServiceUrl;
}
