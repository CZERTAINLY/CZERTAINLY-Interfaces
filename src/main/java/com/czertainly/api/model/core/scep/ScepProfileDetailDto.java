package com.czertainly.api.model.core.scep;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.certificate.CertificateDto;
import com.czertainly.api.model.core.raprofile.RaProfileDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ScepProfileDetailDto extends NameAndUuidDto {
    @Schema(description = "Enabled flag - true = enabled; false = disabled",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean enabled;
    @Schema(description = "ACME Profile description", example = "Sample description")
    private String description;
    @Schema(description = "Enforce manual approval for all requests", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean requireManualApproval;
    @Schema(description = "RA Profile")
    private RaProfileDto raProfile;
    @Schema(description = "SCEP URL", example = "https://some-server.com/api/v1/protocols/acme/profile1/directory")
    private String scepUrl;
    @Schema(description = "List of Attributes to issue a Certificate")
    private List<ResponseAttributeDto> issueCertificateAttributes;
    @Schema(description = "List of Attributes to revoke a Certificate")
    private List<ResponseAttributeDto> revokeCertificateAttributes;
    @Schema(description = "List of Custom Attributes")
    private List<ResponseAttributeDto> customAttributes;

    @Schema(description = "CA Certificate for the SCEP Profile")
    private CertificateDto caCertificate;
    @Schema(description = "Include CA Certificate in the scep response", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean includeCaCertificate;
    @Schema(description = "Include CA Certificate Chain in the scep response", requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean includeCaCertificateChain;
    @Schema(description = "Renew Threshold")
    private Integer renewThreshold;
}
