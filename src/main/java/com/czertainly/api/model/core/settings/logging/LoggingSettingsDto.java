package com.czertainly.api.model.core.settings.logging;

import com.czertainly.api.model.core.settings.SettingsDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class LoggingSettingsDto implements SettingsDto {

    @NotNull
    @Schema(description = "Settings of audit logging", requiredMode = Schema.RequiredMode.REQUIRED)
    private AuditLoggingSettingsDto auditLogs;

    @NotNull
    @Schema(description = "Settings of event logging", requiredMode = Schema.RequiredMode.REQUIRED)
    private ResourceLoggingSettingsDto eventLogs;

}
