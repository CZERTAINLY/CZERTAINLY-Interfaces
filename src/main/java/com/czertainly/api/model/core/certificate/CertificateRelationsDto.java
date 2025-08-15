package com.czertainly.api.model.core.certificate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CertificateRelationsDto {

    @Schema(
            description = "UUID of the certificate that is the subject of the request. " +
                    "Listed certificates include its source and descending certificates.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID certificateUuid;

    @Schema(
            description = "List of related certificates where the subject certificate is the source certificate",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<CertificateSimpleDto> relatedCertificates;

    @Schema(
            description = "List of source certificates where the subject certificate is not the source certificate",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<CertificateSimpleDto> sourceCertificates;
}
