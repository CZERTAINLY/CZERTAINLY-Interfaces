package com.czertainly.api.model.client.raprofile;

import com.czertainly.api.model.common.RequestAttributeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class EditRaProfileRequestDto {

    @Schema(description = "Description of RA Profile")
    private String description;

    @Schema(description = "Authority instance UUID",
            required = true)
    private String authorityInstanceUuid;

    @Schema(description = "List of Attributes for RA Profile",
            required = true)
    private List<RequestAttributeDto> attributes;

    @Schema(description = "Enabled flag - true = enabled; false = disabled")
    private Boolean enabled;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorityInstanceUuid() {
        return authorityInstanceUuid;
    }

    public void setAuthorityInstanceUuid(String authorityInstanceUuid) {
        this.authorityInstanceUuid = authorityInstanceUuid;
    }

    public List<RequestAttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<RequestAttributeDto> attributes) {
        this.attributes = attributes;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("description", description)
                .append("authorityInstanceUuid", authorityInstanceUuid)
                .append("attributes", attributes)
                .toString();
    }
}
