package com.czertainly.api.model.core.settings;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

@Data
public class UtilsSettingsDto implements Serializable {

    @URL
    @NotNull
    @Schema(
            description = "URL of the Util Service",
            example = "http://util-service:8080"
    )
    private String utilsServiceUrl;
}
