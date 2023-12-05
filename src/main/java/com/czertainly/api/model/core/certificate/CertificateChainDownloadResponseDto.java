package com.czertainly.api.model.core.certificate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class CertificateChainDownloadResponseDto extends CertificateDownloadResponseDto {

    @Schema(
            description = "Indicator whether the chain returned is complete",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean completeChain;

    @Schema(
            description = "Format of the downloaded chain",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CertificateFormat format;

    @Schema(
            description = "Encoding of the downloaded certificate chain",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CertificateFormatEncoding encoding;

    @Schema(
            description = "Base64 encoded certificate chain in the specified format",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String content;
}
