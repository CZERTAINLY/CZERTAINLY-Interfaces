package com.czertainly.api.model.core.settings.logging;

import com.czertainly.api.model.core.logging.enums.AuditLogOutput;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuditLoggingSettingsDto extends ResourceLoggingSettingsDto {

    @Schema(
            description = "Type of audit logs output/storage channel",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private AuditLogOutput output = AuditLogOutput.NONE;

    public void setResourceLogging(ResourceLoggingSettingsDto resourceLoggingSettingsDto) {
        this.setLogAllModules(resourceLoggingSettingsDto.isLogAllModules());
        this.setLogAllResources(resourceLoggingSettingsDto.isLogAllResources());
        this.setLoggedModules(resourceLoggingSettingsDto.getLoggedModules());
        this.setIgnoredModules(resourceLoggingSettingsDto.getIgnoredModules());
        this.setLoggedResources(resourceLoggingSettingsDto.getLoggedResources());
        this.setIgnoredResources(resourceLoggingSettingsDto.getIgnoredResources());
    }
}
