package com.czertainly.api.model.common.events.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CertificateActionPerformedEventData extends CertificateEventData {

    @Schema(description = "Certificate action", requiredMode = Schema.RequiredMode.REQUIRED)
    private String action;

    @Schema(description = "Error message. Filled when action failed", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String errorMessage;

    @Schema(description = "Authority instance reference UUID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID authorityInstanceUuid;

    @Schema(description = "RA profile UUID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UUID raProfileUuid;

    @Schema(description = "RA profile name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String raProfileName;
}
