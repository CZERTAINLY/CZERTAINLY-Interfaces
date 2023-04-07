package com.czertainly.api.model.core.scep;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.certificate.CertificateDto;
import com.czertainly.api.model.core.raprofile.RaProfileDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ScepProfileDetailDto extends ScepProfileDto {
    @Schema(description = "RA Profile")
    private RaProfileDto raProfile;
    @Schema(description = "SCEP URL", example = "https://some-server.com/api/v1/protocols/scep/profile/pkiclient.exe")
    private String scepUrl;
    @Schema(description = "List of Attributes to issue a Certificate")
    private List<ResponseAttributeDto> issueCertificateAttributes;
    @Schema(description = "List of Custom Attributes")
    private List<ResponseAttributeDto> customAttributes;
    @Schema(description = "CA Certificate for the SCEP Profile")
    private CertificateDto caCertificate;
}
