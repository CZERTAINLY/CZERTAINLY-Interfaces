package com.czertainly.api.model.core.cmp;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.client.raprofile.SimplifiedRaProfileDto;
import com.czertainly.api.model.core.certificate.CertificateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CmpProfileDetailDto extends CmpProfileDto {

    @Schema(
            description = "RA Profile associated with the CMP Profile"
    )
    private SimplifiedRaProfileDto raProfile;

    @Schema(
            description = "List of Attributes to issue a Certificate for the associated RA Profile"
    )
    private List<ResponseAttributeDto> issueCertificateAttributes;

    @Schema(
            description = "List of Attributes to revoke a Certificate for the associated RA Profile"
    )
    private List<ResponseAttributeDto> revokeCertificateAttributes;

    @Schema(
            description = "List of Custom Attributes for CMP Profile"
    )
    private List<ResponseAttributeDto> customAttributes;

    @Schema(
            description = "Protection Method for the CMP Request",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private ProtectionMethod requestProtectionMethod;

    @Schema(
            description = "Protection Method for the CMP Response",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private ProtectionMethod responseProtectionMethod;

    @Schema(
            description = "Signing certificate for the CMP responses"
    )
    private CertificateDto signingCertificate;

}
