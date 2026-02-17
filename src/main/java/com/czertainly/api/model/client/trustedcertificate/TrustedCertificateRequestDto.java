package com.czertainly.api.model.client.trustedcertificate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrustedCertificateRequestDto {

    @Schema(
            description = "Base64 encoded Certificate content",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private byte[] certificateContent;

}