package com.czertainly.api.model.raprofile;

import com.czertainly.api.model.AttributeDefinition;
import com.czertainly.api.model.Identified;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Class representing RA profile
 */
public class RaProfileDto implements Identified {

    @Schema(description = "Identifier of RA profile")
    private Long id;

    @Schema(description = "UUID of RA profile")
    private String uuid;

    @Schema(description = "RA profile name",
            required = true)
    private String name;

    @Schema(description = "Detailed description")
    private String description;
  
    @Schema(description = "Identifier of CA instance reference",
            required = true)
    private String caInstanceUuid;

    @Schema(description = "Name of CA instance",
            required = true)
    private String caInstanceName;

    @Schema(description = "List of RA profiles attributes",
            implementation = List.class,
            required = true)
    private List<AttributeDefinition> attributes;

    @Schema(description = "Enabled flag - true = enabled; false = disabled",
            required = true)
    private Boolean enabled;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCaInstanceUuid() {
        return caInstanceUuid;
    }

    public void setCaInstanceUuid(String caInstanceUuid) {
        this.caInstanceUuid = caInstanceUuid;
    }

    public List<AttributeDefinition> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDefinition> attributes) {
        this.attributes = attributes;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getCaInstanceName() { return caInstanceName; }

    public void setCaInstanceName(String caInstanceName) { this.caInstanceName = caInstanceName; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("uuid", uuid)
                .append("name", name)
                .append("description", description)
                .append("caInstanceUuid", caInstanceUuid)
                .append("attributes", attributes)
                .append("enabled", enabled)
                .append("caInstanceName", caInstanceName)
                .toString();
    }
}
