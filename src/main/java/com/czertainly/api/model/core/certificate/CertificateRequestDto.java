package com.czertainly.api.model.core.certificate;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.core.enums.CertificateRequestFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CertificateRequestDto {


    @Schema(
            description = "Certificate type",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CertificateType certificateType;

    @Schema(
            description = "Certificate request format",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CertificateRequestFormat certificateRequestFormat;

    @Schema(
            description = "Public key algorithm",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String publicKeyAlgorithm;

    @Schema(
            description = "Certificate signature algorithm",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String signatureAlgorithm;

    @Schema(
            description = "Certificate request content",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String content;

    @Schema(
            description = "Certificate common name",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String commonName;

    @Schema(
            description = "Subject DN of the Certificate",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String subjectDn;

    @Schema(
            description = "Subject alternative names",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Map<String, List<String>> subjectAlternativeNames;

    @Schema(
            description = "CSR Attributes",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<ResponseAttributeDto> attributes;

    @Schema(
            description = "Signature Attributes",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private List<ResponseAttributeDto> signatureAttributes;

    @Schema(
            description = "UUID of the Key",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String keyUuid;

}
