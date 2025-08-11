package com.czertainly.api.model.core.cmp;

import com.czertainly.api.model.client.raprofile.SimplifiedRaProfileDto;
import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.core.protocol.ProtocolCertificateAssociationsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CmpProfileDto extends NameAndUuidDto {

    @Schema(
            description = "Enabled flag - true = enabled; false = disabled",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private boolean enabled;

    @Schema(
            description = "Variant of the CMP Profile",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CmpProfileVariant variant;

    @Schema(
            description = "CMP Profile description",
            examples = {"Sample text description"}
    )
    private String description;

    @Schema(
            description = "RA Profile associated with the CMP Profile"
    )
    private SimplifiedRaProfileDto raProfile;

    @Schema(
            description = "CMP URL",
            examples = {"https://your-domain.com/api/v1/protocols/cmp/cmpProfile"}
    )
    private String cmpUrl;

    @Schema(description = "Properties to set for certificates associated with protocol", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private ProtocolCertificateAssociationsDto certificateAssociations;

}
