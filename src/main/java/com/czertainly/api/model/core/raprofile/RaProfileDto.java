package com.czertainly.api.model.core.raprofile;

import com.czertainly.api.model.common.NameAndUuidDto;
import com.czertainly.api.model.client.attribute.ResponseAttributeDto;
import com.czertainly.api.model.core.compliance.ComplianceProfileDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class representing RA profile
 */
public class RaProfileDto extends NameAndUuidDto {

    @Schema(description = "Description of RA Profile")
    private String description;

    @Schema(description = "UUID of Authority provider",
            required = true)
    private String authorityInstanceUuid;

    @Schema(description = "Name of Authority instance",
            required = true)
    private String authorityInstanceName;

    @Schema(description = "List of RA Profiles attributes",
            required = true)
    private List<ResponseAttributeDto> attributes;

    @Schema(description = "Enabled flag - true = enabled; false = disabled",
            required = true)
    private Boolean enabled;

    @Schema(description = "List of protocols enabled",
            required = false)
    private List<String> enabledProtocols;

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ResponseAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ResponseAttributeDto> attributes) {
        this.attributes = attributes;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getAuthorityInstanceUuid() {
        return authorityInstanceUuid;
    }

    public void setAuthorityInstanceUuid(String authorityInstanceUuid) {
        this.authorityInstanceUuid = authorityInstanceUuid;
    }

    public String getAuthorityInstanceName() {
        return authorityInstanceName;
    }

    public void setAuthorityInstanceName(String authorityInstanceName) {
        this.authorityInstanceName = authorityInstanceName;
    }

    public List<String> getEnabledProtocols() {
        return enabledProtocols;
    }

    public void setEnabledProtocols(List<String> enabledProtocols) {
        this.enabledProtocols = enabledProtocols;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("uuid", uuid)
                .append("name", name)
                .append("description", description)
                .append("authorityInstanceUuid", authorityInstanceUuid)
                .append("attributes", attributes)
                .append("enabled", enabled)
                .append("authorityInstanceName", authorityInstanceName)
                .toString();
    }
}
