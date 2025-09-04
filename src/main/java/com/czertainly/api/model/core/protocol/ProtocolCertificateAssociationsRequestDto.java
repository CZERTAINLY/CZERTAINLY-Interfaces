package com.czertainly.api.model.core.protocol;

import com.czertainly.api.model.client.attribute.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ProtocolCertificateAssociationsRequestDto implements Serializable {

    @Schema(description = "UUID of the user to be associated with certificate by protocol", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID ownerUuid;

    @Schema(description = "UUIDs of the groups to be associated with certificate by protocol",  requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<UUID> groupUuids = new ArrayList<>();

    @Schema(description = "Custom Attributes to be associated with certificate by protocol",  requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RequestAttributeDto> customAttributes = new ArrayList<>();

        return ownerUuid == null
                && (groupUuids == null || groupUuids.isEmpty())
                && (customAttributes == null || customAttributes.isEmpty());
    }
}
