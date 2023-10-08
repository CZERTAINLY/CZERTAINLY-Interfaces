package com.czertainly.api.model.core.certificate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateValidationDto {

    @Schema(
            description = "Certificate validation step result status",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CertificateValidationStatus status;

    @Schema(
            description = "Certificate validation step result message",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String message;
}
