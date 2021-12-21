package com.czertainly.api.model.raprofile;

import com.czertainly.api.model.AttributeDefinition;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class representing RA profile registration request
 */
public class AddRaProfileRequestDto {

    private String authorityInstanceUuid;
    private String name;
    private String description;
    private List<AttributeDefinition> attributes;
    private Boolean enabled;

    public String getAuthorityInstanceUuid() {
        return authorityInstanceUuid;
    }

    public void setAuthorityInstanceUuid(String authorityInstanceUuid) {
        this.authorityInstanceUuid = authorityInstanceUuid;
    }

    public Boolean getEnabled() {
        return enabled;
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

    public List<AttributeDefinition> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDefinition> attributes) {
        this.attributes = attributes;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("authorityInstanceUuid", authorityInstanceUuid)
                .append("name", name)
                .append("description", description)
                .append("attributes", attributes)
                .append("enabled", enabled)
                .toString();
    }
}
