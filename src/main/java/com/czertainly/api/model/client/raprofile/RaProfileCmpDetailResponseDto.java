package com.czertainly.api.model.client.raprofile;

import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RaProfileCmpDetailResponseDto extends NameAndUuidDto {

    @Schema(
            description = "CMP availability flag - true = yes; false = no",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean cmpAvailable;

    @Schema(
            description = "CMP URL"
    )
    private String cmpUrl;

    @Schema(
            description = "List of Attributes to issue Certificate"
    )

    private List<ResponseAttributeDto> issueCertificateAttributes;

    @Schema(
            description = "List of Attributes to revoke Certificate"
    )
    private List<ResponseAttributeDto> revokeCertificateAttributes;

}
