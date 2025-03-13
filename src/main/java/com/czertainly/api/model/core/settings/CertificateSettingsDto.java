package com.czertainly.api.model.core.settings;

import jakarta.validation.Valid;
import lombok.Data;

import java.io.Serializable;

@Data
public class CertificateSettingsDto implements Serializable {

   @Valid
    private CertificateValidationSettingsDto certificateValidationSettingsDto;
}
