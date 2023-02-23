package com.czertainly.api.model.core.acme;

import com.czertainly.api.model.common.NameAndUuidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AcmeProfileListDto extends NameAndUuidDto {

    @Schema(description = "Enabled flag - true = enabled; false = disabled",
            requiredMode = Schema.RequiredMode.REQUIRED,
            example = "false")
    private boolean enabled;
    @Schema(description = "ACME Profile description", example = "Some description")
    private String description;
    @Schema(description = "Name of the RA Profile", example = "RA Profile 1")
    private String raProfileName;
    @Schema(description = "UUID of RA Profile", example = "6b55de1c-844f-11ec-a8a3-0242ac120002")
    private String raProfileUuid;
    @Schema(description = "URL of the ACME Directory", example = "https://some-server.com/api/v1/protocols/acme/profile1/directory")
    private String directoryUrl;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRaProfileName() {
        return raProfileName;
    }

    public void setRaProfileName(String raProfileName) {
        this.raProfileName = raProfileName;
    }

    public String getRaProfileUuid() {
        return raProfileUuid;
    }

    public void setRaProfileUuid(String raProfileUuid) {
        this.raProfileUuid = raProfileUuid;
    }

    public String getDirectoryUrl() {
        return directoryUrl;
    }

    public void setDirectoryUrl(String directoryUrl) {
        this.directoryUrl = directoryUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AcmeProfileListDto() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("isEnabled", enabled)
                .append("description", description)
                .append("raProfileName", raProfileName)
                .append("raProfileUuid", raProfileUuid)
                .append("directoryUrl", directoryUrl)
                .toString();
    }
}
