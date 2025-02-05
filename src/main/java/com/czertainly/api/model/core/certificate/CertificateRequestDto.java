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
            description = "Certificate type"
    )
    private CertificateType certificateType;

    @Schema(
            description = "Certificate request format"
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
            description = "Subject alternative names"
    )
    private Map<String, Object> subjectAlternativeNames;

    @Schema(
            description = "CSR Attributes"
    )
    private List<ResponseAttributeDto> attributes;

    @Schema(
            description = "Signature Attributes"
    )
    private List<ResponseAttributeDto> signatureAttributes;

    @Schema(
            description = "UUID of the Public Key"
    )
    private String keyUuid;

}
